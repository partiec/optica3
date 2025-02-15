package ru.frolov.optica3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import ru.frolov.optica3.defaults.Dioptre;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.enums.abstract_enums.ProductStatus;
import ru.frolov.optica3.enums.accessory_enums.AccessoryCategory;
import ru.frolov.optica3.enums.contact_enums.*;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//@Entity
//@Inheritance(
//        strategy = InheritanceType.SINGLE_TABLE
//)
//@DiscriminatorColumn(name = "u_type")
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @CreationTimestamp(source = SourceType.VM)
    private LocalDateTime createdAt;
    @UpdateTimestamp(source = SourceType.VM)
    private LocalDateTime changedAt;

    @Enumerated(EnumType.STRING)
    public ProductCategory productCategory;
    // только для unit
    @Enumerated(EnumType.STRING)
    public ProductStatus productStatus;

    // фирма, модель, описание, закуп., продаж.
    public String firm;
    public String model;
    public String details;
    public BigDecimal purchase;
    public BigDecimal sale;

    @ManyToOne
    @JoinColumn(name = "order_id")
    public _Order order;



}
