package ru.gilko.rsoi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.gilko.api.dto.PersonInDto;
import ru.gilko.api.dto.PersonOutDto;
import ru.gilko.api.dto.PersonUpdateDto;
import ru.gilko.api.exception.NoSuchEntityException;
import ru.gilko.rsoi.domain.Person;
import ru.gilko.rsoi.repository.PersonRepository;
import ru.gilko.rsoi.service.api.PersonService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;
    private ObjectMapper mapper;


    @Override
    public PersonOutDto create(PersonInDto personInDto) {
        Person mappedPerson = mapper.convertValue(personInDto, Person.class);

        Person savedPerson = personRepository.save(mappedPerson);

        log.info("Created person {}", savedPerson);

        return mapper.convertValue(savedPerson, PersonOutDto.class);
    }

    @Override
    public Collection<PersonOutDto> getAll() {
        return personRepository.findAll().stream()
                .map(person -> mapper.convertValue(person, PersonOutDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public PersonOutDto getBy(Long personId) throws NoSuchEntityException {
        Optional<Person> personFromDb = personRepository.findById(personId);

        if (personFromDb.isEmpty()) {
            log.error("There is no person with id = {}", personId);
            throw new NoSuchEntityException("There is no person with id = " + personId);
        }

        return mapper.convertValue(personFromDb, PersonOutDto.class);
    }

    @Override
    public void delete(Long personId) throws NoSuchEntityException {
        try {
            personRepository.deleteById(personId);
        } catch (EmptyResultDataAccessException e) {
            log.info("Trying to delete non-existent person with id = {}", personId);
            throw new NoSuchEntityException("Trying to delete non-existent person with id = " + personId);
        }
    }

    @Override
    public PersonOutDto update(Long personId, PersonUpdateDto personInDto) {
        Person personFromDb = personRepository.findById(personId).orElseThrow(() -> {
            log.error("Trying to update entity with id = {}", personId);
            throw new NoSuchEntityException("Trying to update entity with id = " + personId);
        });

        if (personInDto.getName() != null) {
            personFromDb.setName(personInDto.getName());
        }
        if (personInDto.getAddress() != null) {
            personFromDb.setAddress(personInDto.getAddress());
        }
        if (personInDto.getWork() != null) {
            personFromDb.setWork(personInDto.getWork());
        }
        if (personInDto.getAge() != null) {
            personFromDb.setAge(personInDto.getAge());
        }

        return mapper.convertValue(personFromDb, PersonOutDto.class);
    }
}
