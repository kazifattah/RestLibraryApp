package org.example.restlibraryapp.service;

import org.example.restlibraryapp.entity.LibraryMember;
import org.example.restlibraryapp.entity.MembershipCard;
import org.example.restlibraryapp.repository.BorrowRecordRepo;
import org.example.restlibraryapp.repository.LibraryMemberRepo;
import org.example.restlibraryapp.repository.MembershipCardRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class LibraryMemberService {

    private LibraryMemberRepo libraryMemberRepo;
    private MembershipCardRepo membershipCardRepo;
    private BorrowRecordRepo borrowedRecordRepo;

    public LibraryMemberService(LibraryMemberRepo libraryMemberRepo, MembershipCardRepo membershipCardRepo, BorrowRecordRepo borrowedRecordRepo) {
        this.libraryMemberRepo = libraryMemberRepo;
        this.membershipCardRepo = membershipCardRepo;
        this.borrowedRecordRepo = borrowedRecordRepo;
    }

    // read library member

    public List<LibraryMember> getAllLibraryMembers() {
        return libraryMemberRepo.findAll();
    }

    // read all library member

    public LibraryMember getLibraryMemberById(Long memberId) {
        return libraryMemberRepo.findById(memberId).orElse(null);
    }

    // create

    public void createLibraryMember(LibraryMember libraryMember) {

        // create membership card

        // generate unique random number

        String memberShipCardNumber = String.valueOf(getGeneratedNumber());

        // get today's date for membership date
        LocalDate dateToday = LocalDate.now();

        // get expiry date (10 years from now?)
        LocalDate dateTenYearsLater = LocalDate.now().plusYears(10);

        // set membership card
        MembershipCard membershipCard = new MembershipCard(memberShipCardNumber, dateToday, dateTenYearsLater);
        membershipCard.setLibraryMember(libraryMember);
        libraryMember.setMembershipCard(membershipCard);
        membershipCardRepo.save(membershipCard);

        libraryMember.setMembershipDate(dateToday);

        libraryMemberRepo.save(libraryMember);
    }

    private static int getGeneratedNumber() {
        int place = 1;
        int digits = 8;
        int generatedNumber = 0;
        //Random random = new Random();
        for (int i = 0; i < digits+1; i++)
        {
            int number = (int)(Math.random()*10);
            generatedNumber = generatedNumber + number*place;
            place = place*10;
        }
        return generatedNumber;
    }

    // update

    public void updateLibraryMember(Long libraryMemberId, LibraryMember libraryMember) {
        LibraryMember existingMember = libraryMemberRepo.findById(libraryMemberId).orElse(null);
        if (existingMember != null) {
            existingMember.setName(libraryMember.getName());
            existingMember.setEmail(libraryMember.getEmail());

        }

        libraryMemberRepo.save(existingMember);
    }

    // delete

    public void deleteLibraryMember(Long libraryMemberId) throws Exception {
        LibraryMember libraryMemberToDelete = libraryMemberRepo.findById(libraryMemberId).orElse(null);


        if (libraryMemberToDelete != null) {

            // check if borrowed books have been returned
            if (libraryMemberToDelete.getBorrowedBooks().isEmpty()) {

                // sever link between membership card and library member
                MembershipCard membershipCardToDelete = libraryMemberToDelete.getMembershipCard();
                membershipCardToDelete.setLibraryMember(null);

                // delete membership card
                membershipCardRepo.delete(membershipCardToDelete);

                // delete libraryMember

                libraryMemberRepo.delete(libraryMemberToDelete);
            } else {
                throw new Exception("Please return all books before deleting account");
            }

        }



    }


}
