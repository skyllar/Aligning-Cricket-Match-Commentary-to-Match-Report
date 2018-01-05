package com.process.cricketarticle;

import java.util.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Apratim on 03-Sep-14.
 */
public class GlobalVariable {

    static String cricketArticlesPath="D:\\Cse\\Development\\utility-AnalyzingCricketArticles\\cricket HTML files\\data";

    static String inning1Comm= "innings1Commentary.html";
    static String inning2Comm= "innings2Commentary.html";
    static String report ="report.html";

    static HashSet<String> mentionDelemitersList = new HashSet<String>(Arrays.asList(","));
    static Vector<SentenceParameters> matchSentences = new Vector<SentenceParameters>();

}
