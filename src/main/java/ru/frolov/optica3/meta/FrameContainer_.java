package ru.frolov.optica3.meta;


import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.enums.enums_for_frames.FrameInstallType;
import ru.frolov.optica3.enums.enums_for_frames.FrameMaterial;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FrameContainer.class)
public abstract class FrameContainer_ {

    public static volatile SingularAttribute<FrameContainer, Long> id;
    public static volatile SingularAttribute<FrameContainer, String> firm;
    public static volatile SingularAttribute<FrameContainer, String> model;
    public static volatile SingularAttribute<FrameContainer, FrameInstallType>  frameInstallType;
    public static volatile SingularAttribute<FrameContainer, FrameMaterial>  frameMaterial;
    public static volatile SingularAttribute<FrameContainer, String> details;
    public static volatile SingularAttribute<FrameContainer, BigDecimal> purchase;
    public static volatile SingularAttribute<FrameContainer, BigDecimal> sale;

    public static final String ID = "id";
    public static final String FIRM = "firm";
    public static final String MODEL = "model";
    public static final String DETAILS = "details";
    public static final String PURCHASE = "purchase";
    public static final String SALE = "sale";
}
