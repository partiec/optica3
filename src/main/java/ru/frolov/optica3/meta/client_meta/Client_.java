package ru.frolov.optica3.meta.client_meta;


import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ru.frolov.optica3.entity.client.Client;

import javax.annotation.processing.Generated;
import java.time.LocalDate;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ {

    public static volatile SingularAttribute<Client, Long> id;

    public static volatile SingularAttribute<Client, String> lastName;
    public static volatile SingularAttribute<Client, String> firstName;
    public static volatile SingularAttribute<Client, String> patronymic;
    public static volatile SingularAttribute<Client, LocalDate> birthday;
    public static volatile SingularAttribute<Client, String> passport;
    public static volatile SingularAttribute<Client, String> details;


}
