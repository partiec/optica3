package ru.frolov.optica3.entity.products.frame;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;
import ru.frolov.optica3.entity.AbstractContainer;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
//@Primary
public class FrameContainer
        extends AbstractContainer {


    ////////////////////////
    @Enumerated(EnumType.STRING)
    private FrameUseType frameUseType;

    @Enumerated(EnumType.STRING)
    private FrameInstallType frameInstallType;

    @Enumerated(EnumType.STRING)
    private FrameMaterial frameMaterial;
    ////////////////////////


    @OneToMany(mappedBy = "container", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<FrameUnit> unitList = new ArrayList<>();

}
