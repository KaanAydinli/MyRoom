package com.example.myrooms;
import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

    public ArrayList<PostIt> postItArrayList;
    public PostIt currentPostIt ;
    public int currentColor;
    public String imagePath = "CsProject-BackGrounds/HalfBoard.png";

    public Board()
    {
        postItArrayList = new ArrayList<>();
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



