package com.corenlpanalyzer.api.Controller;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FrontEndController {
    private final IPageAnalyzerService iPageAnalyzerService;

    private final ICoreNLPAnalyzerService coreNLPAnalyzerService;

    @Autowired
    public FrontEndController(IPageAnalyzerService iPageAnalyzerService, ICoreNLPAnalyzerService coreNLPAnalyzerService) {
        this.iPageAnalyzerService = iPageAnalyzerService;
        this.coreNLPAnalyzerService = coreNLPAnalyzerService;
    }

    @RequestMapping(value = "/index_raw")
    public String index_raw(@RequestParam(value = "url", required = false) String link,
                            @RequestParam(value = "raw_text", required = false) String text,
                            Model model){
        AnalysisResult result;
        if (link != null){
            result = iPageAnalyzerService.score(link);
        } else if (text != null){
            result = coreNLPAnalyzerService.score(text);
        } else {
            model.addAttribute("visible", "hidden");
            model.addAttribute("text", "");
            model.addAttribute("text", "");
            model.addAttribute("sentiment", "");
            model.addAttribute("parse_tree", "");
            model.addAttribute("sentence_num", "");
            model.addAttribute("word_num", "");
            model.addAttribute("words_sentence", "");
            model.addAttribute("coref_chains", "");
            return "index";
        }
        model.addAttribute("visible", "visible");
        model.addAttribute("text", result.getTargetText());
        model.addAttribute("sentiment", result.getBodyEmotionsCoefficient());
        model.addAttribute("parse_tree", result.getParseTree());
        model.addAttribute("sentence_num", result.getSentenceCount());
        model.addAttribute("word_num", result.getWordCount());
        model.addAttribute("words_sentence", result.getWordsPerSentence());
        model.addAttribute("coref_chains", result.corefChainsListToString());
        return "index";
    }
}
