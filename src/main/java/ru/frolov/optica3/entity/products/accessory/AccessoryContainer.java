package ru.frolov.optica3.entity.products.accessory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.frolov.optica3.entity.AbstractContainer;
import ru.frolov.optica3.enums.accessory_enums.AccessoryCategory;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AccessoryContainer
        extends AbstractContainer {


    ////////////////////////////////////////////
    @Enumerated(EnumType.STRING)
    private AccessoryCategory accessoryCategory;
    ////////////////////////////////////////////

    @OneToMany(mappedBy = "container", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<AccessoryUnit> unitList = new ArrayList<>();

}
