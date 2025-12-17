package ru.softmachine.odyssey.backend.cms.utils;

import com.ibm.icu.text.Transliterator;
import org.springframework.stereotype.Component;

@Component
public class SlugUtils {

    private static final Transliterator TO_LATIN = Transliterator.getInstance("Russian-Latin/BGN");

    public String nameToSlug(String name) {
        var transStr = TO_LATIN.transliterate(name);
        return transStr.toLowerCase()
                .replaceAll("[^\sa-z0-9_—-]", "")
                .replaceAll("\s", "-")
                .replaceAll("—", "-")
                .replaceAll("_", "-")
                .replaceAll("-+", "-");
    }
}
