package ru.frolov.optica3.payload;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.frolov.optica3.enums.enums_for_frames.FrameInstallType;
import ru.frolov.optica3.enums.enums_for_frames.FrameMaterial;

import java.math.BigDecimal;


public record FiltersPayload(

        @NotNull
        @NotBlank(message = "Заполни 'Фирма'.")
        String firm,

        @NotNull
        @NotBlank(message = "Заполни 'Модель'.")
        String model,

        @Enumerated(EnumType.STRING)
        FrameInstallType frameInstallType,

        @Enumerated(EnumType.STRING)
        FrameMaterial frameMaterial,
        String details,
        BigDecimal purchase,
        BigDecimal sale

) {
}
