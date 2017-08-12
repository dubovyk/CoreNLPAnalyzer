package com.corenlpanalyzer.api.Controller;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.PageAnalysisResult;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines all API endpoints directly related
 * with the NLP text analysis.
 *
 * @author Sergey Dubovyk
 * @version 1.0
 */
@RestController
public class CoreNLPAnalyzeController {
    private final ICoreNLPAnalyzerService rawAnalyzerService;
    private final IPageAnalyzerService pageAnalyzerService;

    @Autowired
    public CoreNLPAnalyzeController(ICoreNLPAnalyzerService analyzerService, IPageAnalyzerService pageAnalyzerService) {
        this.rawAnalyzerService = analyzerService;
        this.pageAnalyzerService = pageAnalyzerService;
    }

    /**
     * This API endpoint should be used to get the NLP analysis
     * data for the body of given page.
     *
     * @param targetURL A link to the page to be analyzed
     * @return A JSON-ed AnalysisResult instance with analysis data.
     */
    @GetMapping(path = "/url_analyze")
    public @ResponseBody PageAnalysisResult response(@RequestParam String targetURL){
        PageAnalysisResult result = pageAnalyzerService.score(targetURL);
        if (result != null){

            return result;
        } else {
            return null;
        }
    }

    @PostMapping(path = "/raw_analyze")
    public @ResponseBody AnalysisResult analyze(@RequestBody Map<String, String> data){
        AnalysisResult result = rawAnalyzerService.score(data.get("text"));

        return result;
    }

    @GetMapping("/echo")
    public @ResponseBody String echo(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }
}
