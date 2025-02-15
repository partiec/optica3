package ru.frolov.optica3.specification.products;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto.products.GlassDto;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

@Component
public class GlassSpec
        extends AbstractProductSpec {



    public Specification<GlassContainer> allContains(GlassDto filters) {
        return Specification.where(
                productCategoryLike(filters.getProductCategory())
                        .and(firmContains(filters.getFirm()))
                        .and(modelContains(filters.getModel()))
                        .and(detailsContains(filters.getDetails()))
                        .and(purchaseEqual(filters.getPurchase()))
                        .and(saleEqual(filters.getSale()))
                        /////////////////////////////////////
                        .and(glassMaterialLike(filters.getGlassMaterial()))
                        .and(glassDesignLike(filters.getGlassDesign()))
                        .and(glassCoatLike(filters.getGlassCoat()))
                        .and(dioptreLike(filters.getDioptre()))
        );


    }


    public Specification<GlassContainer> allLike(GlassDto filters) {
        return Specification.where(
                productCategoryLike(filters.getProductCategory())
                        .and(firmLike(filters.getFirm()))
                        .and(modelLike(filters.getModel()))
                        .and(detailsLike(filters.getDetails()))
                        .and(purchaseEqual(filters.getPurchase()))
                        .and(saleEqual(filters.getSale()))
                        /////////////////////////////////////
                        .and(glassMaterialLike(filters.getGlassMaterial()))
                        .and(glassDesignLike(filters.getGlassDesign()))
                        .and(glassCoatLike(filters.getGlassCoat()))
                        .and(dioptreLike(filters.getDioptre()))
        );
    }

    //========================================================================================================
    //========================================================================================================


    //-------------------------------
    // glass 3
    //-------------------------------
    public Specification<GlassContainer> glassMaterialLike(GlassMaterial filter) {
        System.out.println("принят GlassMaterial = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("glassMaterialLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("glassMaterialLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.like(
                    root.get("glassMaterial"),
                    filter.toString());
        };
    }

    public Specification<GlassContainer> glassDesignLike(GlassDesign filter) {

        System.out.println(".....SpacForGlasses.designLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("glassDesign"),
                    filter.toString());
        };
    }

    public Specification<GlassContainer> glassCoatLike(GlassCoat filter) {

        System.out.println(".....SpacForGlasses.coatLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("glassCoat"),
                    filter.toString());
        };
    }

    public Specification<GlassContainer> dioptreLike(String filter) {

        System.out.println(".....dioptreLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("dioptre"),
                    filter);

        };
    }


}
