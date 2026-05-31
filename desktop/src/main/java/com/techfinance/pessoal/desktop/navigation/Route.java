package com.techfinance.pessoal.desktop.navigation;

public enum Route {
    REGISTER("register.fxml", "register.css", 1000, 650);

    private final String fxml;
    private final String css;
    private final int width;
    private final int height;

    Route(String fxml, String css, int width, int height) {
        this.fxml = fxml;
        this.css = css;
        this.width = width;
        this.height = height;
    }

    public String getFxml() {
        return fxml;
    }

    public String getCss() {
        return css;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}