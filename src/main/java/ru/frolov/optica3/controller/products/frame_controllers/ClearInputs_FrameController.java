package ru.frolov.optica3.controller.products.frame_controllers;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto.products.FrameDto;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.service.products.frames.FrameContainerService;
import ru.frolov.optica3.service.products.frames.FrameUnitService;


@Controller
public class ClearInputs_FrameController
        extends AbstractFrameController {


    public ClearInputs_FrameController(FrameContainerService containerService, FrameUnitService unitService) {
        super(containerService, unitService);
    }

    @PostMapping("clearInputs")
    public String clearInputs(
            Model model) {
        /*
         * Задача:
         *   В html должны очистится поля поиска. Остальное должно остаться без изменений.
         * */




        // Подготовка данных
        //-------------------
        // Кэшируем пустую dto. Из него пустые значения "очистят" поля html
        containerService.getCache().setDto(new FrameDto());

        // page берем из кэша и отправляем без изменений
        Page<FrameContainer> samePage = containerService.getCache().getPage();


        // Отправляем данные в модель
        // --------------------------->
        containerService.transferToModel(
                model,
                samePage,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        containerService.getCache().cacheAttributesNotNull(
                samePage,
                null,
                null,
                null,
                null
        );

        return "displayFrames";
    }

}
