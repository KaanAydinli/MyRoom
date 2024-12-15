package com.example.myrooms;

public class BookCase {
    public Book[] books;
    static public int count = 0;

    public BookCase() {
        books = new Book[29];
    }
    public int getCount(){
        return count;
    }
    public void setCount(int count){
        BookCase.count = count;
    }
    public void sortBooks() {

        for(int i = 0; i < books.length - 1; i++){
            for(int j = 0; j < books.length - 1 - i; j++){

                if(books[j + 1] != null && books[j] != null && books[j].compareTo(books[j + 1]) > 0){
                    Book temp = books[j];
                    books[j] = books[j + 1];
                    books[j + 1] = temp;
                }
            }
        }
    }
}
