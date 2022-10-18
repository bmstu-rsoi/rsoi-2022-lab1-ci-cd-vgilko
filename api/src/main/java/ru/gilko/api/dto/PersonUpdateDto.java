package ru.gilko.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonUpdateDto {
    private String name;
    private String address;
    private String work;
    private Integer age;
}
