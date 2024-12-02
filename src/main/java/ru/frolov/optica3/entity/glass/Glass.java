package ru.frolov.optica3.entity.glass;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
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
public class Glass {

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
    private GlassMaterial material;

    @Enumerated(EnumType.STRING)
    private GlassDesign design;

    @Enumerated(EnumType.STRING)
    private GlassCoat coat ;

    ////////////////////////
    private String details;

    private BigDecimal purchase;
    private BigDecimal sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "glassContainer_id")
    private GlassContainer glassContainer;




}

