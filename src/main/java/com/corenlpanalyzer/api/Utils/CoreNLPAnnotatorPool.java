package com.corenlpanalyzer.api.Utils;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CoreNLPAnnotatorPool {
    private final AtomicReference<Stack<StanfordCoreNLP>> pipelineStack;
    private static final AtomicInteger THREAD_NUM = new AtomicInteger(1);


    private static CoreNLPAnnotatorPool ourInstance = new CoreNLPAnnotatorPool();

    public static CoreNLPAnnotatorPool getInstance() {
        return ourInstance;
    }

    private CoreNLPAnnotatorPool() {
        pipelineStack = new AtomicReference<>(new Stack<>());

        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse,mention, coref, sentiment");
        properties.setProperty("outputFormat", "json");

        for(int i = 0; i < THREAD_NUM.get(); i++){
            pipelineStack.get().push(new StanfordCoreNLP(properties));
        }
    }

    public StanfordCoreNLP getAnnotator(){
        System.out.println(String.format("%d annotators available", pipelineStack.get().size()));

        synchronized (pipelineStack.get()){
            if (pipelineStack.get().size() == 0){
                return null;
            }
            StanfordCoreNLP coreNLP = pipelineStack.get().pop();
            System.out.println(String.format("Will return annotator now."));
            return coreNLP;
        }


    }

    public void pushAnalyzer(StanfordCoreNLP coreNLP){
        synchronized (pipelineStack.get()){
            pipelineStack.get().push(coreNLP);
        }
    }
}
