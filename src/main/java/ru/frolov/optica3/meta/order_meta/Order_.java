package ru.frolov.optica3.meta.order_meta;


import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.frolov.optica3.entity.client.Client;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.enums.order_enums.OrderPayment;
import ru.frolov.optica3.enums.order_enums.OrderStage;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.time.LocalDate;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(_Order.class)
public abstract class Order_ {


    public static volatile SingularAttribute<_Order, Long> id;
    public static volatile SingularAttribute<_Order, Boolean> current;
    public static volatile SingularAttribute<_Order, OrderStage> stage;
    public static volatile SingularAttribute<_Order, OrderPayment> payment;
    public static volatile SingularAttribute<_Order, String> orderDetails;
    public static volatile SingularAttribute<_Order, BigDecimal> price;

    public static volatile SingularAttribute<_Order, Client> client;

    ////////////////////////////////////////////////////////////////
    public static volatile SingularAttribute<_Order, String> lastName;
    public static volatile SingularAttribute<_Order, String> firstName;
    public static volatile SingularAttribute<_Order, LocalDate> birthday;
    public static volatile SingularAttribute<_Order, String> patronymic;
    public static volatile SingularAttribute<_Order, String> passport;
    public static volatile SingularAttribute<_Order, String> clientDetails;
    ////////////////////////////////////////////////////////////////
}
