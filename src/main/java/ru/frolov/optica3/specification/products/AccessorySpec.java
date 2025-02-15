package ru.frolov.optica3.specification.products;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto.products.AccessoryDto;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.enums.accessory_enums.AccessoryCategory;

@Component
public class AccessorySpec
        extends AbstractProductSpec {


    public Specification<AccessoryContainer> allContains(AccessoryDto filters) {

        return Specification.where(
                productCategoryLike(filters.getProductCategory())
                        .and(firmContains(filters.getFirm()))
                        .and(modelContains(filters.getModel()))
                        .and(detailsContains(filters.getDetails()))
                        .and(purchaseEqual(filters.getPurchase()))
                        .and(saleEqual(filters.getSale()))
                        /////////////////////////////////////
                        .and(accessoryCategoryLike(filters.getAccessoryCategory()))

        );
    }


    public Specification<AccessoryContainer> allLike(AccessoryDto filters) {

        return Specification.where(
                productCategoryLike(filters.getProductCategory())
                        .and(firmLike(filters.getFirm()))
                        .and(modelLike(filters.getModel()))
                        .and(detailsLike(filters.getDetails()))
                        .and(purchaseEqual(filters.getPurchase()))
                        .and(saleEqual(filters.getSale()))
                        /////////////////////////////////////
                        .and(accessoryCategoryLike(filters.getAccessoryCategory()))
        );
    }


    //========================================================================================================


    //-------------------------------
    // accessory 1
    //-------------------------------
    public Specification<AccessoryContainer> accessoryCategoryLike(AccessoryCategory filter) {
        System.out.println("принят AccessoryCategory = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("accessoryCategoryLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("accessoryCategoryLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.equal(
                    root.get("accessoryCategory"),
                    filter.toString());
        };
    }


}
