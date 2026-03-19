package com.aspnt.mddl;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;
import org.jeasy.random.randomizers.time.InstantRandomizer;
import org.jeasy.random.randomizers.time.LocalDateTimeRandomizer;
import org.jeasy.random.randomizers.time.OffsetDateTimeRandomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Configuration
public class EasyRandomConfiguration {

    @Bean
    public EasyRandom easyRandom() {
        return create();
    }

    public static EasyRandom create() {
        return new EasyRandom(getParameters());
    }

    private static EasyRandomParameters getParameters() {
        return new EasyRandomParameters()
                .seed(123L)
                .randomizationDepth(4)
                .objectPoolSize(100)
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(5, 20)
                .collectionSizeRange(1, 4)
                .scanClasspathForConcreteTypes(true)
                .overrideDefaultInitialization(true)
                .ignoreRandomizationErrors(true)
                .excludeField(f -> f.getAnnotation(Id.class) != null)
                .excludeField(f -> f.getName().equals("createdTs"))
                .excludeField(f -> f.getName().equals("updatedTs"))
                .excludeField(f -> f.getName().equals("parentId"))
                .randomize(Integer.class, new IntegerRangeRandomizer(0, Integer.MAX_VALUE))
                .randomize(OffsetDateTime.class, new OffsetDateTimeRandomizer(123L) {
                    @Override
                    public OffsetDateTime getRandomValue() {
                        return super.getRandomValue()
                                .atZoneSameInstant(ZoneOffset.systemDefault()).toOffsetDateTime()
                                .truncatedTo(ChronoUnit.SECONDS);
                    }
                })
                .randomize(LocalDateTime.class, new LocalDateTimeRandomizer(123L) {
                    @Override
                    public LocalDateTime getRandomValue() {
                        return super.getRandomValue()
                                .truncatedTo(ChronoUnit.SECONDS);
                    }
                })
                .randomize(Instant.class, new InstantRandomizer(123L) {
                    @Override
                    public Instant getRandomValue() {
                        return super.getRandomValue().truncatedTo(ChronoUnit.SECONDS);
                    }
                });
    }
}
