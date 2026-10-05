package com.codegym.model;

public class EmailConfig {
    private String language;
    private int pageSize;

    public EmailConfig() {
    }

    public EmailConfig(String language, int pageSize) {
        this.language = language;
        this.pageSize = pageSize;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "EmailConfig{" +
                "language='" + language + '\'' +
                ", pageSize=" + pageSize +
                '}';
    }
}
