package com.corenlpanalyzer.api.NLP.Entities;

public enum SentimentValuesEnum {
    VERY_NEGATIVE,
    NEGATIVE,
    NEUTRAL,
    POSITIVE,
    VERY_POSITIVE;

    public int getValue(){
        switch (this){
            case VERY_NEGATIVE:
                return 1;
            case NEGATIVE:
                return 2;
            case NEUTRAL:
                return 3;
            case POSITIVE:
                return 4;
            case VERY_POSITIVE:
                return 5;
            default:
                return 0;
        }
    }

}
