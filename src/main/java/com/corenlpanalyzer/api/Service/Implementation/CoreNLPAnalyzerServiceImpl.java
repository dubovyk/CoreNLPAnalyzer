package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.RawPageData;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerService;
import com.corenlpanalyzer.api.Service.IPageDataRetrievalService;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.coref.data.Mention;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

/**
 * This is an implementation of ICoreNLPAnalyzerService which works with
 * remote Stanford CoreNLP server.
 */
@SuppressWarnings("Duplicates")
@Service
public class CoreNLPAnalyzerServiceImpl implements ICoreNLPAnalyzerService {
    private final IPageDataRetrievalService pageDataRetrievalService;
    private StanfordCoreNLP pipeline;

    @Autowired
    public CoreNLPAnalyzerServiceImpl(IPageDataRetrievalService pageDataRetrievalService) {
        this.pageDataRetrievalService = pageDataRetrievalService;

        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse,mention, coref, sentiment");
        properties.setProperty("outputFormat", "json");
        this.pipeline = new StanfordCoreNLP(properties);
    }

    /**
     * This method analyzes a scraped web-page with a
     * method for plain-text analysis.
     *
     * @param data RawPageData instance for page to be analyzed.
     * @return An instance of AnalysisResult with analysis data.
     */
    @Override
    public AnalysisResult score(RawPageData data) {
        AnalysisResult result = score(data.getBodyText());
        return result;
    }


    @Override
    public AnalysisResult score(String rawText){
        AnalysisResult result = new AnalysisResult();
        result.setBodyEmotionsCoefficient(1);
        result.setTargetText(rawText);
        Annotation doc = new Annotation(rawText);
        pipeline.annotate(doc);
        //pipeline.prettyPrint(doc, System.out);
        List<CoreMap> sentences = doc.get(CoreAnnotations.SentencesAnnotation.class);
        if (sentences == null){
            return result;
        }
        int words = 0;
        int sentences_num = 0;
        int main_sentiment = 0;
        int longest = 0;
        for (CoreMap sentence : sentences){
            /*System.out.println("The first sentence is:");
            System.out.println(sentence.toShorterString());
            System.out.println("The first sentence tokens are:");
            for (CoreMap token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                System.out.println(token.toShorterString());
            }*/

            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            result.appendParseTree(tree.toString().replace("null", ""));
            Sentence s = new Sentence(sentence);
            words += s.length();
            sentences_num += 1;
            Tree sentimentTree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(sentimentTree);
            String part = sentence.toString();
            if(part.length() > longest){
                longest = part.length();
                main_sentiment = sentiment;
            }

        }

        result.setCorefChains(doc.get(CorefCoreAnnotations.CorefChainAnnotation.class).values());

        result.setWordCount(words);
        result.setSentenceCount(sentences_num);
        result.setWordsPerSentence((sentences_num > 0) ?(float)words/(float)sentences_num : 0);
        result.setBodyEmotionsCoefficient(main_sentiment);
        return result;
    }
}
