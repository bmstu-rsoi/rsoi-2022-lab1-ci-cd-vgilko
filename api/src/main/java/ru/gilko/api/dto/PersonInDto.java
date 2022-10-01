package ru.gilko.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInDto {
    @NotBlank
    @Length(max = 250)
    private String name;
    @NotBlank
    @Length(max = 250)
    private String address;
    @NotBlank
    @Length(max = 250)
    private String work;
    @NotNull
    @Min(0)
    private Integer age;
}
