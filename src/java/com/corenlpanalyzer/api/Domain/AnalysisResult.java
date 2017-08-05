package com.corenlpanalyzer.api.Domain;

public class AnalysisResult {
    private String targetURL;
    private double bodyEmotionsCoefficient;

    public AnalysisResult() {
    }

    public AnalysisResult(String targetURL, double bodyEmotionsCoefficient) {
        this.targetURL = targetURL;
        this.bodyEmotionsCoefficient = bodyEmotionsCoefficient;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public double getBodyEmotionsCoefficient() {
        return bodyEmotionsCoefficient;
    }

    public void setBodyEmotionsCoefficient(double bodyEmotionsCoefficient) {
        this.bodyEmotionsCoefficient = bodyEmotionsCoefficient;
    }
}
