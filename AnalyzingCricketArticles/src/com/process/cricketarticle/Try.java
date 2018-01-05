package com.process.cricketarticle;

/**
 * Created by Apratim on 03-Sep-14.
 */

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import edu.stanford.nlp.ling.CoreLabel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Try {

    static String cricketArticlesPath;

    public static void main(String[] args) {
    CoreLabel token = new CoreLabel();

        token.setLemma("fsddfds");
        token.setLemma("fs");

        System.out.println(token.lemma());
    String testcase = "das";
        testcase = testcase.substring(0,testcase.length()-2);
        System.out.println("----"+testcase+"\n");

//        if(args.length == 0)
//        {
//            System.out.println("You Must Provide File Path");
//            return;
//        }
//
//        cricketArticlesPath = args[0];
//        System.out.println("HTML File Path:"+cricketArticlesPath);
//
//        //parseMatchCommentary(21);
//        //parseMatchReport(21);
//        String sentence ="hii";
//        stanfordTagger(sentence);
    }
}
