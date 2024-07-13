package ru.frolov.optica3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {

    public TestEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Id
    private Long id;

    private String name;
    private int age;

}

