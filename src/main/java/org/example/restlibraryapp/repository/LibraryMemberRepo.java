package org.example.restlibraryapp.repository;

import org.example.restlibraryapp.entity.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryMemberRepo extends JpaRepository<LibraryMember, Long> {
}
