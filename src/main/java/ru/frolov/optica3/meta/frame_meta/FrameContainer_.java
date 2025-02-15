package ru.frolov.optica3.meta.frame_meta;


import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FrameContainer.class)
public abstract class FrameContainer_ {

    public static volatile SingularAttribute<FrameContainer, Long> id;

    public static volatile SingularAttribute<AccessoryContainer, ProductCategory> productCategory;

    public static volatile SingularAttribute<FrameContainer, String> firm;
    public static volatile SingularAttribute<FrameContainer, String> model;
    public static volatile SingularAttribute<FrameContainer, String> details;
    public static volatile SingularAttribute<FrameContainer, BigDecimal> purchase;
    public static volatile SingularAttribute<FrameContainer, BigDecimal> sale;
    //////////////////////////////////////////////////////////////////////////////
    public static volatile SingularAttribute<FrameContainer, FrameUseType> frameUseType;
    public static volatile SingularAttribute<FrameContainer, FrameInstallType> frameInstallType;
    public static volatile SingularAttribute<FrameContainer, FrameMaterial> frameMaterial;
    //////////////////////////////////////////////////////////////////////////////


}
