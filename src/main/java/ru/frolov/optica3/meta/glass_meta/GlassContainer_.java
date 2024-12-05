package ru.frolov.optica3.meta.glass_meta;


import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GlassContainer.class)
public abstract class GlassContainer_ {

    public static volatile SingularAttribute<GlassContainer, Long> id;
    public static volatile SingularAttribute<GlassContainer, String> firm;
    //////////////////////////////////////////////////////////////////////////////
    public static volatile SingularAttribute<GlassContainer, GlassMaterial> material;
    public static volatile SingularAttribute<GlassContainer, GlassDesign> design;
    public static volatile SingularAttribute<GlassContainer, GlassCoat> coat;
    //////////////////////////////////////////////////////////////////////////////
    public static volatile SingularAttribute<FrameContainer, String> details;
    public static volatile SingularAttribute<FrameContainer, BigDecimal> purchase;
    public static volatile SingularAttribute<FrameContainer, BigDecimal> sale;
    public static volatile SingularAttribute<FrameContainer, String> dioptre;
}
