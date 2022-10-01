package ru.gilko.rsoi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.gilko.api.dto.PersonInDto;
import ru.gilko.api.dto.PersonOutDto;
import ru.gilko.api.exception.NoSuchEntityException;
import ru.gilko.rsoi.domain.Person;
import ru.gilko.rsoi.repository.PersonRepository;
import ru.gilko.rsoi.service.api.PersonService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonServiceImplTest {
    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Should throw an exception when the person does not exist")
    void deleteWhenPersonDoesNotExistThenThrowException() {
        final Long id = 1L;
        doThrow(EmptyResultDataAccessException.class).when(personRepository).deleteById(id);

        assertThrows(NoSuchEntityException.class, () -> personService.delete(id));

        verify(personRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    @DisplayName("Should delete the person when the person exists")
    void deleteWhenPersonExists() {
        final Long id = 1L;
        Person person = new Person(id, "name1", "address1", "work1", 1);

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        personService.delete(id);

        when(personRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> personService.getBy(id));

        verify(personRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should throw an exception when the person does not exist")
    void getByWhenPersonDoesNotExistThenThrowException() {
        final Long id = 1L;
        when(personRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> personService.getBy(id));

        verify(personRepository, times(1)).findById(id);
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    @DisplayName("Should return the person when the person exists")
    void getByWhenPersonExistsThenReturnPerson() {
        Person person = new Person(1L, "name1", "address1", "work1", 1);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        PersonOutDto personOutDto = personService.getBy(person.getId());

        PersonOutDto mappedPerson = mapper.convertValue(person, PersonOutDto.class);

        assertEquals(mappedPerson, personOutDto);

        verify(personRepository, times(1)).findById(any());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    @DisplayName("Should return all persons from the database")
    void getAllShouldReturnAllPersonsFromTheDatabase() {
        Person person1 = new Person(1L, "name1", "address1", "work1", 1);
        Person person2 = new Person(2L, "name2", "address2", "work2", 2);
        Person person3 = new Person(3L, "name3", "address3", "work3", 3);

        when(personRepository.findAll()).thenReturn(List.of(person1, person2, person3));

        Collection<PersonOutDto> persons = personService.getAll();

        assertEquals(3, persons.size());
        assertTrue(persons.contains(mapper.convertValue(person1, PersonOutDto.class)));
        assertTrue(persons.contains(mapper.convertValue(person2, PersonOutDto.class)));
        assertTrue(persons.contains(mapper.convertValue(person3, PersonOutDto.class)));

        verify(personRepository, times(1)).findAll();
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    @DisplayName("Should save the person when the person is valid")
    void createWhenPersonIsValid() {
        PersonInDto personInDto = new PersonInDto("name", "address", "work", 20);

        Person person = new Person(1L, "name", "address", "work", 20);

        doReturn(person).when(personRepository).save(any());

        PersonOutDto personOutDto = personService.create(personInDto);

        PersonOutDto mappedInDto = mapper.convertValue(personInDto, PersonOutDto.class);
        mappedInDto.setId(1);

        assertEquals(mappedInDto, personOutDto);

        verify(personRepository, times(1)).save(any());
        verifyNoMoreInteractions(personRepository);
    }
}