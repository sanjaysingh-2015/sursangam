package com.p3s.sursangam.repository;

import com.p3s.sursangam.model.Person;
import com.p3s.sursangam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUserId(User user);
}
