package ru.frolov.optica3.spec.glass_spec;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;

import java.math.BigDecimal;

@UtilityClass
public class SpecForGlasses {


    public Specification<GlassContainer> byAllContains(Filters_GlassPayload filters) {

        System.out.println(".....SpecForGlasses.byAllContains()... ");

        return Specification.where(
                firmContains(filters.firm())
                        /////////////////////////////////////
                        .and(materialLike(filters.material()))
                        .and(designLike(filters.design()))
                        .and(coatLike(filters.coat()))
                        /////////////////////////////////////
                        .and(detailsContains(filters.details()))
                        .and(purchaseEqual(filters.purchase()))
                        .and(saleEqual(filters.sale()))
                        .and(dioptreLike(filters.dioptre())));
    }


    // by base fields
    public Specification<GlassContainer> byAllLike(Filters_GlassPayload filters) {

        System.out.println(".....SpacForGlasses.byAllFieldsLike()...");

        return Specification.where(
                firmLike(filters.firm())
                        /////////////////////////////////////
                        .and(materialLike(filters.material()))
                        .and(designLike(filters.design()))
                        .and(coatLike(filters.coat()))
                        /////////////////////////////////////
                        .and(detailsLike(filters.details()))
                        .and(purchaseEqual(filters.purchase()))
                        .and(saleEqual(filters.sale()))
                        .and(dioptreLike(filters.dioptre())));
    }

    public Specification<GlassContainer> firmContains(String filter) {

        System.out.println(".....SpacForGlasses.firmContains()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    ("%" + filter.toUpperCase() + "%"));
        };
    }

    public Specification<GlassContainer> firmLike(String filter) {

        System.out.println(".....SpacForGlasses.firmLike()...");

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    filter.toUpperCase());
        };
    }


    public Specification<GlassContainer> detailsContains(String filter) {

        System.out.println(".....SpacForGlasses.detailsContains()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("details")),
                    ("%" + filter.toUpperCase() + "%"));
        };
    }

    public Specification<GlassContainer> detailsLike(String filter) {

        System.out.println(".....SpacForGlasses.detailsLike()...");

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("details")),
                    filter.toUpperCase());
        };
    }

    public Specification<GlassContainer> purchaseEqual(BigDecimal filter) {

        System.out.println(".....SpacForGlasses.purchaseEqual()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("purchase"),
                    filter);
        };
    }

    public Specification<GlassContainer> saleEqual(BigDecimal filter) {

        System.out.println(".....SpacForGlasses.saleEqual()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("sale"),
                    filter);
        };
    }


    //////////////////////////////////////////////////////////////////////////////////////////////

    public Specification<GlassContainer> materialLike(GlassMaterial filter) {

        System.out.println(".....SpacForGlasses.materialLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("material"),
                    filter.toString());
        };
    }

    public Specification<GlassContainer> designLike(GlassDesign filter) {

        System.out.println(".....SpacForGlasses.designLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("design"),
                    filter.toString());
        };
    }

    public Specification<GlassContainer> coatLike(GlassCoat filter) {

        System.out.println(".....SpacForGlasses.coatLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("coat"),
                    filter.toString());
        };
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    public Specification<GlassContainer> dioptreLike(String filter) {

        System.out.println(".....SpacForGlasses.dioptreLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null || !filter.matches("^[+-p_]\\S*")) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("dioptre"),
                    filter);
        };
    }

}
