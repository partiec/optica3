package ru.frolov.optica3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.frolov.optica3.defaults.Dioptre;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.enums.accessory_enums.AccessoryCategory;
import ru.frolov.optica3.enums.contact_enums.*;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

import java.math.BigDecimal;
import java.util.List;

//@Entity
//@Inheritance(
//        strategy = InheritanceType.SINGLE_TABLE
//)
//@DiscriminatorColumn(name = "c_type")
@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Enumerated(EnumType.STRING)
    public ProductCategory productCategory;
    // только для unit<
//    @Enumerated(EnumType.STRING)
//    protected ProductStatus productStatus;


    public String firm;
    public String model;
    public String details;
    public BigDecimal purchase;
    public BigDecimal sale;

    // метод переопределяется в конкретном контейнере
    public abstract List<? extends AbstractUnit> getUnitList();



}
