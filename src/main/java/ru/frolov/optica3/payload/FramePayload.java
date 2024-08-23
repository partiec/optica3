package ru.frolov.optica3.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record FramePayload(

        @NotBlank(message = "Заполни 'Фирма'.")
        String firm,
        @NotBlank(message = "Заполни 'Модель'.")
        String model,
        String details,
        BigDecimal purchasePrice,
        BigDecimal salePrice

) {
}
