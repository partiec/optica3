package ru.frolov.optica3.specification.client_spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.client.Client;
import ru.frolov.optica3.entity.order._Order;

@Component
public class ClientSpec {


    public Specification<Client> allContains(OrderAndClientDto filters) {
        System.out.println(getClass().getSimpleName() + ". allContains()принят OrderDto = " + filters);
        return Specification.where(
                        // client's fields
                        (lastNameContains(filters.getLastName()))
                        .and(firstNameContains(filters.getFirstName()))
                        .and(patronymicContains(filters.getPatronymic()))
                        .and(passportContains(filters.getPassport()))
                        .and(clientDetailsContains(filters.getClientDetails()))
                /////////////////////////////////////
        );
    }


    public Specification<Client> allLike(OrderAndClientDto filters) {
        System.out.println(getClass().getSimpleName() + ". allLike()принят OrderDto = " + filters);
        return Specification.where(
                        // client's fields
                        (lastNameLike(filters.getLastName()))
                        .and(firstNameLike(filters.getFirstName()))
                        .and(patronymicLike(filters.getPatronymic()))
                        .and(passportLike(filters.getPassport()))
                        .and(clientDetailsLike(filters.getClientDetails()))
                /////////////////////////////////////
        );
    }


    //========================================================================================================





    // client's fields
    //-----------------
    // client lastName
    public Specification<Client> lastNameContains(String filter) {
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

    public Specification<Client> lastNameLike(String filter) {
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
    public Specification<Client> firstNameContains(String filter) {
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

    public Specification<Client> firstNameLike(String filter) {
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
    public Specification<Client> patronymicContains(String filter) {
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

    public Specification<Client> patronymicLike(String filter) {
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

    // client passport
    public Specification<Client> passportContains(String filter) {
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

    public Specification<Client> passportLike(String filter) {
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
    public Specification<Client> clientDetailsContains(String filter) {
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

    public Specification<Client> clientDetailsLike(String filter) {
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
