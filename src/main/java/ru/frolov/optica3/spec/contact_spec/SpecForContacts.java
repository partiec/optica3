package ru.frolov.optica3.spec.contact_spec;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.enums.contact_enums.*;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;

import java.math.BigDecimal;

@UtilityClass
public class SpecForContacts {


    public Specification<ContactContainer> byAllContains(Filters_ContactPayload filters) {

        System.out.println(".....SpecForContacts.byAllContains()... ");

        return Specification.
                where(firmContains(filters.firm())
                        /////////////////////////////////////
                        .and(designLike(filters.design()))
                        .and(periodLike(filters.period()))
                        .and(oxygenLike(filters.oxygen()))
                        .and(waterLike(filters.water()))
                        /////////////////////////////////////
                        .and(detailsContains(filters.details()))
                        .and(purchaseEqual(filters.purchase()))
                        .and(saleEqual(filters.sale()))
                        ////////////
                        .and(diameterLike(filters.diameter()))
                        .and(radiusLike(filters.radius()))
                        ////////////
                        .and(dioptreLike(filters.dioptre())));
    }

    public Specification<ContactContainer> byAllLike(Filters_ContactPayload filters) {

        System.out.println(".....SpecForContacts.byAllLike()... ");

        return Specification.
                where(firmLike(filters.firm())
                        /////////////////////////////////////
                        .and(designLike(filters.design()))
                        .and(periodLike(filters.period()))
                        .and(oxygenLike(filters.oxygen()))
                        .and(waterLike(filters.water()))
                        /////////////////////////////////////
                        .and(detailsLike(filters.details()))
                        .and(purchaseEqual(filters.purchase()))
                        .and(saleEqual(filters.sale()))
                        ////////////
                        .and(diameterLike(filters.diameter()))
                        .and(radiusLike(filters.radius()))
                        ////////////
                        .and(dioptreLike(filters.dioptre())));
    }


    // by base fields


    public Specification<ContactContainer> firmContains(String filter) {

        System.out.println(".....SpacForContacts.firmContains()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    ("%" + filter.toUpperCase() + "%"));
        };
    }

    public Specification<ContactContainer> firmLike(String filter) {

        System.out.println(".....SpacForContacts.firmLike()...");

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("firm")),
                    filter.toUpperCase());
        };
    }


    //////////////////////////////////////////////////////////////////////////////////////////////


    public Specification<ContactContainer> designLike(ContactDesign filter) {

        System.out.println(".....SpacForConntacts.designLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("design"),
                    filter.getStr());
        };
    }

    public Specification<ContactContainer> periodLike(ContactPeriod filter) {

        System.out.println(".....SpacForConntacts.periodLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("period"),
                    filter.getStr());
        };
    }

    public Specification<ContactContainer> oxygenLike(ContactOxygen filter) {

        System.out.println(".....SpacForConntacts.oxygenLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("oxygen"),
                    filter.getStr());
        };
    }

    public Specification<ContactContainer> waterLike(ContactWater filter) {

        System.out.println(".....SpacForConntacts.waterLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("water"),
                    filter.getStr());
        };
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    public Specification<ContactContainer> detailsContains(String filter) {

        System.out.println(".....SpacForContacts.detailsContains()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("details")),
                    ("%" + filter.toUpperCase() + "%"));
        };
    }

    public Specification<ContactContainer> detailsLike(String filter) {

        System.out.println(".....SpacForContacts.detailsLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("details")),
                    (filter.toUpperCase()));
        };
    }

    public Specification<ContactContainer> purchaseEqual(BigDecimal filter) {

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

    public Specification<ContactContainer> saleEqual(BigDecimal filter) {

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

    /////////////////////////////////////////
    public Specification<ContactContainer> diameterLike(ContactDiameter filter) {

        System.out.println(".....SpacForGlasses.diameterLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("diameter"),
                    filter.getStr());
        };
    }

    public Specification<ContactContainer> radiusLike(ContactRadius filter) {

        System.out.println(".....SpacForGlasses.radiusLike()...");

        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("radius"),
                    filter.getStr());
        };
    }

    /////////////////////////////////////////
    public Specification<ContactContainer> dioptreLike(String filter) {

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
