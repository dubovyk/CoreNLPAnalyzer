package com.corenlpanalyzer.api.Runnables.Implementation;

import com.corenlpanalyzer.api.Domain.AnalysisResult;
import com.corenlpanalyzer.api.Domain.SentimentValuesEnum;
import com.corenlpanalyzer.api.Runnables.ICoreNLPAnalyzer;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;

@SuppressWarnings("Duplicates")
public class CoreNLPAnalyzer implements ICoreNLPAnalyzer{
    private AnalysisResult result;
    private String rawText;
    private StanfordCoreNLP coreNLP;

    public CoreNLPAnalyzer(){
    }

    public CoreNLPAnalyzer(StanfordCoreNLP coreNLP){
        this.coreNLP = coreNLP;
    }

    @Override
    public void run(){
        analyze();
    }

    @Override
    public AnalysisResult getResult(){
        return result;
    }

    @Override
    public void setAnnotator(StanfordCoreNLP coreNLP){
        this.coreNLP = coreNLP;
    }

    @Override
    public StanfordCoreNLP getAnnotator(){
        return this.coreNLP;
    }

    private void analyze(){
        int words = 0;
        int sentences_num = 0;
        int total_sentiment = 0;

        Map<String, List<String>> NERtags = new HashMap<>();

        NERtags.put("PERSON", new ArrayList<>());
        NERtags.put("LOCATION", new ArrayList<>());
        NERtags.put("ORGANIZATION", new ArrayList<>());
        NERtags.put("MISC", new ArrayList<>());

        result = new AnalysisResult();
        result.setTargetText(rawText);

        Annotation doc = new Annotation(rawText);

        coreNLP.annotate(doc);

        List<CoreMap> sentences = doc.get(CoreAnnotations.SentencesAnnotation.class);
        if (sentences == null){
            return;
        }

        String nerClass = "";
        StringBuffer ner;

        for (CoreMap sentence : sentences){
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            result.appendParseTree(tree.toString().replace("null", ""));
            Sentence s = new Sentence(sentence);
            words += s.length();
            sentences_num += 1;
            total_sentiment += getTypeOfEmotion(sentence.get(SentimentCoreAnnotations.SentimentClass.class)).getValue();
            ner = new StringBuffer();


            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)){
                if ((token.value() != null)
                        && (token.get(CoreAnnotations.NamedEntityTagAnnotation.class) != null)){
                    if (!token.get(CoreAnnotations.NamedEntityTagAnnotation.class).equals("O")){
                        NERtags.computeIfAbsent(token.get(CoreAnnotations.NamedEntityTagAnnotation.class), k -> new ArrayList<>());

                        if (!NERtags.get(token.get(CoreAnnotations.NamedEntityTagAnnotation.class)).contains(token.value())){
                            if (nerClass.equals(token.get(CoreAnnotations.NamedEntityTagAnnotation.class))){
                                ner.append(token.value()).append(" ");
                            } else {
                                if (NERtags.keySet().contains(nerClass) && !ner.toString().equals("")){
                                    NERtags.get(nerClass).add(ner.toString().trim());
                                }
                                nerClass = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                                ner = new StringBuffer();
                                ner.append(token.value()).append(" ");
                            }
                        }
                    } else {
                        if (!ner.toString().equals("")){
                            NERtags.get(nerClass).add(ner.toString().trim());
                            ner = new StringBuffer();
                        }
                        nerClass = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                    }
                }
            }
        }

        List<CorefChain> chains = getCorefChains(doc);
        result.setCorefChains(chains);

        result.setWordCount(words);
        result.setSentenceCount(sentences_num);
        result.setWordsPerSentence((sentences_num > 0) ?(float)words/(float)sentences_num : 0);
        result.setBodyEmotionsCoefficient((sentences_num > 0) ? ((double) total_sentiment / (double) sentences_num) : 0);

        result.setNERentities(NERtags);
    }

    private List<CorefChain> getCorefChains(Annotation doc){
        List<CorefChain> chains = new ArrayList<>(doc.get(CorefCoreAnnotations.CorefChainAnnotation.class).values());
        chains.sort((o1, o2) -> o2.getMentionsInTextualOrder().size() - o1.getMentionsInTextualOrder().size());
        return chains;
    }

    private SentimentValuesEnum getTypeOfEmotion(String type){
        if (type.equals("Neutral")){
            return SentimentValuesEnum.NEGATIVE;
        } else if (type.equals("Positive")){
            return SentimentValuesEnum.POSITIVE;
        } else if (type.equals("Very positive")){
            return SentimentValuesEnum.VERY_POSITIVE;
        } else if (type.equals("Negative")){
            return SentimentValuesEnum.NEGATIVE;
        } else {
            return SentimentValuesEnum.VERY_NEGATIVE;
        }
    }

    public String getRawText() {
        return rawText;
    }

    @Override
    public void setRawText(String rawText) {
        this.rawText = rawText;
    }
}
