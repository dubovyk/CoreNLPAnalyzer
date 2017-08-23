package com.corenlpanalyzer.api;

import com.corenlpanalyzer.api.NLP.Summarizer.Summarizer;
import com.corenlpanalyzer.api.NLP.Summarizer.summarizer.DocumentSummarizer;
import com.corenlpanalyzer.api.NLP.Summarizer.summarizer.KeywordExtractor;
import com.corenlpanalyzer.api.NLP.Summarizer.summarizer.SentencePreprocessor;
import com.corenlpanalyzer.api.NLP.Summarizer.summarizer.SentenceSegmenter;
import edu.stanford.nlp.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main extends SpringApplication{
    public static void main(String... args) throws Exception{
        //SpringApplication.run(Main.class, args);
        try {
            String filename = "in.txt";
            String content = IOUtils.slurpFile(filename);

            Summarizer summarizer = new Summarizer();
            summarizer.setText(content);

            String summary = summarizer.getSummary();
            String keywords = summarizer.getKeywords();
            System.out.println(summary);
            System.out.println("\n\n\n\n");
            System.out.println(keywords);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
