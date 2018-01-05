package com.process.cricketarticle;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Apratim on 04-Sep-14.
 */
public class ParseMatchCommentary {

    static String  cricketArticlesPath;
    static StanfordLemmatizer sL;

    static void parseMatchCommentary(String path,StanfordLemmatizer sLlocal) {

        sL=sLlocal;
        cricketArticlesPath = path;

        parseMatchCommentaryUtility(39,GlobalVariable.inning1Comm);
        parseMatchCommentaryUtility(21,GlobalVariable.inning2Comm);

    }

    private static void parseMatchCommentaryUtility(int matchNumber,String inning) {

        Document doc;
        try {
            String fullHtmlFilePath = cricketArticlesPath+"\\match"+matchNumber+inning;
            File input = new File(fullHtmlFilePath);
            System.out.println("----->"+fullHtmlFilePath);
            doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

            // get page title
            String title = doc.title();
            System.out.println("Title :\n " + title+"\n");

            int flag=0;
            System.out.println("Match Details Ball By Ball :\n");
            for (Element table : doc.select("table")) {

                //System.out.println("***************************************");
                for (Element row : table.select("tr")) {

                  Iterator itr =  row.select("p[class=commsText]").iterator();
                    while(itr.hasNext())
                    {
                        Element paraOut = (Element)itr.next();
                        String paraContent = paraOut.text();
                        //System.out.println("&&&&&"+paraContent);
                        if(checkWhetherNumber(paraContent))
                        {
                            flag = 1;
                            System.out.println("Over.Ball-->"+paraContent);
                               Element paraIn =  (Element)itr.next();
                                String ballDetails = paraIn.text();
                                System.out.println("Ball Detail--->"+ballDetails);

                                if(!paraIn.select("span[class=commsImportant").isEmpty())
                                {
                                    String impBallDetail = paraIn.select("span[class=commsImportant").text();
                                    System.out.println("Important Ball --->"+impBallDetail);
                                }
                        }
                    }
                    if(flag == 1)
                        break;
                }

                if(flag == 1)
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkWhetherNumber(String paraContent) {

        try{
            Float.parseFloat(paraContent);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }


    private static void stanfordTagger(String text) {
        System.out.println("Tagging para------------->");
        //StanfordLemmatizer sL = new StanfordLemmatizer();
        List<CoreMap> sentences = sL.stanfordLammetizer(text);
        for (CoreMap sentence : sentences) {
            System.out.println("---->Sentence------->");
            System.out.println(sentence);
            System.out.println("___________________________________");

            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String origWord = token.originalText();
                String rootWord = token.lemma();
                String posTag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                System.out.println("OrigWord:"+origWord+"-----  RootWord:" + rootWord + "-----  posTag:" + posTag);
            }
        }
        System.out.println("End Tagging--------------->");

    }
}
