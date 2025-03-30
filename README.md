

# Library Management System

## Overview
This Library Management System is designed to handle the operations of a library, managing members, books, authors, borrow records, and membership cards. The system is built using Spring Boot and utilizes JPA to establish the relationships between entities.

The main objective of the project is to implement the correct JPA relationships, including One-to-One, One-to-Many, and Many-to-Many associations, along with full CRUD functionality and input validation.

---

## Features
- **LibraryMember Entity**: Manages users who borrow books from the library.
- **MembershipCard Entity**: Links each LibraryMember to their membership card.
- **Book Entity**: Represents books in the library and allows for management of authors and borrow records.
- **Author Entity**: Represents authors and their relationship with books.
- **BorrowRecord Entity**: Tracks borrowing and returning of books by members.

---

## JPA Relationships
The following relationships are established between entities:

1. **LibraryMember ↔ MembershipCard**: 
   - One-to-One relationship: Each member has a unique membership card, and deleting a member will also delete their membership card.

2. **LibraryMember ↔ BorrowRecord**:
   - One-to-Many relationship: A member can have many borrow records, but each record refers to a single member.

3. **Book ↔ Author**: 
   - Many-to-Many relationship: A book can have multiple authors, and an author can write multiple books.

4. **Book ↔ BorrowRecord**: 
   - One-to-Many relationship: A book can be borrowed many times (each borrow is a separate record), but each record is linked to a specific book.

5. **LibraryMember ↔ BorrowRecord**:
   - Many-to-One relationship: Each borrow record links a member to a specific book.

---

## CRUD Operations
The system implements full CRUD functionality for all entities through REST API endpoints:

### **LibraryMember**
- **Create**: Register a new library member.
- **Read**: Retrieve member details.
- **Update**: Update member information.
- **Delete**: Delete a member (only if all books have been returned).

### **MembershipCard**
- **Create**: Assign a membership card to a library member.
- **Delete**: Delete a member's membership card when the member is deleted.

### **Book**
- **Create**: Add a new book to the library.
- **Read**: Retrieve book details.
- **Update**: Update book information (e.g., title, ISBN, publication year).
- **Delete**: Delete a book (only if it is not currently borrowed).

### **Author**
- **Create**: Add a new author.
- **Read**: Retrieve author details.
- **Update**: Update author information.
- **Delete**: Delete an author (only if the author is not linked to any books).

### **BorrowRecord**
- **Create**: Create a new borrow record when a member borrows a book.
- **Update**: Update the borrow record when a book is returned.

---

## Key Constraints and Business Rules
- **Library Member Deletion**: A library member can only be deleted if they have returned all borrowed books.
- **Book Deletion**: A book cannot be deleted while it is currently borrowed by any member.
- **Author Deletion**: An author cannot be deleted if they are linked to any book.
- **Membership Card**: Deleting a membership card does not remove the associated library member.
- **Book Validation**: A book cannot exist without authors. It must have at least one author before being saved to the database.
- **Borrow Record Validation**: A borrow record is created when a book is borrowed and updated when it is returned.

---

## Entity Details

### **LibraryMember Entity**
- **id**: Primary Key, Auto-Generated
- **name**: String, Not Null
- **email**: String, Unique, Not Null
- **membershipDate**: Date, Not Null
- **membershipCard**: One-to-One with MembershipCard
- **borrowedBooks**: One-to-Many with BorrowRecord

### **MembershipCard Entity**
- **id**: Primary Key, Auto-Generated
- **cardNumber**: String, Unique, Not Null
- **issueDate**: Date, Not Null
- **expiryDate**: Date, Not Null
- **libraryMember**: Bidirectional One-to-One with LibraryMember

### **Book Entity**
- **id**: Primary Key, Auto-Generated
- **title**: String, Unique, Not Null
- **isbn**: String, Unique, Not Null
- **publicationYear**: Integer, Not Null
- **authors**: Many-to-Many with Author
- **borrowRecords**: One-to-Many with BorrowRecord

### **Author Entity**
- **id**: Primary Key, Auto-Generated
- **name**: String, Not Null
- **biography**: Text, Optional
- **books**: Many-to-Many with Book

### **BorrowRecord Entity**
- **id**: Primary Key, Auto-Generated
- **borrowDate**: Date, Not Null
- **returnDate**: Date, Nullable
- **libraryMember**: Many-to-One with LibraryMember
- **book**: Many-to-One with Book

---



## Database Details
- **Database Name**: `librarymanagement`
- **Username**: `oosdassignment`
- **Password**: `oosdassignment`

Seed data is loaded using `data.sql`

Instructions:

1. Create a schema called `librarymanagement` on MySQL if you haven't already done so.
2. Run the application as submitted to load the tables in your database
3. Uncomment lines 11 and 12 in the file application.properties and rerun the file to load the seed data from data.sql
4. For any subsequent runs of the application, make sure to comment lines 11 and 12.
5. There will be an admin account stored in the database with the following details that will be able to access the secured endpoints required to run the app:
- **Username**: `admin`
- **Password**: `adminpassword`

---

## Running the Application
1. Clone the repository.
2. Open the project in your IDE (e.g., IntelliJ IDEA, Eclipse).
3. Ensure you have a compatible version of Java (Java 8 or higher).
4. Run the application as a Spring Boot application.
5. The application will start on `localhost:8080` (default port).
6. The endpoints detailed in the next section can be accessed on postman
7. There will be an admin account stored in the database with the following details that will be able to access the secured endpoints required to run the app:
- **Username**: `admin`
- **Password**: `adminpassword`

---

## API Endpoints

`/library/`

### LibraryMember
- POST: `/member/register-member`: Create a new member. Also automatically creates Membership card for member
- GET: `/member/{member_id}`: Get details of a specific member.
- PUT: `/member/{member_id}`: Update a member’s details.
- DELETE: `/member/{member_id}`: Delete a member (if no borrowed books). Also deletes automatically deletes Membership card of member.



### Book
- POST: `/book`: Add a new book.
- GET: `/book/{book_id}`: Get details of a specific book.
- GET: `/book/`: Get details of all books in db.
- PUT: `/book/{book_id}`: Update a book’s details.
- DELETE: `/book/{book_id}`: Delete a book (if not currently borrowed).

### Author
- POST: `/author`: Add a new author.
- GET: `/author/{author_id}`: Get details of a specific author.
- PUT: `/author/{author_id}`: Update an author’s details.
- DELETE: `/author/{author_id}`: Delete an author (if not linked to any books).
- POST: `/author//{author_id}/add-book/{book_id}`: Add book to author
- DELETE: `/author/{author_id}/remove-book/{book_id}`: Remove book from author

### BorrowRecord
- GET: `/borrowed-books/`: Gets all borrowed records
- GET: `/borrowed-books/{member_id}`: Gets all borrowed records of a specific library member
- GET: `/borrowed-books/{book_Id`: Gets all borrowed records of a specific book
- POST: `/borrowed-books/borrow-book/{member_id}`: Create new borrow record for a specific library member when a book is borrowed by that member
- DELETE: `/borrowed-books/return-book/{member_id}`: Delete borrow record for a specific library member when a book is returned by that member

### Registration
- POST: `/register-user`: Adds a new entry in the Users table of the database with username, password and role that enables the user to access particular endpoints based on their role
- PUT: `/update-user/{user_id}`: Update a user’s username and Role.
- DELETE: `/delete-user/{user_id}`: Delete user.

---
