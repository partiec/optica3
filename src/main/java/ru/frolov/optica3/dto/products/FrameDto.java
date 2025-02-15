package ru.frolov.optica3.dto.products;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrameDto
        extends AbstractProductDto {


    //////////////////////////////////////////
    //frame 3
    @Enumerated(EnumType.STRING)
    private FrameUseType frameUseType;
    @Enumerated(EnumType.STRING)
    private FrameInstallType frameInstallType;
    @Enumerated(EnumType.STRING)
    private FrameMaterial frameMaterial;
    //////////////////////////////////////////


}
