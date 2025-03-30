package org.example.restlibraryapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;



import java.time.LocalDate;

import java.util.Set;

@Entity
public class LibraryMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Email is not valid")
    private String email;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message="Must be a valid date that is not in the future")
    private LocalDate membershipDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Membership_Card_id", referencedColumnName = "id")
    //@JsonManagedReference // prevent recurrence of data
    @JsonIgnoreProperties({"libraryMember"})
    private MembershipCard membershipCard;

    @OneToMany(mappedBy = "libraryMember",cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH})
   // @JsonManagedReference
    @JsonIgnoreProperties({"libraryMember"})
    private Set<BorrowRecord> borrowedBooks;

    public LibraryMember() {}

    public LibraryMember(String name, String email, LocalDate membershipDate) {
        this.name = name;
        this.email = email;
        this.membershipDate = membershipDate;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    public MembershipCard getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard membershipCard) {
        this.membershipCard = membershipCard;
    }

    public Set<BorrowRecord> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Set<BorrowRecord> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void addBorrowedBook(BorrowRecord borrowedBook) {
        this.borrowedBooks.add(borrowedBook);
    }

    public void removeBorrowedBook(BorrowRecord borrowedBook) {

        this.borrowedBooks.remove(borrowedBook);
    }
}
