package ru.frolov.optica3.dto.products;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlassDto
        extends AbstractProductDto {


    /////////////////////////////////////
    // glass 3
    @Enumerated(EnumType.STRING)
    private GlassMaterial glassMaterial;
    @Enumerated(EnumType.STRING)
    private GlassDesign glassDesign;
    @Enumerated(EnumType.STRING)
    private GlassCoat glassCoat;

    private String dioptre;
    /////////////////////////////////////


}
