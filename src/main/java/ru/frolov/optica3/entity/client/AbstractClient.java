package ru.frolov.optica3.entity.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import ru.frolov.optica3.entity.order._Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreationTimestamp(source = SourceType.VM)
    protected LocalDateTime createdAt;
    @UpdateTimestamp(source = SourceType.VM)
    protected LocalDateTime changedAt;

    // ФИО
    protected String lastName;
    protected String firstName;
    protected String patronymic;

    protected LocalDate birthday;
    protected String passport;
    protected String details;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    protected List<_Order> orderList = new ArrayList<>();
}
