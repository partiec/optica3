package ru.frolov.optica3.specification.products;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto.products.ContactDto;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.enums.contact_enums.*;

@Component
public class ContactSpec
        extends AbstractProductSpec {




    public Specification<ContactContainer> allContains(ContactDto filters) {
        return Specification.where(
                productCategoryLike(filters.getProductCategory())
                        .and(firmContains(filters.getFirm()))
                        .and(modelContains(filters.getModel()))
                        .and(detailsContains(filters.getDetails()))
                        .and(purchaseEqual(filters.getPurchase()))
                        .and(saleEqual(filters.getSale()))
                        /////////////////////////////////////
                        .and(contactDesignLike(filters.getContactDesign()))
                        .and(contactPeriodLike(filters.getContactPeriod()))
                        .and(contactOxygenLike(filters.getContactOxygen()))
                        .and(contactWaterLike(filters.getContactWater()))
                        .and(contactDiameterLike(filters.getContactDiameter()))
                        .and(contactRadiusLike(filters.getContactRadius()))
                        .and(dioptreLike(filters.getDioptre()))
        );


    }


    public Specification<ContactContainer> allLike(ContactDto filters) {
        System.out.println("spec: byAllFieldsLike(filters)...");

        return Specification.where(
                productCategoryLike(filters.getProductCategory())
                        .and(firmLike(filters.getFirm()))
                        .and(modelLike(filters.getModel()))
                        .and(detailsLike(filters.getDetails()))
                        .and(purchaseEqual(filters.getPurchase()))
                        .and(saleEqual(filters.getSale()))
                        /////////////////////////////////////
                        .and(contactDesignLike(filters.getContactDesign()))
                        .and(contactPeriodLike(filters.getContactPeriod()))
                        .and(contactOxygenLike(filters.getContactOxygen()))
                        .and(contactWaterLike(filters.getContactWater()))
                        .and(contactDiameterLike(filters.getContactDiameter()))
                        .and(contactRadiusLike(filters.getContactRadius()))
                        .and(dioptreLike(filters.getDioptre()))
        );
    }


    //========================================================================================================
    //========================================================================================================


    //-------------------------------
    // contact 6 + dioptre
    //-------------------------------
    public Specification<ContactContainer> contactDesignLike(ContactDesign filter) {
        System.out.println("принят ContactDesign = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("contactDesignLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("contactDesignLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.like(
                    root.get("contactDesign"),
                    filter.toString());
        };
    }

    public Specification<ContactContainer> contactPeriodLike(ContactPeriod filter) {
        System.out.println("принят ContactPeriod = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("contactPeriodLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("contactPeriodLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.like(
                    root.get("contactPeriod"),
                    filter.toString());
        };
    }

    public Specification<ContactContainer> contactOxygenLike(ContactOxygen filter) {
        System.out.println("принят ContactOxygen = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("contactOxygenLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("contactOxygenLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.like(
                    root.get("contactOxygen"),
                    filter.toString());
        };
    }

    public Specification<ContactContainer> contactWaterLike(ContactWater filter) {
        System.out.println("принят ContactWater = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("contactWaterLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("contactWaterLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.like(
                    root.get("contactWater"),
                    filter.toString());
        };
    }


    public Specification<ContactContainer> contactDiameterLike(ContactDiameter filter) {
        System.out.println("принят ContactDiameter = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("contactDiameterLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("contactDiameterLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.like(
                    root.get("contactDiameter"),
                    filter.toString());
        };
    }

    public Specification<ContactContainer> contactRadiusLike(ContactRadius filter) {
        System.out.println("принят ContactRadius = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("contactRadiusLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("contactRadiusLike(" + filter + "/" + filter.getStr() + ") - FIND");
            return criteriaBuilder.like(
                    root.get("contactRadius"),
                    filter.toString());
        };
    }

    public Specification<ContactContainer> dioptreLike(String filter) {
        System.out.println("принят String = " + filter);
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("dioptreLike(null) - PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("dioptreLike(" + filter + ") - FIND");
            return criteriaBuilder.like(
                    root.get("dioptre"),
                    filter);

        };
    }


}
