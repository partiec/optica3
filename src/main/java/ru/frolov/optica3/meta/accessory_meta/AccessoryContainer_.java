package ru.frolov.optica3.meta.accessory_meta;


import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.enums.accessory_enums.AccessoryCategory;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.util.List;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AccessoryContainer.class)
public abstract class AccessoryContainer_ {

    public static volatile SingularAttribute<AccessoryContainer, Long> id;

    public static volatile SingularAttribute<AccessoryContainer, ProductCategory> category;

    public static volatile SingularAttribute<AccessoryContainer, String> firm;
    public static volatile SingularAttribute<AccessoryContainer, String> model;
    public static volatile SingularAttribute<AccessoryContainer, String> details;
    public static volatile SingularAttribute<AccessoryContainer, BigDecimal> purchase;
    public static volatile SingularAttribute<AccessoryContainer, BigDecimal> sale;
    public static volatile SingularAttribute<AccessoryContainer, AccessoryCategory> accessoryCategory;

    public static volatile SingularAttribute<AccessoryContainer, _Order> order;

    public static volatile ListAttribute<AccessoryContainer, List<AccessoryUnit>> accessoryList;


}
