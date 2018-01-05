package com.process.cricketarticle;

/**
 * Created by Apratim on 16-Nov-14.
 */
import edu.stanford.nlp.ling.CoreLabel;

import java.util.*;

public class SentenceParameters extends CoreLabel{

    int sentenceSize;
    int currentWorkingIndex=0;
    public void SentenceParameters(int sentenceSize)
    {
        this.sentenceSize = sentenceSize;
    }

    public boolean  bracketOpened;
    public boolean  commaEncountered;

    public String fullSentence= null;

    public  Vector<Boolean> isCandidateMention = new Vector<Boolean>();
    public Vector<Vector<CoreLabel>> fullCandidateMentionText= new  Vector<Vector<CoreLabel>>();
    public Vector< Vector< String > > nounsTillHere =new Vector< Vector< String > >();
    public Vector< Vector<Integer> > numericalNumbersTillHere = new Vector< Vector<Integer>>();
    public Vector<Vector<String>> strongdHitWordsTillHere = new Vector<Vector<String>>();
    public Vector<Vector<String>> mildHitWordsTillHere = new Vector<Vector<String>>();
}
