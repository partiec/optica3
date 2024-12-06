package ru.frolov.optica3.meta.contact_meta;


import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.enums.contact_enums.*;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ContactContainer.class)
public abstract class ContactContainer_ {

    public static volatile SingularAttribute<ContactContainer, Long> id;
    public static volatile SingularAttribute<ContactContainer, String> firm;
    //////////////////////////////////////////////////////////////////////////////
    public static volatile SingularAttribute<ContactContainer, ContactDesign> design;
    public static volatile SingularAttribute<ContactContainer, ContactPeriod> period;
    public static volatile SingularAttribute<ContactContainer, ContactOxygen> oxygen;
    public static volatile SingularAttribute<ContactContainer, ContactWater> water;
    //////////////////////////////////////////////////////////////////////////////
    public static volatile SingularAttribute<ContactContainer, String> details;
    public static volatile SingularAttribute<ContactContainer, BigDecimal> purchase;
    public static volatile SingularAttribute<ContactContainer, BigDecimal> sale;
    /////////////////////////////////
    public static volatile SingularAttribute<ContactContainer, ContactDiameter> diameter;
    public static volatile SingularAttribute<ContactContainer, ContactRadius> radius;
    /////////////////////////////////
    public static volatile SingularAttribute<ContactContainer, String> dioptre;
}
