package ru.frolov.optica3.payload.glass_payloads;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

import java.math.BigDecimal;


public record GlassPayload(

        @NotNull
        @NotBlank(message = "Заполни 'Фирма'.")
        String firm,
        /////////////////////////////////////
        @Enumerated(EnumType.STRING)
        GlassMaterial material,

        @Enumerated(EnumType.STRING)
        GlassDesign design,

        @Enumerated(EnumType.STRING)
        GlassCoat coat,
        /////////////////////////////////////
        String details,
        BigDecimal purchase,
        BigDecimal sale,
        String dioptre
) {
}
