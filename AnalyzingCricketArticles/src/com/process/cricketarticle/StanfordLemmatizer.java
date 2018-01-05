package com.process.cricketarticle;

/**
 * Created by Apratim on 03-Sep-14.
 */

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public  class StanfordLemmatizer {

    // protected StanfordCoreNLP pipeline;
    public StanfordCoreNLP pipeline;

    public StanfordLemmatizer() {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        // dcoref,net,ner,parse
        pipeline = new StanfordCoreNLP(props);
    }

    public List<CoreMap> stanfordLammetizer(String text) {

        // read some text in the text variable
		/*
		 * String text = "the #fox jumped over the lazy dog.Good girl." + "\n" +
		 * "jpojojo";
		 */

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        // System.out.println("Annonating:" + document);
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and
        // has values with custom types

        // this is the parse tree of the current sentence
        // Tree tree = sentence.get(TreeAnnotation.class);


        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        return sentences;
		/*
		 * try { pipeline.xmlPrint(document, System.out); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
    }

    public static void main(String[] args) {
        StanfordLemmatizer sL = new StanfordLemmatizer();
        long startingtTime = System.currentTimeMillis();

       // String tweetText = "RT @DjBlack_Pearl: wat muhfuckaz running for party?????";
       //String tweetText =  "What a World Cup England are producing. From a thrilling tie against India to the shock of losing to Ireland they have now conjured a stunning fightback to beat South Africa by six runs in a gripping contest on a tough pitch in Chennai. They took all ten wickets for 102 through a combination of spin, reverse swing, perseverance and the never-say-die-attitude which is such a trait of this team, with Stuart Broad sealing the victory with two wickets in four balls after Dale Steyn's 31-ball 20 had taken his team close to the winning line.";

        String tweetText ="half-centuries";

        List<CoreMap> sentences = sL.stanfordLammetizer(tweetText);
        for (CoreMap sentence : sentences) {
            System.out.println("---->Sentence------->");
            System.out.println(sentence);
            System.out.println("___________________________________");
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String origWord = token.originalText();
                String rootWord = token.lemma();
                String posTag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                System.out.println("OrigWord:  "+origWord+"-----RootWord:  " + rootWord + "-----posTag:  " + posTag);
            }
        }
        long endingTime = System.currentTimeMillis();
        System.out.println("Total Time:" + (endingTime - startingtTime) / 1000);
    }
}