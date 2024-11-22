package ru.frolov.optica3.controller.controllers_for_frames;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.cache.EditModeCache;
import ru.frolov.optica3.cache.PageCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.service.CacheService;
import ru.frolov.optica3.service.ModelService;


@Controller
@RequiredArgsConstructor
public class EditModeFrameController {

    private final ModelService modelService;
    private final CacheService cacheService;

    @GetMapping("api/editFrames_on")
    public String editMode_on(Model model) {

        System.out.println("___editMode_on();");

        // включить режим редактирования
        EditModeCache.setEditMode(true);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<FrameContainer> samePage = PageCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                samePage,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                samePage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна

        return "displayFrames";
    }

    @GetMapping("api/editFrames_off")
    public String editMode_off(Model model) {

        System.out.println("___editMode_off();");

        EditModeCache.setEditMode(false);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<FrameContainer> samePage = PageCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelService.transferModel(
                model,
                samePage,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheService.cacheAttributes(
                samePage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна

        return "displayFrames";
    }
}
