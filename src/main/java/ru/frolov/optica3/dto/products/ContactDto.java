package ru.frolov.optica3.dto.products;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.frolov.optica3.enums.contact_enums.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto
        extends AbstractProductDto {


    /////////////////////////////////////
    // contact 6 + dioptre
    @Enumerated(EnumType.STRING)
    private ContactDesign contactDesign;
    @Enumerated(EnumType.STRING)
    private ContactPeriod contactPeriod;
    @Enumerated(EnumType.STRING)
    private ContactOxygen contactOxygen;
    @Enumerated(EnumType.STRING)
    private ContactWater contactWater;
    @Enumerated(EnumType.STRING)
    private ContactDiameter contactDiameter;
    @Enumerated(EnumType.STRING)
    private ContactRadius contactRadius;

    private String dioptre;
    /////////////////////////////////////


}
