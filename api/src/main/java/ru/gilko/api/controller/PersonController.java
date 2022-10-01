package ru.gilko.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gilko.api.constants.ControllerUrls;
import ru.gilko.api.dto.PersonInDto;
import ru.gilko.api.dto.PersonUpdateDto;
import ru.gilko.api.exception.NoSuchEntityException;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

public interface PersonController {
    @PostMapping(path = ControllerUrls.PERSON_URL)
    ResponseEntity<?> create(@RequestBody @Valid PersonInDto personInDto);

    @GetMapping(path = ControllerUrls.PERSON_URL)
    ResponseEntity<?> getAll();

    @GetMapping(path = ControllerUrls.CONCRETE_PERSON_URL)
    ResponseEntity<?> getBy(@PathVariable Long personId) throws NoSuchEntityException;

    @DeleteMapping(path = ControllerUrls.CONCRETE_PERSON_URL)
    ResponseEntity<?> delete(@PathVariable Long personId) throws NoSuchEntityException;

    @PatchMapping(path = ControllerUrls.CONCRETE_PERSON_URL)
    ResponseEntity<?> update(@PathVariable Long personId, @RequestBody PersonUpdateDto personInDto) throws InvocationTargetException, IllegalAccessException;
}
