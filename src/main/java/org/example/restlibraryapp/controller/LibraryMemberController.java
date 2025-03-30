package org.example.restlibraryapp.controller;

import org.example.restlibraryapp.entity.LibraryMember;
import org.example.restlibraryapp.service.LibraryMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for accessing endpoints related to LIBRARY MEMBERS
 */

@RestController
@RequestMapping("/library/member")
public class LibraryMemberController {

    private LibraryMemberService libraryMemberService;

    public LibraryMemberController(LibraryMemberService libraryMemberService) {
        this.libraryMemberService = libraryMemberService;
    }

    // view account
    @GetMapping("/{member_id}")
    public LibraryMember getLibraryMember(@PathVariable Long member_id) {
        return libraryMemberService.getLibraryMemberById(member_id);
    }

    // create new library member

    @PostMapping("/register-member")
    public ResponseEntity<LibraryMember> createLibraryMember(@RequestBody LibraryMember libraryMember) {
        libraryMemberService.createLibraryMember(libraryMember);
        return new  ResponseEntity<>(libraryMember, HttpStatus.CREATED);
    }

    // update details
    @PutMapping("/{member_id}")
    public ResponseEntity<LibraryMember> updateLibraryMember(@PathVariable Long member_id, @RequestBody LibraryMember libraryMember) {
        libraryMemberService.updateLibraryMember(member_id, libraryMember);
        return new  ResponseEntity<>(libraryMember, HttpStatus.OK);
    }

    // delete their accounts
    @DeleteMapping("/{member_id}")
    public ResponseEntity<Void> deleteLibraryMember(@PathVariable Long member_id) throws Exception {
        libraryMemberService.deleteLibraryMember(member_id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }


}
