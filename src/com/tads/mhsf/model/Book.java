package com.tads.mhsf.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Book {
    private Long id;
    private String title;
    private String genre;
    private String author;
    private Integer printLength;
    private BookStatus bookStatus;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        support.addPropertyChangeListener(propertyChangeListener);
    }

    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        support.removePropertyChangeListener(propertyChangeListener);
    }

    public Book(String title, String genre, String author, Integer printLength) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.printLength = printLength;
        this.bookStatus = BookStatus.AVAILABLE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPrintLength() {
        return printLength;
    }

    public void setPrintLength(Integer printLength) {
        this.printLength = printLength;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        BookStatus oldStatus = this.bookStatus;
        this.bookStatus = bookStatus;
        if (oldStatus != bookStatus && bookStatus.equals(BookStatus.AVAILABLE)) {
            support.firePropertyChange(
                    new PropertyChangeEvent(
                            this,
                            this.title + " que estava na sua lista de desejos agora está disponível!",
                            null,
                            null
                    )
            );
        }
    }
}
