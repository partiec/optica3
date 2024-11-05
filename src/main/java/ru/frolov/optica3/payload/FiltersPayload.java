package ru.frolov.optica3.payload;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;


public record FiltersPayload(

        @NotBlank(message = "Заполни 'Фирма'.")
        String firm,
        @NotBlank(message = "Заполни 'Модель'.")
        String model,
        String details,
        BigDecimal purchase,
        BigDecimal sale

) {
}
