package ru.gilko.rsoi.controller_impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.gilko.api.controller.PersonController;
import ru.gilko.api.dto.PersonInDto;
import ru.gilko.api.dto.PersonOutDto;
import ru.gilko.api.dto.PersonUpdateDto;
import ru.gilko.rsoi.service.api.PersonService;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

@RestController
@Slf4j
@AllArgsConstructor
public class PersonControllerImpl implements PersonController {
    private PersonService personService;

    @Override
    public ResponseEntity<?> create(PersonInDto personInDto) {
        log.info("Request for creating person {}", personInDto);

        PersonOutDto savedPerson = personService.create(personInDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPerson.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<?> getAll() {
        log.info("Request for reading all persons");

        return ResponseEntity.ok(personService.getAll());
    }

    @Override
    public ResponseEntity<?> getBy(Long personId) {
        log.info("Request for get person with id = {}", personId);

        PersonOutDto requestedPerson = personService.getBy(personId);

        return ResponseEntity.ok(requestedPerson);
    }

    @Override
    public ResponseEntity<?> delete(Long personId) {
        log.info("Request for deleting person with id = {}", personId);

        personService.delete(personId);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> update(Long personId, PersonUpdateDto personInDto) throws InvocationTargetException, IllegalAccessException {
        log.info("Request for update person with id = {}. Data: {}", personId, personInDto);

        PersonOutDto updatedPerson = personService.update(personId, personInDto);

        return ResponseEntity.ok(updatedPerson);
    }
}