package com.corenlpanalyzer.api.Controller;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.PageAnalysisResult;
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
        PageAnalysisResult resultPage;
        AnalysisResult resultText;
        if (link != null){
            resultPage = iPageAnalyzerService.score(link);
            model.addAttribute("visible", "visibility: visible;");
            model.addAttribute("text", resultPage.getBodyAnalysisResult().getTargetText());
            model.addAttribute("sentiment", resultPage.getBodyAnalysisResult().getBodyEmotionsCoefficient());
            model.addAttribute("parse_tree", resultPage.getBodyAnalysisResult().getParseTree());
            model.addAttribute("sentence_num", resultPage.getBodyAnalysisResult().getSentenceCount());
            model.addAttribute("word_num", resultPage.getBodyAnalysisResult().getWordCount());
            model.addAttribute("words_sentence", resultPage.getBodyAnalysisResult().getWordsPerSentence());
            model.addAttribute("coref_chains", resultPage.getBodyAnalysisResult().corefChainsListToString());

            model.addAttribute("title_text", resultPage.getTitleAnalysisResult().getTargetText());
            model.addAttribute("title_sentiment", resultPage.getTitleAnalysisResult().getBodyEmotionsCoefficient());
            model.addAttribute("title_parse_tree", resultPage.getTitleAnalysisResult().getParseTree());
            model.addAttribute("title_sentence_num", resultPage.getTitleAnalysisResult().getSentenceCount());
            model.addAttribute("title_word_num", resultPage.getTitleAnalysisResult().getWordCount());
            model.addAttribute("title_words_sentence", resultPage.getTitleAnalysisResult().getWordsPerSentence());
            model.addAttribute("title_coref_chains", resultPage.getTitleAnalysisResult().corefChainsListToString());

            model.addAttribute("meta_text", resultPage.getMetaAnalysisResult().getTargetText());
            model.addAttribute("meta_sentiment", resultPage.getMetaAnalysisResult().getBodyEmotionsCoefficient());
            model.addAttribute("meta_parse_tree", resultPage.getMetaAnalysisResult().getParseTree());
            model.addAttribute("meta_sentence_num", resultPage.getMetaAnalysisResult().getSentenceCount());
            model.addAttribute("meta_word_num", resultPage.getMetaAnalysisResult().getWordCount());
            model.addAttribute("meta_words_sentence", resultPage.getMetaAnalysisResult().getWordsPerSentence());
            model.addAttribute("meta_coref_chains", resultPage.getMetaAnalysisResult().corefChainsListToString());
        } else if (text != null){
            resultText = coreNLPAnalyzerService.score(text);
            model.addAttribute("visible", "visibility: visible;");
            model.addAttribute("text", resultText.getTargetText());
            model.addAttribute("sentiment", resultText.getBodyEmotionsCoefficient());
            model.addAttribute("parse_tree", resultText.getParseTree());
            model.addAttribute("sentence_num", resultText.getSentenceCount());
            model.addAttribute("word_num", resultText.getWordCount());
            model.addAttribute("words_sentence", resultText.getWordsPerSentence());
            model.addAttribute("coref_chains", resultText.corefChainsListToString());
        } else {
            model.addAttribute("visible", "visibility: hidden;");
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

        return "index";
    }
}
