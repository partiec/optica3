package ru.frolov.optica3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdenticalFramesSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firm;
    private String model;
    private String details;

    private BigDecimal purchasePrice;
    private BigDecimal salePrice;


    @OneToMany(mappedBy = "identicalFramesSet")
    private Set<Frame> frames;




}
