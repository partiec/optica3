package ru.frolov.optica3.payload.contact_payloads;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.frolov.optica3.enums.contact_enums.*;

import java.math.BigDecimal;


public record ContactPayload(

        @NotNull
        @NotBlank(message = "Заполни 'Фирма'.")
        String firm,
        /////////////////////////////////////
        @Enumerated(EnumType.STRING)
        ContactDesign design,
        @Enumerated(EnumType.STRING)
        ContactPeriod period,
        @Enumerated(EnumType.STRING)
        ContactOxygen oxygen,
        @Enumerated(EnumType.STRING)
        ContactWater water,
        /////////////////////////////////////
        String details,
        BigDecimal purchase,
        BigDecimal sale,

        /////////////////////////////////
        @Enumerated(EnumType.STRING)
        ContactDiameter diameter,

        @Enumerated(EnumType.STRING)
        ContactRadius radius,
        /////////////////////////////////
        String dioptre

) {
}
