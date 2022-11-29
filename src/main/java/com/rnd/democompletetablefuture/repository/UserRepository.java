package com.rnd.democompletetablefuture.repository;

import com.rnd.democompletetablefuture.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
