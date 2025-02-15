package ru.frolov.optica3.dto._order;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.frolov.optica3.enums.order_enums.OrderPayment;
import ru.frolov.optica3.enums.order_enums.OrderStage;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAndClientDto {

    private Long id;

    private Boolean current;

    @Enumerated(EnumType.STRING)
    private OrderStage stage;
    @Enumerated(EnumType.STRING)
    private OrderPayment payment;

    private String orderDetails;
    private BigDecimal price;


    //////////////////////////////////////
    // client's fields
    private String lastName;
    private String firstName;
    private LocalDate birthday;
    private String patronymic;
    private String passport;
    private String clientDetails;
    //////////////////////////////////////





}
