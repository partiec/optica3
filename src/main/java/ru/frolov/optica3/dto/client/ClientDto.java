package ru.frolov.optica3.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private String lastName;
    private String firstName;
    private String patronymic;

    private LocalDate birthday;
    private String passport;
    private String details;
}
