package com.corenlpanalyzer.api;

import cc.mallet.pipe.*;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.*;
import com.corenlpanalyzer.api.NLP.TopicAnalyzer;
import com.corenlpanalyzer.api.NLP.TopicExtractionResult;
import com.corenlpanalyzer.api.Utils.CoreNLPAnnotatorPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.*;
import java.util.regex.Pattern;


@SpringBootApplication
public class Main extends SpringApplication{
    public static void main(String... args) throws Exception{

        CoreNLPAnnotatorPool.getInstance();
        SpringApplication.run(Main.class, args);

/*        String text = "Naturalist, artist, activist, adventurer and now published author, Obi Kaufmann melds paintbrush and compass in the richly illustrated California Field Atlas to reveal the beautiful landscapes and ecosystems of our fair state. Kaufmann set out to uncover the secrets of the Golden State and provide us an artist's and poet's rendering of his best finds. A Bay Area native, he has been imparting his naturalist eye to drawings and painting ever since he was a little boy growing up on Mt. Diablo. \"I would map the trails in the sage mazes and I would draw the animals obsessively, all the while feeling a real kinship to them.\" This 600-page volume features 250 hand-painted maps and 600 other works of art that Kaufmann has amassed during his lifetime of connecting with nature. \"It is such a massive project that I know that from here on out my life will be forever defined by it. There will be everything before the California Field Atlas and then there will be everything after.\" The paintings find a real truth not only in the land, but in the animals he interacts with. Accurate presentations of wildlife, to him, are as integral to understanding the California landscape as his geographical representations. \"Artistically, there's almost a topographic relevance to the well-rendered animal—it's still a kind of mapped beauty.\" Kaufmann is clear that this isn't the book to turn to when lost in the woods or trying to figure out how to rough it in Yosemite, but encourages you to dive deep into its pages if you're looking for a \"handbook for conservation\" or \"an indispensable companion for the classic California road trip.\" And while the paintings and maps are meaningful to anyone who identifies as a Californian, they belie the reality of the state of the wilderness: \"The natural world in California is in need of some major love and caring from humans,\" says Kaufmann. \"Every natural system in California is living and deserves respect. This whole book—this whole endeavor—is an exercise in how we can continue our human residency in California not only over the next 100 years, for example, but over the next 10,000 years,\" he says. \"California will keep taking care of us if we take care of it.\" What became quickly apparent is the artist's absolute and unwavering belief that there is hope for nature in California. The last chapter of his book, \"The Rewilded Future,\" presents an alternate view for reconnecting the wilderness, reintroducing lost species and expanding our landscape.\"Give me my dying days in the High Sierra and it will be happy death,\" says Kaufmann, who can be found working at his storefront studio, Premium Arts (at 4130 Broadway) in Oakland. California Field Atlas ($45) is now available for preorders at heydaybooks.com. Beautiful illustrations from Obi Kaufmann's new book The California Field Atlas";

        TopicAnalyzer analyzer = new TopicAnalyzer();
        TopicExtractionResult result = analyzer.analyze(text, 10, 5);*/
    }
}
