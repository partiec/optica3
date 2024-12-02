package ru.frolov.optica3.spec.frame_spec;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;

import java.math.BigDecimal;

@UtilityClass
public class SpecForFrames {

    public Specification<FrameContainer> byAllFieldsContains(Filters_FramePayload filters) {

        return Specification.where(
                firmContains(filters.firm())
                        .and(modelContains(filters.model()))
                        /////////////////////////////////////
                        .and(useTypeLike(filters.useType()))
                        .and(installTypeLike(filters.installType()))
                        .and(materialLike(filters.material()))
                        /////////////////////////////////////
                        .and(detailsContains(filters.details()))
                        .and(purchaseEqual(filters.purchase()))
                        .and(saleEqual(filters.sale())));
    }

    // by base fields
    public Specification<FrameContainer> byAllFieldsLike(Filters_FramePayload filters) {
        return Specification.where(
                firmLike(filters.firm())
                        .and(modelLike(filters.model()))
                        /////////////////////////////////////
                        .and(useTypeLike(filters.useType()))
                        .and(installTypeLike(filters.installType()))
                        .and(materialLike(filters.material()))
                        /////////////////////////////////////
                        .and(detailsLike(filters.details()))
                        .and(purchaseEqual(filters.purchase()))
                        .and(saleEqual(filters.sale())));
    }

    public Specification<FrameContainer> firmContains(String filter) {

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    ("%" + filter.toUpperCase() + "%"));
        };
    }

    public Specification<FrameContainer> firmLike(String filter) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    filter.toUpperCase());
        };
    }

    public Specification<FrameContainer> modelContains(String filter) {

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("model")),
                    ("%" + filter.toUpperCase() + "%"));
        };
    }

    public Specification<FrameContainer> modelLike(String filter) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("model")),
                    filter.toUpperCase());
        };
    }

    public Specification<FrameContainer> detailsContains(String filter) {

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("details")),
                    ("%" + filter.toUpperCase() + "%"));
        };
    }

    public Specification<FrameContainer> detailsLike(String filter) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    root.get("details"),
                    filter);
        };
    }

    public Specification<FrameContainer> purchaseEqual(BigDecimal filter) {

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("purchase"),
                    filter);
        };
    }

    public Specification<FrameContainer> saleEqual(BigDecimal filter) {

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
    public Specification<FrameContainer> useTypeLike(FrameUseType filter) {

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("useType"),
                    filter.toString());
        };
    }

    public Specification<FrameContainer> installTypeLike(FrameInstallType filter) {

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("installType"),
                    filter.toString());
        };
    }

    public Specification<FrameContainer> materialLike(FrameMaterial filter) {

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("material"),
                    filter.toString());
        };
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
}
