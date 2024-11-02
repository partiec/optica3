package ru.frolov.optica3.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

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
    private String details;

    private BigDecimal purchase;
    private BigDecimal sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "frameContainer_id")
    private FrameContainer frameContainer;

    // constructor needed for creation
    public Frame(String firm, String model, String details, BigDecimal purchase, BigDecimal sale, FrameContainer frameContainer) {
        this.firm = firm;
        this.model = model;
        this.details = details;
        this.purchase = purchase;
        this.sale = sale;
        this.frameContainer = frameContainer;
    }


}

