package org.example.restlibraryapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;


import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonIgnore
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    @Pattern(regexp = "\\d{4}", message ="Must be a 4 digit year")
    private Integer publicationYear;


    @ManyToMany(mappedBy = "books",
            cascade = {CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH,
                    CascadeType.PERSIST})

    @JsonIgnoreProperties({"books"})
    private Set<Author> authors;

    @OneToMany(mappedBy = "book",
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH})
   // @JsonManagedReference
    @JsonIgnoreProperties({"book"})
    private Set<BorrowRecord> borrowRecords;

    public Book() {}

    public Book(String title, String isbn, Integer publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public void setBorrowRecords(Set<BorrowRecord> borrowRecords) {
        this.borrowRecords = borrowRecords;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void addBorrowRecord(BorrowRecord borrowRecord) {
        this.borrowRecords.add(borrowRecord);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
    }
    public void removeBorrowRecord(BorrowRecord borrowRecord) {

        this.borrowRecords.remove(borrowRecord);
    }
}
