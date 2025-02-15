package ru.frolov.optica3.entity.products.glass;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.frolov.optica3.entity.AbstractContainer;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class GlassContainer
        extends AbstractContainer {

    ///////////////////////
    @Enumerated(EnumType.STRING)
    private GlassMaterial glassMaterial;

    @Enumerated(EnumType.STRING)
    private GlassDesign glassDesign;

    @Enumerated(EnumType.STRING)
    private GlassCoat glassCoat;

    private String dioptre;
    ////////////////////////


    @OneToMany(mappedBy = "container", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<GlassUnit> unitList = new ArrayList<>();
}
