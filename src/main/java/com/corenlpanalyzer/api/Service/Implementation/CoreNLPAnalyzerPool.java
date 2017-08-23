package com.corenlpanalyzer.api.Service.Implementation;

import com.corenlpanalyzer.api.NLP.Runnables.ICoreNLPAnalyzer;
import com.corenlpanalyzer.api.NLP.Runnables.Implementation.CoreNLPAnalyzer;
import com.corenlpanalyzer.api.Service.ICoreNLPAnalyzerPool;
import com.corenlpanalyzer.api.Service.ISummarizationService;
import com.corenlpanalyzer.api.Service.ITopicExtractionService;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CoreNLPAnalyzerPool implements ICoreNLPAnalyzerPool{

    private final ITopicExtractionService topicExtractionService;
    private final ISummarizationService summarizationService;

    private final AtomicReference<Stack<StanfordCoreNLP>> pipelineStack;
    private static final AtomicInteger THREAD_NUM = new AtomicInteger(2);

    @Autowired
    private CoreNLPAnalyzerPool(ITopicExtractionService topicExtractionService, ISummarizationService summarizationService) {
        this.topicExtractionService = topicExtractionService;
        this.summarizationService = summarizationService;
        pipelineStack = new AtomicReference<>(new Stack<>());

        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse,mention, coref, sentiment");
        properties.setProperty("outputFormat", "json");

        for(int i = 0; i < THREAD_NUM.get(); i++){
            pipelineStack.get().push(new StanfordCoreNLP(properties));
        }
    }

    public ICoreNLPAnalyzer getAnalyzer(String rawText){
        ICoreNLPAnalyzer analyzer = new CoreNLPAnalyzer(topicExtractionService, summarizationService);
        analyzer.setRawText(rawText);

        System.out.println(String.format("%d annotators available", pipelineStack.get().size()));

        while (pipelineStack.get().size() == 0){
            try {
                wait(100);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(String.format("Will return annotator now."));
        synchronized (pipelineStack.get()){
            StanfordCoreNLP coreNLP = pipelineStack.get().pop();
            analyzer.setAnnotator(coreNLP);
            System.out.println(String.format("Returned annotator now."));
        }

        return analyzer;
    }

    public void pushAnalyzer(StanfordCoreNLP coreNLP){
        synchronized (pipelineStack.get()){
            pipelineStack.get().push(coreNLP);
        }
    }
}
