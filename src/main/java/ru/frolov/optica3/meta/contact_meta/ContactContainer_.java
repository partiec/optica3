package ru.frolov.optica3.meta.contact_meta;


import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.entity.products.contact.ContactUnit;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.enums.contact_enums.*;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.util.List;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ContactContainer.class)
public abstract class ContactContainer_ {

    public static volatile SingularAttribute<ContactContainer, Long> id;

    public static volatile SingularAttribute<AccessoryContainer, ProductCategory> contactCategory;

    public static volatile SingularAttribute<ContactContainer, String> firm;
    public static volatile SingularAttribute<ContactContainer, String> model;
    public static volatile SingularAttribute<ContactContainer, String> details;
    public static volatile SingularAttribute<ContactContainer, BigDecimal> purchase;
    public static volatile SingularAttribute<ContactContainer, BigDecimal> sale;
    //////////////////////////////////////////////////////////////////////////////
    public static volatile SingularAttribute<ContactContainer, ContactDesign> contactDesign;
    public static volatile SingularAttribute<ContactContainer, ContactPeriod> contactPeriod;
    public static volatile SingularAttribute<ContactContainer, ContactOxygen> contactOxygen;
    public static volatile SingularAttribute<ContactContainer, ContactWater> contactWater;
    public static volatile SingularAttribute<ContactContainer, ContactDiameter> contactDiameter;
    public static volatile SingularAttribute<ContactContainer, ContactRadius> contactRadius;

    public static volatile SingularAttribute<ContactContainer, String> dioptre;
    //////////////////////////////////////////////////////////////////////////////

    public static volatile SingularAttribute<ContactContainer, _Order> order;

    public static volatile ListAttribute<ContactContainer, List<ContactUnit>> contactList;
}
