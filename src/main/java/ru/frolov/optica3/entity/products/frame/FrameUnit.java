package ru.frolov.optica3.entity.products.frame;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.frolov.optica3.entity.AbstractUnit;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;

@Entity
@NoArgsConstructor
@Getter
@Setter
// Setters are written
public class FrameUnit
        extends AbstractUnit {


    ////////////////////////
    // with setters
    @Enumerated(EnumType.STRING)
    private FrameUseType frameUseType;

    public void setFrameUseType(FrameUseType frameUseType) {
        this.frameUseType = frameUseType == null ? FrameUseType.NOT_SELECTED : frameUseType;
    }

    @Enumerated(EnumType.STRING)
    private FrameInstallType frameInstallType;

    public void setFrameInstallType(FrameInstallType frameInstallType) {
        this.frameInstallType = frameInstallType == null ? FrameInstallType.NOT_SELECTED : frameInstallType;
    }

    @Enumerated(EnumType.STRING)
    private FrameMaterial frameMaterial;

    public void setFrameMaterial(FrameMaterial frameMaterial) {
        this.frameMaterial = frameMaterial == null ? FrameMaterial.NOT_SELECTED : frameMaterial;
    }
    ////////////////////////

    // объемлющий контейнер
    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "container_id") //подсоединить данное поле (hibernate выполняет это по-умолчанию)
    private FrameContainer container;


}

