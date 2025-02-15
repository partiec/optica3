package ru.frolov.optica3.entity.products.contact;

import jakarta.persistence.*;
import lombok.*;
import ru.frolov.optica3.entity.AbstractContainer;
import ru.frolov.optica3.entity.AbstractUnit;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.enums.abstract_enums.ProductStatus;
import ru.frolov.optica3.enums.contact_enums.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ContactUnit
        extends AbstractUnit {


    ///////////////////////
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
    ////////////////////////



    // объемлющий контейнер
    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "container_id") //подсоединить данное поле
    public ContactContainer container;


}

