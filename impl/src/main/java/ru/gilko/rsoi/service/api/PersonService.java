package ru.gilko.rsoi.service.api;

import ru.gilko.api.dto.PersonInDto;
import ru.gilko.api.dto.PersonOutDto;
import ru.gilko.api.dto.PersonUpdateDto;
import ru.gilko.api.exception.NoSuchEntityException;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public interface PersonService {
    PersonOutDto create(PersonInDto personInDto);

    Collection<PersonOutDto> getAll();

    PersonOutDto getBy(Long personId) throws NoSuchEntityException;

    void delete(Long personId) throws NoSuchEntityException;

    PersonOutDto update(Long personId, PersonUpdateDto personInDto) throws InvocationTargetException, IllegalAccessException;
}
