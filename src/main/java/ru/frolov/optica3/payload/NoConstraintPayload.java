package ru.frolov.optica3.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record NoConstraintPayload(

        @NotNull
        @NotBlank(message = "Заполни 'Фирма'.")
        String firm,

        @NotNull
        @NotBlank(message = "Заполни 'Модель'.")
        String model,
        String details,
        BigDecimal purchase,
        BigDecimal sale

) {
}
