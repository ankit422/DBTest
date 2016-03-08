package com.dbtest;

/**
 * Created by ankit on 08/03/16.
 */
public class ModelWords {
    private Words[] words;

    private String version;

    public Words[] getWords ()
    {
        return words;
    }

    public void setWords (Words[] words)
    {
        this.words = words;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [words = "+words+", version = "+version+"]";
    }
}
