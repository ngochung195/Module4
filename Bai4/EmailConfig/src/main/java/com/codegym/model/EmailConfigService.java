package com.codegym.model;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmailConfigService {
    private static EmailConfig currentConfig = new EmailConfig("English", 5);

    public static final List<String> LANGUAGES = Arrays.asList(
            "English",
            "Vietnamese",
            "Japanese",
            "Chinese"
    );

    public static final List<Integer> PAGE_SIZES = Arrays.asList(
            5, 10, 15, 25, 50, 100
    );

    public EmailConfig getConfig() {
        return new EmailConfig(currentConfig.getLanguage(), currentConfig.getPageSize());
    }

    public void updateConfig(EmailConfig newConfig) {
        if (newConfig != null) {
            currentConfig.setLanguage(newConfig.getLanguage());
            currentConfig.setPageSize(newConfig.getPageSize());
        }
    }

    public List<String> getLanguages() {
        return LANGUAGES;
    }

    public List<Integer> getPageSizes() {
        return PAGE_SIZES;
    }
}
