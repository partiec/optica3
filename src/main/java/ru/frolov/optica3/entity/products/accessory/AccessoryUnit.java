package ru.frolov.optica3.entity.products.accessory;

import jakarta.persistence.*;
import lombok.*;
import ru.frolov.optica3.entity.AbstractContainer;
import ru.frolov.optica3.entity.AbstractUnit;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.enums.accessory_enums.AccessoryCategory;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AccessoryUnit
        extends AbstractUnit {


    ////////////////////////////////////////////
    @Enumerated(EnumType.STRING)
    private AccessoryCategory accessoryCategory;
    ////////////////////////////////////////////


    // объемлющий контейнер
    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "container_id") //подсоединить данное поле
    public AccessoryContainer container;



}

