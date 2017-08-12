package com.corenlpanalyzer.api.Domain;

public class PageAnalysisResult {
    private AnalysisResult titleAnalysisResult, metaAnalysisResult, bodyAnalysisResult, wholePageAnalysisResult;

    public AnalysisResult getTitleAnalysisResult() {
        return titleAnalysisResult;
    }

    public void setTitleAnalysisResult(AnalysisResult titleAnalysisResult) {
        this.titleAnalysisResult = titleAnalysisResult;
    }

    public AnalysisResult getMetaAnalysisResult() {
        return metaAnalysisResult;
    }

    public void setMetaAnalysisResult(AnalysisResult metaAnalysisResult) {
        this.metaAnalysisResult = metaAnalysisResult;
    }

    public AnalysisResult getBodyAnalysisResult() {
        return bodyAnalysisResult;
    }

    public void setBodyAnalysisResult(AnalysisResult bodyAnalysisResult) {
        this.bodyAnalysisResult = bodyAnalysisResult;
    }

    public AnalysisResult getWholePageAnalysisResult() {
        return wholePageAnalysisResult;
    }

    public void setWholePageAnalysisResult(AnalysisResult wholePageAnalysisResult) {
        this.wholePageAnalysisResult = wholePageAnalysisResult;
    }
}
