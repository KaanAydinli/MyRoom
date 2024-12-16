package com.example.myrooms;
import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

    public ArrayList<PostIt> postItArrayList;
    public PostIt currentPostIt ;
    public int currentColor;

    public Board()
    {
        postItArrayList = new ArrayList<>();
    }

    public PostIt getCurrentPostIt()
    {
        return this.currentPostIt;
    }

    public ArrayList<PostIt> getPostItArrayList()
    {
        return this.postItArrayList;
    }

    public void deletePostIt(PostIt postIt)
    {
        postItArrayList.remove(postIt);

    }

}



