package com.process.cricketarticle;
import java.util.*;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.util.CoreMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Apratim on 04-Sep-14.
 */
public class ParseMatchReport {

    static String  cricketArticlesPath;
    static StanfordLemmatizer sL;

    static HashSet<String> specialBattingKeyWords = new HashSet<String>();
    static HashSet<String> specialBowlingKeyWords = new HashSet<String>();

    private static void printAllCandidateMentionsExtracted() {

        for( SentenceParameters sP:GlobalVariable.matchSentences)
        {
            System.out.println("\n--------My FullSentence Is.........................\n");
            System.out.println(sP.fullSentence);

            for( int i =0 ; i<= sP.currentWorkingIndex;i++)
            {
                System.out.println("\n------------My Mention-------\n---------IsCandidate: "+sP.isCandidateMention.get(i)+"--------------");
                System.out.println("$$strongdHitWords: "+sP.strongdHitWordsTillHere.get(i).size()+":$$Numeric Words: "+sP.numericalNumbersTillHere.get(i).size());
                for (CoreLabel token:sP.fullCandidateMentionText.get(i))
                {
                       String origWord = token.originalText();
                    String rootWord = token.lemma();
                    String posTag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    //System.out.println("OrigWord:"+origWord+"-----  RootWord:" + rootWord + "-----  posTag:" + posTag);

                    System.out.print(origWord+" ");
                }
            }
            System.out.println();
        }
    }

    private static void printCandidateMentionForThisSentence(SentenceParameters sP) {

        for( int i =0 ; i<= sP.currentWorkingIndex;i++)
        {
            System.out.println("\n------------My Candidate Mention--------------");

            for (CoreLabel token:sP.fullCandidateMentionText.get(i))
            {
                String origWord = token.originalText();
                String rootWord = token.lemma();
                String posTag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                //System.out.println("OrigWord:"+origWord+"-----  RootWord:" + rootWord + "-----  posTag:" + posTag);

                System.out.print(origWord+" ");
            }
        }
        System.out.println();
    }

    private static boolean isDelimiter(CoreLabel token, SentenceParameters sP) {

        String origWord = token.originalText().trim();
        String del = token.lemma().toLowerCase();

        if(sP.bracketOpened == true)
        {
            return false;
        }

        return GlobalVariable.mentionDelemitersList.contains(del);

    }

    private static void checkThresholdUsingSentenceParameters(SentenceParameters sP) {
        int nounsCount = 0;
        int numericalNumbersCount = 0;
        int strongHitWordsCount = 0;
        int mildHitWordsCount = 0;

        nounsCount = sP.nounsTillHere.get(sP.currentWorkingIndex).size();
        numericalNumbersCount = sP.numericalNumbersTillHere.get(sP.currentWorkingIndex).size();
        strongHitWordsCount = sP.strongdHitWordsTillHere.get(sP.currentWorkingIndex).size();
        mildHitWordsCount = sP.mildHitWordsTillHere.get(sP.currentWorkingIndex).size();

        if( nounsCount > 0 && strongHitWordsCount > 0)
        {
            sP.isCandidateMention.add(sP.currentWorkingIndex,true);
            sP.currentWorkingIndex++;
            return;
        }

        if( nounsCount > 0 && numericalNumbersCount > 0)
        {
            sP.isCandidateMention.add(sP.currentWorkingIndex,true);
            sP.currentWorkingIndex++;
            return;
        }
    }


    private static void addValueToParameter(CoreLabel token, SentenceParameters sP) {
        String origWord = token.originalText().trim().toLowerCase();
        String rootWord = token.lemma().trim().toLowerCase();
        String posTag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
        //System.out.println("\n~~~~~~~~~~~~~"+origWord+"~~~~~~~~");

        token.setLemma(rootWord);
        token.setOriginalText(origWord);

        if(token.lemma().equals("bowl"))
        {
            token.setLemma(origWord);
        }

        sP.fullCandidateMentionText.get(sP.currentWorkingIndex).add(token);

        String str;
        Integer in;

        str = ParameterVerifier.strongHitWordsVerifier(sP, token);
        if(str != null)
        {
            sP.strongdHitWordsTillHere.get(sP.currentWorkingIndex).add(str);
            //return;
        }

        str = ParameterVerifier.nounVerifier(sP,token);
        if(str != null)
        {
            sP.nounsTillHere.get(sP.currentWorkingIndex).add(str);
            //return;
        }

        in = ParameterVerifier.numericVerifier(sP, token);
        if(in != null)
        {
            sP.numericalNumbersTillHere.get(sP.currentWorkingIndex).add(in);
            //return;
        }

        str = ParameterVerifier.mildHitWordsVerifier(sP, token);
        if(str != null)
        {
            sP.mildHitWordsTillHere.get(sP.currentWorkingIndex).add(str);
            //return;
        }

        if(origWord.equals("("))
        {
            sP.bracketOpened = true;
            sP.commaEncountered = false;
            //System.out.println("\n-------------------------Root: "+origWord+",val: "+sP.bracketInQueue);
        }
        else if(origWord.equals(")"))
        {
            sP.bracketOpened = false;
            //System.out.println("\n-------------------------Root: "+origWord+",val: "+sP.bracketInQueue);
        }
        else if( sP.bracketOpened == true && origWord.equals(","))
        {
            sP.commaEncountered = true;
            //System.out.println("\n-------------------------Root: "+origWord+",val: "+sP.bracketInQueue);
        }
    }

