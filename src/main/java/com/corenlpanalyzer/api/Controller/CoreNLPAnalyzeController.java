package com.corenlpanalyzer.api.Controller;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.PageAnalysisResult;
import com.corenlpanalyzer.api.NLP.Runnables.ICoreNLPAnalyzer;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageAnalyzerService;
import com.corenlpanalyzer.api.Service.ISummarizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

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
    private final ISummarizationService summarizationService;

    @Autowired
    public CoreNLPAnalyzeController(ICoreNLPAnalyzerService analyzerService, IPageAnalyzerService pageAnalyzerService, ISummarizationService summarizationService) {
        this.rawAnalyzerService = analyzerService;
        this.pageAnalyzerService = pageAnalyzerService;
        this.summarizationService = summarizationService;
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
    public @ResponseBody List<AnalysisResult> analyze(@RequestBody Map<String, Object> data){
        List<AnalysisResult> results = new ArrayList<>();


        try {
            for (String text : (List<String>)data.get("text")){

                ICoreNLPAnalyzer analyzer = rawAnalyzerService.getAnalyzer(text);
                analyzer.setUseLDA(true);

                Thread thread = new Thread(analyzer);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException ignored){
                } finally {
                    rawAnalyzerService.pushAnalyzer(analyzer.getAnnotator());
                }


                results.add(analyzer.getResult());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return results;
    }

    @PostMapping(path = "/single_analyze/summary")
    public List<Map<String, String>> summaryEndpoint(@RequestBody Map<String, Object> input){
        List<Map<String, String>> result = new ArrayList<>();

        try {
            for (String text : (List<String>)input.get("text")){
                Map<String, String> res = new HashMap<>();
                res.put("summary", summarizationService.getSummary(text));
                res.put("keywords", summarizationService.getTags(text));
                result.add(res);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
    }

    @GetMapping("/echo")
    public @ResponseBody String echo(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }
}
