package com.jazzcript.assignment2.models;

import java.io.Serializable;

public class BookModel implements Serializable {




    private String name;
    private String author;
    private String isbn;
    private float price;



    public BookModel( String name, String author, String isbn, float price) {

        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }




}
