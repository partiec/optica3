package ru.frolov.optica3.specification.order_spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.enums.order_enums.OrderPayment;
import ru.frolov.optica3.enums.order_enums.OrderStage;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class OrderSpec {


    public Specification<_Order> allContains(OrderAndClientDto filters) {
        System.out.println(getClass().getSimpleName() + ". allContains()принят OrderDto = " + filters);
        return Specification.where(
                idLike(filters.getId())
                        .and(currentLike(filters.getCurrent()))
                        .and(stageLike(filters.getStage()))
                        .and(paymentLike(filters.getPayment()))
                        .and(orderDetailsContains(filters.getOrderDetails()))
                        .and(priceEqual(filters.getPrice()))
                        ////////////////////////////////////
                        // client's fields
                        .and(lastNameContains(filters.getLastName()))
                        .and(firstNameContains(filters.getFirstName()))
                        .and(patronymicContains(filters.getPatronymic()))
                        //.and(birthdayEqual(filters.getBirthday())) не использовать
                        .and(passportContains(filters.getPassport()))
                        .and(clientDetailsContains(filters.getClientDetails()))
                /////////////////////////////////////
        );
    }



    public Specification<_Order> allLike(OrderAndClientDto filters) {
        System.out.println(getClass().getSimpleName() + ". allLike()принят OrderDto = " + filters);
        return Specification.where(
                idLike(filters.getId())
                        .and(currentLike(filters.getCurrent()))
                        .and(stageLike(filters.getStage()))
                        .and(paymentLike(filters.getPayment()))
                        .and(orderDetailsLike(filters.getOrderDetails()))
                        .and(priceEqual(filters.getPrice()))
                        ////////////////////////////////////
                        // client's fields
                        .and(lastNameLike(filters.getLastName()))
                        .and(firstNameLike(filters.getFirstName()))
                        .and(patronymicLike(filters.getPatronymic()))
                        //.and(birthdayEqual(filters.getBirthday())) не использовать
                        .and(passportLike(filters.getPassport()))
                        .and(clientDetailsLike(filters.getClientDetails()))
                /////////////////////////////////////
        );
    }


    //========================================================================================================


    // id
    public Specification<_Order> idLike(Long filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("idLike(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("idLike(Long=" + filter + ") FIND");
            return criteriaBuilder.equal(
                    root.get("id"),
                    filter);
        };
    }

    public Specification<_Order>currentLike(Boolean filter){
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("currentLike(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("currentLike(Boolean=" + filter + ") FIND");
            return criteriaBuilder.equal(
                    root.get("current"),
                    filter);
        };
    }

    // stage
    public Specification<_Order> stageLike(OrderStage filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("stageLike(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("stageLike(OrderStage=" + filter + ") FIND");
            return criteriaBuilder.equal(
                    root.get("stage"),
                    filter.toString());
        };
    }

    // payment
    public Specification<_Order> paymentLike(OrderPayment filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("paymentLike(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("paymentLike(OrderPayment=" + filter + ") FIND");
            return criteriaBuilder.equal(
                    root.get("payment"),
                    filter.toString());
        };
    }

    // details (of order)
    public Specification<_Order> orderDetailsContains(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("orderDetailsContains(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("orderDetailsContains(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("orderDetails"),
                    ("%" + filter.trim() + "%"));
        };
    }

    public Specification<_Order> orderDetailsLike(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("orderDetailsLike(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("orderDetailsLike(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("orderDetails"),
                    filter.trim());
        };
    }


    // price
    public Specification<_Order> priceEqual(BigDecimal filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                System.out.println("priceEqual(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("priceEqual(BigDecimal=" + filter + ") FIND");
            return criteriaBuilder.equal(
                    root.get("price"),
                    filter);
        };
    }


    // client's fields
    //-----------------
    // client lastName
    public Specification<_Order> lastNameContains(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("lastNameContains(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("lastNameContains(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("lastName"),
                    ("%" + filter.trim() + "%"));
        };
    }

    public Specification<_Order> lastNameLike(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("lastNameLike(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("lastNameLike(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("lastName"),
                    filter.trim());
        };
    }

    // client firstName
    public Specification<_Order> firstNameContains(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("firstNameContains(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("firstNameContains(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("firstName"),
                    ("%" + filter.trim() + "%"));
        };
    }

    public Specification<_Order> firstNameLike(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("firstNameLike(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("firstNameLike(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("firstName"),
                    filter.trim());
        };
    }

    // client patronymic
    public Specification<_Order> patronymicContains(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("patronymicContains(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("patronymicContains(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("patronymic"),
                    ("%" + filter.trim() + "%"));
        };
    }

    public Specification<_Order> patronymicLike(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("patronymicLike(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("patronymicLike(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("patronymic"),
                    filter.trim());
        };
    }

    // client birthday
    // не использовать для поиска
    private Specification<_Order> birthdayEqual(LocalDate filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null ) {
                System.out.println("birthdayLike(LocalDate=null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("birthdayLike(LocalDate=" + filter + ") FIND");
            return criteriaBuilder.equal(
                    root.get("birthday"),
                    filter);
        };
    }

    // client passport
    public Specification<_Order> passportContains(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("passportContains(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("passportContains(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("passport"),
                    ("%" + filter.trim() + "%"));
        };
    }

    public Specification<_Order> passportLike(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("passportLike(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("passportLike(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("passport"),
                    filter.trim());
        };
    }

    // client details
    public Specification<_Order> clientDetailsContains(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("clientDetailsContains(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("clientDetailsContains(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("clientDetails"),
                    ("%" + filter.trim() + "%"));
        };
    }

    public Specification<_Order> clientDetailsLike(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isBlank()) {
                System.out.println("clientDetailsLike(null) PASS");
                return criteriaBuilder.conjunction();
            }
            System.out.println("clientDetailsLike(String=" + filter + ") FIND");
            return criteriaBuilder.like(
                    root.get("clientDetails"),
                    filter.trim());
        };
    }
}
