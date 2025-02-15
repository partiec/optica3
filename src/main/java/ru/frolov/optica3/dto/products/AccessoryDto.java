package ru.frolov.optica3.dto.products;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.frolov.optica3.enums.accessory_enums.AccessoryCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryDto
        extends AbstractProductDto {


    /////////////////////////////////////////////
    // accessory 1
    @Enumerated(EnumType.STRING)
    private AccessoryCategory accessoryCategory;
    /////////////////////////////////////////////


}
