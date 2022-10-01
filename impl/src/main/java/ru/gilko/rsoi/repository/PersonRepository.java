package ru.gilko.rsoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gilko.rsoi.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
