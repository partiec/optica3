package ru.frolov.optica3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrameContainer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firm;
    private String model;
    private String details;

    private BigDecimal purchase;
    private BigDecimal sale;

    @OneToMany(mappedBy = "frameContainer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    List<Frame> frameList = new ArrayList<>();

    public FrameContainer(String firm, String model, String details, BigDecimal purchase, BigDecimal sale) {
        this.firm = firm;
        this.model = model;
        this.details = details;
        this.purchase = purchase;
        this.sale = sale;
    }

    public void addToFrameList(Frame frame) {
        this.frameList.add(frame);
    }
    public void removeFromFrameList(Frame frame) {
        this.frameList.remove(frame);
    }




}
