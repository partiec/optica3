package ru.frolov.optica3.payload.frame_payloads;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;

import java.math.BigDecimal;


public record FramePayload(

        @NotBlank(message = "Заполни 'Фирма'.")
        String firm,
        @NotBlank(message = "Заполни 'Модель'.")
        String model,

        /////////////////////////////

        @Enumerated(EnumType.STRING)
        FrameUseType useType,

        @Enumerated(EnumType.STRING)
        FrameInstallType installType,

        @Enumerated(EnumType.STRING)
        FrameMaterial material,
        /////////////////////////////
        String details,
        BigDecimal purchase,
        BigDecimal sale

) {
}
