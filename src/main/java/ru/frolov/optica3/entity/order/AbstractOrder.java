package ru.frolov.optica3.entity.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import ru.frolov.optica3.entity.client.Client;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.entity.products.contact.ContactUnit;
import ru.frolov.optica3.entity.products.frame.FrameUnit;
import ru.frolov.optica3.entity.products.glass.GlassUnit;
import ru.frolov.optica3.enums.order_enums.OrderPayment;
import ru.frolov.optica3.enums.order_enums.OrderStage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Boolean current;

    @CreationTimestamp(source = SourceType.VM)
    protected LocalDateTime createdAt;
    @UpdateTimestamp(source = SourceType.VM)
    protected LocalDateTime changedAt;

    public String getFormattedCreatedAt() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd. MM. yyyy", new Locale("ru", "RU"));
        return createdAt.format(dtf);
    }


    @Enumerated(EnumType.STRING)
    protected OrderStage stage;
    @Enumerated(EnumType.STRING)
    protected OrderPayment payment;

    protected String orderDetails;
    protected BigDecimal price;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    protected Client client;

    //////////////////////////////
    // client's fields
    protected String lastName;
    protected String firstName;
    protected String patronymic;
    protected LocalDate birthday;
    protected String passport;
    protected String clientDetails;
    //////////////////////////////


    //  для каждого вида товаров отдельный список
    // -----------------------------------------------------------------
    // оправы
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    protected List<FrameUnit> frameUnits = new ArrayList<>();

    // очковые линзы
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    protected List<GlassUnit> glassUnits = new ArrayList<>();

    // контактные линзы
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    protected List<ContactUnit> contactUnits = new ArrayList<>();

    // аксессуары
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    protected List<AccessoryUnit> accessoryUnits = new ArrayList<>();


}
