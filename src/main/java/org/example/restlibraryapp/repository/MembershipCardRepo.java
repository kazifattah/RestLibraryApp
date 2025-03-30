package org.example.restlibraryapp.repository;

import org.example.restlibraryapp.entity.MembershipCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipCardRepo extends JpaRepository<MembershipCard,Long> {
}
