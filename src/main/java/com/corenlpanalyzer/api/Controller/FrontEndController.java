package com.corenlpanalyzer.api.Controller;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.PageAnalysisResult;
import com.corenlpanalyzer.api.Runnables.ICoreNLPAnalyzer;
import com.corenlpanalyzer.api.Runnables.Implementation.CoreNLPAnalyzer;
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

    @RequestMapping(value = {"/index_raw", "/", "/index", "/index.html"})
    public String index_raw(@RequestParam(value = "url", required = false) String link,
                            @RequestParam(value = "raw_text", required = false) String text,
                            Model model){
        PageAnalysisResult resultPage;
        AnalysisResult resultText;
        if ((link != null && link.equals("")) || (text != null && text.equals(""))){
            model.addAttribute("info", 1);
            return "index";
        }
        if (link != null){
            resultPage = iPageAnalyzerService.score(link);
            model.addAttribute("whole", 1);
            model.addAttribute("title", 1);
            model.addAttribute("meta", 1);
            model.addAttribute("body", 1);
            model.addAttribute("body_text", resultPage.getBodyAnalysisResult().getTargetText());
            model.addAttribute("body_sentiment", resultPage.getBodyAnalysisResult().getBodyEmotionsCoefficient());
            model.addAttribute("body_parse_tree", resultPage.getBodyAnalysisResult().getParseTree());
            model.addAttribute("body_sentence_num", resultPage.getBodyAnalysisResult().getSentenceCount());
            model.addAttribute("body_word_num", resultPage.getBodyAnalysisResult().getWordCount());
            model.addAttribute("body_words_sentence", resultPage.getBodyAnalysisResult().getWordsPerSentence());
            model.addAttribute("body_coref_chains", resultPage.getBodyAnalysisResult().corefChainsListToString());
            model.addAttribute("body_ner_person", resultPage.getBodyAnalysisResult().getNamedEntititesAsString("PERSON"));
            model.addAttribute("body_ner_location", resultPage.getBodyAnalysisResult().getNamedEntititesAsString("LOCATION"));
            model.addAttribute("body_ner_organization", resultPage.getBodyAnalysisResult().getNamedEntititesAsString("ORGANIZATION"));
            model.addAttribute("body_ner_misc", resultPage.getBodyAnalysisResult().getNamedEntititesAsString("MISC"));


            model.addAttribute("title_text", resultPage.getTitleAnalysisResult().getTargetText());
            model.addAttribute("title_sentiment", resultPage.getTitleAnalysisResult().getBodyEmotionsCoefficient());
            model.addAttribute("title_parse_tree", resultPage.getTitleAnalysisResult().getParseTree());
            model.addAttribute("title_sentence_num", resultPage.getTitleAnalysisResult().getSentenceCount());
            model.addAttribute("title_word_num", resultPage.getTitleAnalysisResult().getWordCount());
            model.addAttribute("title_words_sentence", resultPage.getTitleAnalysisResult().getWordsPerSentence());
            model.addAttribute("title_coref_chains", resultPage.getTitleAnalysisResult().corefChainsListToString());
            model.addAttribute("title_ner_person", resultPage.getTitleAnalysisResult().getNamedEntititesAsString("PERSON"));
            model.addAttribute("title_ner_location", resultPage.getTitleAnalysisResult().getNamedEntititesAsString("LOCATION"));
            model.addAttribute("title_ner_organization", resultPage.getTitleAnalysisResult().getNamedEntititesAsString("ORGANIZATION"));
            model.addAttribute("title_ner_misc", resultPage.getTitleAnalysisResult().getNamedEntititesAsString("MISC"));

            model.addAttribute("meta_text", resultPage.getMetaAnalysisResult().getTargetText());
            model.addAttribute("meta_sentiment", resultPage.getMetaAnalysisResult().getBodyEmotionsCoefficient());
            model.addAttribute("meta_parse_tree", resultPage.getMetaAnalysisResult().getParseTree());
            model.addAttribute("meta_sentence_num", resultPage.getMetaAnalysisResult().getSentenceCount());
            model.addAttribute("meta_word_num", resultPage.getMetaAnalysisResult().getWordCount());
            model.addAttribute("meta_words_sentence", resultPage.getMetaAnalysisResult().getWordsPerSentence());
            model.addAttribute("meta_coref_chains", resultPage.getMetaAnalysisResult().corefChainsListToString());
            model.addAttribute("meta_ner_person", resultPage.getMetaAnalysisResult().getNamedEntititesAsString("PERSON"));
            model.addAttribute("meta_ner_location", resultPage.getMetaAnalysisResult().getNamedEntititesAsString("LOCATION"));
            model.addAttribute("meta_ner_organization", resultPage.getMetaAnalysisResult().getNamedEntititesAsString("ORGANIZATION"));
            model.addAttribute("meta_ner_misc", resultPage.getMetaAnalysisResult().getNamedEntititesAsString("MISC"));

            model.addAttribute("text", resultPage.getWholePageAnalysisResult().getTargetText());
            model.addAttribute("sentiment", resultPage.getWholePageAnalysisResult().getBodyEmotionsCoefficient());
            model.addAttribute("parse_tree", resultPage.getWholePageAnalysisResult().getParseTree());
            model.addAttribute("sentence_num", resultPage.getWholePageAnalysisResult().getSentenceCount());
            model.addAttribute("word_num", resultPage.getWholePageAnalysisResult().getWordCount());
            model.addAttribute("words_sentence", resultPage.getWholePageAnalysisResult().getWordsPerSentence());
            model.addAttribute("coref_chains", resultPage.getWholePageAnalysisResult().corefChainsListToString());
            model.addAttribute("ner_person", resultPage.getWholePageAnalysisResult().getNamedEntititesAsString("PERSON"));
            model.addAttribute("ner_location", resultPage.getWholePageAnalysisResult().getNamedEntititesAsString("LOCATION"));
            model.addAttribute("ner_organization", resultPage.getWholePageAnalysisResult().getNamedEntititesAsString("ORGANIZATION"));
            model.addAttribute("ner_misc", resultPage.getWholePageAnalysisResult().getNamedEntititesAsString("MISC"));
        } else if (text != null){

            ICoreNLPAnalyzer analyzer = coreNLPAnalyzerService.getAnalyzer(text);
            analyzer.setUseLDA(true);

            Thread thread = new Thread(analyzer);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ignored){
            }

            coreNLPAnalyzerService.pushAnalyzer(analyzer.getAnnotator());
            resultText = analyzer.getResult();
            model.addAttribute("whole", 1);
            model.addAttribute("visible", "visibility: visible;");
            model.addAttribute("text", resultText.getTargetText());
            model.addAttribute("sentiment", resultText.getBodyEmotionsCoefficient());
            model.addAttribute("parse_tree", resultText.getParseTree());
            model.addAttribute("sentence_num", resultText.getSentenceCount());
            model.addAttribute("word_num", resultText.getWordCount());
            model.addAttribute("words_sentence", resultText.getWordsPerSentence());
            model.addAttribute("coref_chains", resultText.corefChainsListToString());
            model.addAttribute("ner_person", resultText.getNamedEntititesAsString("PERSON"));
            model.addAttribute("ner_location", resultText.getNamedEntititesAsString("LOCATION"));
            model.addAttribute("ner_organization", resultText.getNamedEntititesAsString("ORGANIZATION"));
            model.addAttribute("ner_misc", resultText.getNamedEntititesAsString("MISC"));
        } else {
            model.addAttribute("info", 1);
        }

        return "index";
    }
}