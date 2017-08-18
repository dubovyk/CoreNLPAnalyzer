package com.corenlpanalyzer.api;

import com.corenlpanalyzer.api.Utils.CoreNLPAnnotatorPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main extends SpringApplication{
    public static void main(String... args){
        CoreNLPAnnotatorPool.getInstance();
        SpringApplication.run(Main.class, args);
    }
}
