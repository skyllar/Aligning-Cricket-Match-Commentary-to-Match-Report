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
import java.util.HashSet;
import java.util.List;

/**
 * Created by Apratim on 02-Sep-14.
 */

public class ProcessCricketArticles {

    static String cricketArticlesPath;
    static StanfordLemmatizer sL = new StanfordLemmatizer();
    static InNumerals5Digits testInstance = new InNumerals5Digits();

    public static void main(String []args)
    {
        if(args.length != 1)
        {
            System.out.println("You Must Provide File Path");
            return;
        }

        cricketArticlesPath = args[0];
        System.out.println("HTML File Path"+cricketArticlesPath);

        //System.out.println(testInstance.wordtonum("eighty"));

        ParseMatchReport.parseMatchReport(cricketArticlesPath,sL);
        //ParseMatchCommentary.parseMatchCommentary(cricketArticlesPath,sL);
    }

}
