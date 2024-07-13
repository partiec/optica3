package ru.frolov.optica3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrameDTO {

    private Long id;

    private LocalDateTime changedAt;

    private String firm;
    private String model;
    private String details;

    private BigDecimal purchasePrice;
    private BigDecimal salePrice;

}
