package ru.frolov.optica3.entity.contact;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.enums.contact_enums.*;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp(source = SourceType.VM)
    private LocalDateTime createdAt;
    @UpdateTimestamp(source = SourceType.VM)
    private LocalDateTime changedAt;

    private String firm;
    ///////////////////////
    @Enumerated(EnumType.STRING)
    private ContactDesign design;

    @Enumerated(EnumType.STRING)
    private ContactPeriod period;

    @Enumerated(EnumType.STRING)
    private ContactOxygen oxygen;

    @Enumerated(EnumType.STRING)
    private ContactWater water;
    ////////////////////////
    private String details;
    private BigDecimal purchase;
    private BigDecimal sale;

    ////////////////////////
    @Enumerated(EnumType.STRING)
    private ContactDiameter diameter;

    @Enumerated(EnumType.STRING)
    private ContactRadius radius;
    ////////////////////////
    private String dioptre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contactContainer_id")
    private ContactContainer contactContainer;


}

