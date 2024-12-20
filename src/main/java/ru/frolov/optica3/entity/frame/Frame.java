package ru.frolov.optica3.entity.frame;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp(source = SourceType.VM)
    private LocalDateTime createdAt;
    @UpdateTimestamp(source = SourceType.VM)
    private LocalDateTime changedAt;

    private String firm;
    private String model;
    ////////////////////////
    @Enumerated(EnumType.STRING)
    private FrameUseType useType;

    @Enumerated(EnumType.STRING)
    private FrameInstallType installType;

    @Enumerated(EnumType.STRING)
    private FrameMaterial material;
    ////////////////////////
    private String details;

    private BigDecimal purchase;
    private BigDecimal sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "frameContainer_id")
    private FrameContainer frameContainer;




}

