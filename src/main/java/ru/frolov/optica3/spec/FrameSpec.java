package ru.frolov.optica3.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.entity.FrameContainer;

import java.math.BigDecimal;

public class FrameSpec {


    public static Specification<FrameContainer> allFieldsContains(String firm,
                                                                  String model,
                                                                  String details,
                                                                  BigDecimal purchase,
                                                                  BigDecimal sale) {
        return Specification.where(FrameSpec.firmContains(firm)
                .and(FrameSpec.modelContains(model))
                .and(FrameSpec.detailsContains(details))
                .and(FrameSpec.purchaseEqual(purchase))
                .and(FrameSpec.saleEqual(sale)));
    }

    public static Specification<FrameContainer> firmContains(String firm) {

        return (root, query, criteriaBuilder) -> {
            if (firm == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    ("%" + firm.toUpperCase() + "%"));
        };
    }

    public static Specification<FrameContainer> modelContains(String model) {

        return (root, query, criteriaBuilder) -> {
            if (model == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("model")),
                    ("%" + model.toUpperCase() + "%"));
        };
    }

    public static Specification<FrameContainer> detailsContains(String details) {

        return (root, query, criteriaBuilder) -> {
            if (details == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("details")),
                    ("%" + details.toUpperCase() + "%"));
        };
    }

    public static Specification<FrameContainer> purchaseEqual(BigDecimal purchase) {

        return (root, query, criteriaBuilder) -> {
            if (purchase == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("purchase"),
                    purchase);
        };
    }

    public static Specification<FrameContainer> saleEqual(BigDecimal sale) {

        return (root, query, criteriaBuilder) -> {
            if (sale == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("sale"),
                    sale);
        };
    }

    public static Specification<FrameContainer> firmLike(String firm) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    root.get("firm"),
                    firm);
        };
    }

    public static Specification<FrameContainer> modelLike(String model) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    root.get("model"),
                    model);
        };
    }

    public static Specification<FrameContainer> detailsLike(String details) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    root.get("details"),
                    details);
        };
    }

}
