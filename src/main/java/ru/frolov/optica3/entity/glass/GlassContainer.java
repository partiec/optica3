package ru.frolov.optica3.entity.glass;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import ru.frolov.optica3.entity.frame.Frame;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class GlassContainer {

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

    @OneToMany(mappedBy = "glassContainer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Glass>glassList = new ArrayList<>();


    public void addToGlassList(Glass unit) {
        this.glassList.add(unit);
    }
    public void removeFromGlassList(Glass unit) {
        this.glassList.remove(unit);
    }
}

