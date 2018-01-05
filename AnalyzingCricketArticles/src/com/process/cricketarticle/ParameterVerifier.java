package com.process.cricketarticle;

import edu.stanford.nlp.ling.CoreLabel;

import java.util.Arrays;
import java.util.HashSet;

public class ParameterVerifier {

    static HashSet<String> strongHitWordsList = new HashSet<String>(Arrays.asList("bowled","wicket","run","ball","century","win","out","partnership","maiden","half-century","century"));
    static HashSet<String> mildHitWordsList = new HashSet<String>(Arrays.asList("maiden","","",""));

    public static String nounVerifier(SentenceParameters sP,CoreLabel token)
    {
        String lemma = token.lemma();
        //String orig = token.originalText();
        String posTag = token.tag();
        if( posTag.startsWith("NN"))
            return lemma;
        return null;
    }
    public static Integer numericVerifier(SentenceParameters sP,CoreLabel token)
    {
        String lemma = token.lemma();
        //String orig = token.originalText();
        //String posTag = token.tag();

        int result = InNumerals5Digits.getNumber(lemma);
        if( result != -1)
            return result;

        //very basic implementation
        return null;
    }
    public static String strongHitWordsVerifier(SentenceParameters sP,CoreLabel token)
    {
        String lemma = token.lemma();
       // String orig = token.originalText();
        //String posTag = token.tag();

        if( strongHitWordsList.contains(lemma))
            return lemma;

        return null;
    }
    public static String mildHitWordsVerifier(SentenceParameters sP,CoreLabel token)
    {

        String lemma = token.lemma();
       // String orig = token.originalText();
        //String posTag = token.tag();

        if( mildHitWordsList.contains(lemma))
            return lemma;

        return null;
    }
}
