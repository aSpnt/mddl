package ru.softmachine.odyssey.backend.cms.utils.compression.impl;

import net.coobird.thumbnailator.Thumbnails;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.utils.compression.CompressionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class BytedecoCompressionUtils implements CompressionUtils {

    @Value("${app.upload.compression.video.captureWidth}")
    private Integer captureWidth;
    @Value("${app.upload.compression.video.captureHeight}")
    private Integer captureHeight;
    @Value("${app.upload.compression.video.quality}")
    private String crfVideoQuality;
    @Value("${app.upload.compression.video.preset}")
    private String videoPreset;
    @Value("${app.upload.compression.video.target-format}")
    private String videoFormat;
    @Value("${app.upload.compression.video.preserved-format}")
    private String preservedFormat;
    @Value("${app.upload.compression.video.tune}")
    private String videoTune;
    @Value("${app.upload.compression.video.video-bitrate}")
    private Integer videoBitrate;

    @Value("${app.upload.compression.video.audio-quality}")
    private String audioQuality;
    @Value("${app.upload.compression.video.audio-bitrate}")
    private Integer audioBitrate;
    @Value("${app.upload.compression.video.audio-sample-rate}")
    private Integer audioSampleRate;
    @Value("${app.upload.compression.video.audio-channels}")
    private Integer audioChannels;

    @Value("${app.upload.compression.image.quality}")
    private Double imageQuality;
    @Value("${app.upload.compression.image.scale}")
    private Double imageScale;


    public void compressImage(InputStream in, OutputStream out) {
        try {
            Thumbnails.of(in)
                    .scale(imageScale)
                    .outputQuality(imageQuality)
                    .toOutputStream(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void compressVideo(File in, File out, String currentVideoFormat) {
        var targetVideoFormat = !preservedFormat.contains(currentVideoFormat) ? videoFormat : currentVideoFormat;
        var builder = new ProcessBuilder(
                "ffmpeg", "-i", in.getAbsolutePath(),
                "-crf", crfVideoQuality,
                "-f", targetVideoFormat,
                "-b:v", String.valueOf(videoBitrate),
                "-vcodec", "h264",
                "-acodec", "aac",
                "-ac", String.valueOf(audioChannels),
                "-aq", String.valueOf(audioQuality),
                "-ar", String.valueOf(audioSampleRate),
                "-b:a", String.valueOf(audioBitrate),
                out.getAbsolutePath());
        try {
            builder.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO Временно не используется, так как не работает в Докер-контейнере
     */
    public void compressVideo(InputStream in, File out, String currentVideoFormat) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(in);
             FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(out, captureWidth, captureHeight, audioChannels)) {

            grabber.start();

            recorder.setInterleaved(true);

            recorder.setVideoOption("tune", videoTune);
            recorder.setVideoOption("preset", videoPreset);
            recorder.setVideoOption("crf", crfVideoQuality);

            recorder.setVideoBitrate(videoBitrate);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);

            if (!preservedFormat.contains(currentVideoFormat)) {
                recorder.setFormat(videoFormat);
            } else {
                recorder.setFormat(currentVideoFormat);
            }

            recorder.setAudioOption("crf", audioQuality);
            recorder.setAudioBitrate(audioBitrate);
            recorder.setSampleRate(audioSampleRate);
            recorder.setAudioChannels(audioChannels);
            recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);

            recorder.start();

            Frame capturedFrame;
            while ((capturedFrame = grabber.grab()) != null) {
                recorder.record(capturedFrame);
            }

            recorder.stop();
            grabber.stop();

        } catch (FrameGrabber.Exception e) {
            //TODO
            throw new RuntimeException(e);
        } catch (FrameRecorder.Exception e) {
            //TODO
            throw new RuntimeException(e);
        }
    }
}

