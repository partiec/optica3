package ru.frolov.optica3.specification.products;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;

import java.math.BigDecimal;


@RequiredArgsConstructor
public abstract class AbstractProductSpec {


//    public abstract Specification<C> allLike(AbstractDto filters);
//
//    public abstract Specification<C> allContains(AbstractDto<C, U> filters);

    ///////////////////////////////////////////////////////////////////////////////
    public Specification productCategoryLike(ProductCategory filter) {
        System.out.println("принят ProductCategory = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("_____productCategoryLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("productCategoryLike(" + filter + ") - FIND");
            return criteriaBuilder.like(
                    root.get("productCategory"),
                    filter.toString());
        };
    }
    ////////////////////////////////////////////////////////////////////////////////


    public Specification firmContains(String filter) {
        System.out.println("принят firm = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("firmContains(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("firmContains(" + filter + ") - FIND");
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    ("%" + filter.trim().toUpperCase() + "%"));
        };
    }

    public Specification firmLike(String filter) {
        System.out.println("принят firm = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("firmLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("_____firmLike(" + filter + ") - FIND");
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    filter.trim().toUpperCase());
        };
    }

    public Specification modelContains(String filter) {
        System.out.println("принят model = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("modelContains(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("modelContains(" + filter + ") - FIND");
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("model")),
                    ("%" + filter.trim().toUpperCase() + "%"));
        };
    }

    public Specification modelLike(String filter) {
        System.out.println("принят model = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("modelLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("modelLike(" + filter + ") - FIND");
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("model")),
                    filter.trim().toUpperCase());
        };
    }

    public Specification detailsContains(String filter) {
        System.out.println("принят details = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("_____detailsContains(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("detailsContains(" + filter + ") - FIND");
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("details")),
                    ("%" + filter.trim().toUpperCase() + "%"));
        };
    }

    public Specification detailsLike(String filter) {
        System.out.println("принят details = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("detailsLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("detailsLike(" + filter + ") - FIND");
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("details")),
                    filter.trim().toUpperCase());
        };
    }

    public Specification purchaseEqual(BigDecimal filter) {
        System.out.println("принят purchase BigDecimel = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("_____purchaseEqual(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("purchaseEqual(" + filter + ") - FIND");
            return criteriaBuilder.equal(
                    root.get("purchase"),
                    filter);
        };
    }

    public Specification saleEqual(BigDecimal filter) {
        System.out.println("принят sale BigDecimal = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("_____saleEqual(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("saleEqual(" + filter + ") - FIND");
            return criteriaBuilder.equal(
                    root.get("sale"),
                    filter);
        };
    }


}
