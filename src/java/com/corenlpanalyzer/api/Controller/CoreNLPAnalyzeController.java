package com.corenlpanalyzer.api.Controller;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageDataRetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This class defines all API endpoints directly related
 * with the NLP text analysis.
 *
 * @author Sergey Dubovyk
 * @version 1.0
 */
@RestController
public class CoreNLPAnalyzeController {
    private final ICoreNLPAnalyzerService analyzerService;


    @Autowired
    public CoreNLPAnalyzeController(ICoreNLPAnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    /**
     * This API endpoint should be used to get the NLP analysis
     * data for the body of given page.
     *
     * @param targetURL A link to the page to be analyzed
     * @return A JSON-ed AnalysisResult instance with analysis data.
     */
    @GetMapping(path = "/analyze")
    public @ResponseBody AnalysisResult response(@RequestParam String targetURL){
        AnalysisResult result = analyzerService.score(targetURL);
        if (result != null){
            return result;
        } else {
            return null;
        }
    }
}
