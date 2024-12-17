package com.example.myrooms;

import java.io.Serializable;
import java.util.ArrayList;

public class BookCase implements Serializable {
    public ArrayList<Book> books;
    static public int count = 0;

    public BookCase() {
        books = new ArrayList<>();
    }
    public int getCount(){
        return count;
    }
    public void setCount(int count){
        BookCase.count = count;
    }
    public void sortBooks() {

        for(int i = 0; i < books.size() - 1; i++){
            for(int j = 0; j < books.size() - 1 - i; j++){

                if(books.get(j + 1) != null && books.get(j) != null && books.get(j).compareTo(books.get(j)) > 0){
                    Book temp = books.get(j);
                    books.set(j, books.get(j + 1));
                    books.set(j + 1, temp);
                }
            }
        }
    }
}
