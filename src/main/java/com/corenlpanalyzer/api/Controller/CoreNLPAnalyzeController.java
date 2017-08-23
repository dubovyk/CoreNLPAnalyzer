package com.corenlpanalyzer.api.Controller;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.PageAnalysisResult;
import com.corenlpanalyzer.api.NLP.Entities.TopicExtractionResult;
import com.corenlpanalyzer.api.NLP.Runnables.ICoreNLPAnalyzer;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageAnalyzerService;
import com.corenlpanalyzer.api.Service.ISummarizationService;
import com.corenlpanalyzer.api.Service.ITopicExtractionService;
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
    private final ITopicExtractionService topicExtractionService;

    @Autowired
    public CoreNLPAnalyzeController(ICoreNLPAnalyzerService analyzerService, IPageAnalyzerService pageAnalyzerService, ISummarizationService summarizationService, ITopicExtractionService topicExtractionService) {
        this.rawAnalyzerService = analyzerService;
        this.pageAnalyzerService = pageAnalyzerService;
        this.summarizationService = summarizationService;
        this.topicExtractionService = topicExtractionService;
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
                analyzer.setUseSummarizer(true);

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
                res.put("keywords", summarizationService.getKeywords(text));
                result.add(res);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
    }

    @PostMapping(path = "/single_analyze/topics")
    public List<TopicExtractionResult> topicsEndpoint(@RequestBody Map<String, Object> input){
        List<TopicExtractionResult> result = new ArrayList<>();

        try {
            for (String text : (List<String>)input.get("text")){
                result.add(topicExtractionService.getTopic(text));
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
