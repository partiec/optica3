package ru.frolov.optica3.dto.products;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.enums.abstract_enums.ProductStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractProductDto {


    ////////////////////////////
    @Enumerated(EnumType.STRING)
    public ProductCategory productCategory;

    @Enumerated(EnumType.STRING)
    public ProductStatus productStatus;
    ////////////////////////////


    @NotNull
    @NotBlank(message = "Заполни 'Фирма'.")
    public String firm;
    @NotNull
    @NotBlank(message = "Заполни 'Модель'.")
    public String model;
    public String details;
    public BigDecimal purchase;
    public BigDecimal sale;


}
