package ru.frolov.optica3.spec;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.enums.enums_for_frames.FrameInstallType;
import ru.frolov.optica3.enums.enums_for_frames.FrameMaterial;
import ru.frolov.optica3.payload.FiltersPayload;

import java.math.BigDecimal;

@UtilityClass
public class SpecForFrames {

    public Specification<FrameContainer> byAllFieldsContains(FiltersPayload filters) {
        String firm = filters.firm();
        String model = filters.model();
        String frameInstallTypeStr = filters.frameInstallType() == null ? "" : filters.frameInstallType().getStr();
        String frameMaterialStr = filters.frameMaterial() == null ? "" : filters.frameMaterial().getStr();
        String details = filters.details();
        BigDecimal purchase = filters.purchase();
        BigDecimal sale = filters.sale();
        return Specification.where(SpecForFrames.firmContains(firm)
                .and(modelContains(model))
                .and(installTypeLike(filters.frameInstallType()))
                .and(materialLike(filters.frameMaterial()))
                .and(detailsContains(details))
                .and(purchaseEqual(purchase))
                .and(saleEqual(sale)));
    }

    public Specification<FrameContainer> firmContains(String firm) {

        return (root, query, criteriaBuilder) -> {
            if (firm == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    ("%" + firm.toUpperCase() + "%"));
        };
    }

    public Specification<FrameContainer> modelContains(String model) {

        return (root, query, criteriaBuilder) -> {
            if (model == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("model")),
                    ("%" + model.toUpperCase() + "%"));
        };
    }

    public Specification<FrameContainer> detailsContains(String details) {

        return (root, query, criteriaBuilder) -> {
            if (details == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("details")),
                    ("%" + details.toUpperCase() + "%"));
        };
    }

    public Specification<FrameContainer> purchaseEqual(BigDecimal purchase) {

        return (root, query, criteriaBuilder) -> {
            if (purchase == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("purchase"),
                    purchase);
        };
    }

    public Specification<FrameContainer> saleEqual(BigDecimal sale) {

        return (root, query, criteriaBuilder) -> {
            if (sale == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("sale"),
                    sale);
        };
    }

    public Specification<FrameContainer> firmLike(String firm) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    root.get("firm"),
                    firm);
        };
    }

    public Specification<FrameContainer> modelLike(String model) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    root.get("model"),
                    model);
        };
    }

    public Specification<FrameContainer> detailsLike(String details) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    root.get("details"),
                    details);
        };
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    public Specification<FrameContainer> installTypeLike(FrameInstallType frameInstallType) {

        return (root, query, criteriaBuilder) -> {
            if (frameInstallType == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("frameInstallType"),
                    frameInstallType.toString());
        };
    }
    public Specification<FrameContainer> materialLike(FrameMaterial frameMaterial) {

        return (root, query, criteriaBuilder) -> {
            if (frameMaterial == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("frameMaterial"),
                    frameMaterial.toString());
        };
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
}