    private static void processCurrentToken(CoreLabel token, SentenceParameters sP) {

        if ( sP.fullCandidateMentionText.size()-1 < sP.currentWorkingIndex )
        {
            sP.bracketOpened = false;
            sP.commaEncountered = false;
            sP.isCandidateMention.add(sP.currentWorkingIndex,false);
            sP.fullCandidateMentionText.add(sP.currentWorkingIndex,new Vector<CoreLabel>());
            sP.nounsTillHere.add(sP.currentWorkingIndex,new Vector<String>());
            sP.numericalNumbersTillHere.add(sP.currentWorkingIndex,new Vector<Integer>());
            sP.strongdHitWordsTillHere.add(sP.currentWorkingIndex,new Vector<String>());
            sP.mildHitWordsTillHere.add(sP.currentWorkingIndex,new Vector<String>());
        }

        addValueToParameter(token, sP);

        if( isDelimiter(token,sP) == true )
        {
            checkThresholdUsingSentenceParameters(sP);
        }
    }

    private static void taggingAndPreProcessingPara(String text) {

        List<CoreMap> sentences = sL.stanfordLammetizer(text);

        for (CoreMap sentence : sentences) {

            GlobalVariable.matchSentences.add(GlobalVariable.matchSentences.size(), new SentenceParameters());
            SentenceParameters sP = GlobalVariable.matchSentences.get(GlobalVariable.matchSentences.size() - 1) ;
            sP.fullSentence = sentence.toString();
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                processCurrentToken(token,sP);
            }
            sP.currentWorkingIndex = sP.fullCandidateMentionText.size()-1;
            checkThresholdUsingSentenceParameters(sP);
            sP.currentWorkingIndex = sP.fullCandidateMentionText.size()-1;
        }
    }

    private static void parseMatchReportUtility(int matchNumber) {
        GlobalVariable.matchSentences.clear();

        Document doc;
        try {
            //System.out.println(cricketArticlesPath);
            String fullHtmlFilePath = cricketArticlesPath+"\\match"+matchNumber+GlobalVariable.report;
            File input = new File(fullHtmlFilePath);
            //System.out.println("----->"+fullHtmlFilePath);
            doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

            // get page title
            //String title = doc.title();
            //System.out.println("title : " + title);

            // get all paragraphs
            Elements paras = doc.select("p[class=news-body]");
            for (Element para : paras) {
                //System.out.println(para.text());
                //System.out.println();
               //System.out.println("Start paragraph body.......................");
                // get the value from "news-body" class
               //System.out.println("Original Para-------> :\n " + para.text()+"\n");
                //System.out.println("..........................Tagging Para.................");
                taggingAndPreProcessingPara(para.text());
               //System.out.println("------------------------End Tagging--------------->");
                //System.out.println("$$" + para.text());
                //break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        printAllCandidateMentionsExtracted();
    }

    private static void initialiseSpecialBattingKeyWords() {
        specialBattingKeyWords.add("bat");
        specialBattingKeyWords.add("score");
    }

    private static void initialiseSpecialBowlingKeyWords() {
        specialBowlingKeyWords.add("bowl");
        specialBowlingKeyWords.add("wicket");
    }

    static void parseMatchReport(String path,StanfordLemmatizer sLlocal) {

        sL=sLlocal;
        cricketArticlesPath = path;

        initialiseSpecialBattingKeyWords();
        initialiseSpecialBowlingKeyWords();

        parseMatchReportUtility(1);
        //System.out.println(testInstance.wordtonum("eighty"));
    }
}

