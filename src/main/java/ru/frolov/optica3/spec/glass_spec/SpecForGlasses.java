package ru.frolov.optica3.spec.glass_spec;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;

import java.math.BigDecimal;

@UtilityClass
public class SpecForGlasses {

    public Specification<GlassContainer> byAllFieldsContains(Filters_GlassPayload filters) {

        return Specification.where(
                firmContains(filters.firm())
                        /////////////////////////////////////
                        .and(materialLike(filters.material()))
                        .and(designLike(filters.design()))
                        .and(coatLike(filters.coat()))
                        /////////////////////////////////////
                        .and(detailsContains(filters.details()))
                        .and(purchaseEqual(filters.purchase()))
                        .and(saleEqual(filters.sale())));
    }

    // by base fields
    public Specification<GlassContainer> byAllFieldsLike(Filters_GlassPayload filters) {
        return Specification.where(
                firmLike(filters.firm())
                        /////////////////////////////////////
                        .and(materialLike(filters.material()))
                        .and(designLike(filters.design()))
                        .and(coatLike(filters.coat()))
                        /////////////////////////////////////
                        .and(detailsContains(filters.details()))
                        .and(purchaseEqual(filters.purchase()))
                        .and(saleEqual(filters.sale())));
    }

    public Specification<GlassContainer> firmContains(String filter) {

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

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    filter.toUpperCase());
        };
    }



    public Specification<GlassContainer> detailsContains(String filter) {

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

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    root.get("details"),
                    filter);
        };
    }

    public Specification<GlassContainer> purchaseEqual(BigDecimal filter) {

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
}
