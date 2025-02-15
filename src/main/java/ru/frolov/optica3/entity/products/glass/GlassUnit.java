package ru.frolov.optica3.entity.products.glass;

import jakarta.persistence.*;
import lombok.*;
import ru.frolov.optica3.entity.AbstractContainer;
import ru.frolov.optica3.entity.AbstractUnit;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class GlassUnit
        extends AbstractUnit {


    ///////////////////////
    @Enumerated(EnumType.STRING)
    private GlassMaterial glassMaterial;

    @Enumerated(EnumType.STRING)
    private GlassDesign glassDesign;

    @Enumerated(EnumType.STRING)
    private GlassCoat glassCoat;

    private String dioptre;
    ////////////////////////


    // объемлющий контейнер
    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "container_id") //подсоединить данное поле
    public GlassContainer container;


}

