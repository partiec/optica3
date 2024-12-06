package ru.frolov.optica3.entity.contact;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import ru.frolov.optica3.entity.glass.Glass;
import ru.frolov.optica3.enums.contact_enums.*;

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
public class ContactContainer {

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

    @OneToMany(mappedBy = "contactContainer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Contact> contactList = new ArrayList<>();


    public void addToContactList(Contact unit) {
        this.contactList.add(unit);
    }
    public void removeFromGlassList(Glass unit) {
        this.contactList.remove(unit);
    }
}

