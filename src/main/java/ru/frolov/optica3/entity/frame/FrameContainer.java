package ru.frolov.optica3.entity.frame;

import jakarta.persistence.*;
import lombok.*;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "frameContainer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Frame> frameList = new ArrayList<>();


    public void addToFrameList(Frame frame) {
        this.frameList.add(frame);
    }
    public void removeFromFrameList(Frame frame) {
        this.frameList.remove(frame);
    }




}
