package ru.frolov.optica3.entity.products.contact;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.frolov.optica3.defaults.Dioptre;
import ru.frolov.optica3.entity.AbstractContainer;
import ru.frolov.optica3.enums.contact_enums.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ContactContainer
        extends AbstractContainer {


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

    @OneToMany(mappedBy = "container", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<ContactUnit> unitList = new ArrayList<>();

}

