package ru.frolov.optica3.meta.glass_meta;


import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GlassContainer.class)
public abstract class GlassContainer_ {

    public static volatile SingularAttribute<GlassContainer, Long> id;

    public static volatile SingularAttribute<GlassContainer, ProductCategory> productCategory;

    public static volatile SingularAttribute<GlassContainer, String> firm;
    public static volatile SingularAttribute<GlassContainer, String> model;
    public static volatile SingularAttribute<GlassContainer, String> details;
    public static volatile SingularAttribute<GlassContainer, BigDecimal> purchase;
    public static volatile SingularAttribute<GlassContainer, BigDecimal> sale;
    //////////////////////////////////////////////////////////////////////////////
    public static volatile SingularAttribute<GlassContainer, GlassMaterial> glassMaterial;
    public static volatile SingularAttribute<GlassContainer, GlassDesign> glassDesign;
    public static volatile SingularAttribute<GlassContainer, GlassCoat> glassCoat;

    public static volatile SingularAttribute<GlassContainer, String> dioptre;
    //////////////////////////////////////////////////////////////////////////////


}
