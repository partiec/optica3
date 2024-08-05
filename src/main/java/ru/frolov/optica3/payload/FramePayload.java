package ru.frolov.optica3.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record FramePayload(

        @NotBlank(message = "Заполни поле 'Фирма'.")
        @NotEmpty(message = "Поле 'Фирма' не должно быть пустым.")
        String firm,
        @NotBlank(message = "Поле 'Модель' обязательно для заполнения.")
        @NotEmpty(message = "Can't be empty, bro")
        String model,
        String details,
        BigDecimal purchasePrice,
        BigDecimal salePrice

) {
}
