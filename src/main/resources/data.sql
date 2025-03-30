-- Insert into Author table
INSERT INTO author (name, biography) VALUES
('George Orwell', 'British novelist, famed for 1984 and Animal Farm, critiquing totalitarianism and political corruption.'),
('Margaret Atwood', 'Canadian writer known for dystopian novels like The Handmaid’s Tale and feminist themes.'),
('Mark Twain', 'American humorist, known for The Adventures of Huckleberry Finn and social commentary.'),
('Dan Brown', 'American author known for thrillers like The Da Vinci Code, blending history, puzzles, and conspiracies.');


-- Insert into Book table
INSERT INTO book (title, isbn, publication_year) VALUES
('1984', '978-0451524935', 1949),
('The Handmaid’s Tale', '978-0385490818', 1985),
('The Adventures of Huckleberry Finn', '978-0451530912', 1884),
('The Da Vinci Code', '978-0385504201', 2003);

-- Insert into membership_card table (generate card_number field and populate)
INSERT INTO membership_card (card_number, issue_date, expiry_date) VALUES
(76398257, '2025-01-12', '2035-01-12'),
(67289431, '2025-02-18', '2035-02-18'),
(52916783, '2025-03-15', '2035-03-15'),
(38127594, '2025-03-21', '2035-03-21');
-- ON CONFLICT (card_number, issue_date, expiry_date) DO NOTHING;

-- Insert into LibraryMember table

INSERT INTO library_member (name, email, membership_date, membership_card_id) VALUES
('John Smith', 'john.smith@example.com', '2025-01-12', 1),
('Emma Williams', 'emma.williams@example.com', '2025-02-18', 2),
('Oliver Brown', 'oliver.brown@example.com', '2025-03-15', 3),
('Sophia Davis', 'sophia.davis@example.com', '2025-03-21', 4);  -- Linking to MembershipCard with ID 4

-- Insert into Book-Author relationship table
INSERT INTO author_book (book_id, author_id) VALUES
(1, 1),  
(2, 2),  
(3, 3),  
(4, 4);  

-- Insert into BorrowRecord table (make sure to link books and library members)

INSERT INTO borrow_record (borrow_date, return_date, library_member_id, book_id) VALUES
('2025-01-18', null, 1, 1),
('2025-02-22', null, 2, 2),
('2025-03-16', null, 3, 3),
('2025-03-23', null, 4, 4);

-- Insert into User table an admin account

INSERT INTO `librarymanagement`.`my_user` (`password`, `role`, `username`) VALUES ('$2a$12$.hmYZimxMEEKs2z1.KLLo.uGvP1hxuQ4DByOhxu86Vyeiw3xGjK8C', 'ADMIN', 'admin');

