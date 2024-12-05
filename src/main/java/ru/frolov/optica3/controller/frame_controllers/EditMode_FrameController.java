package ru.frolov.optica3.controller.frame_controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.cache.frame_caches.EditMode_FrameCache;
import ru.frolov.optica3.cache.frame_caches.Page_FrameCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.service.frame_services.Cache_FrameService;
import ru.frolov.optica3.service.frame_services.Model_FrameService;


@Controller
@RequiredArgsConstructor
public class EditMode_FrameController {

    private final Model_FrameService modelFrameService;
    private final Cache_FrameService cacheFrameService;

    @GetMapping("api/editFrame_on")
    public String editMode_on(Model model) {

        System.out.println("___editMode_on();");

        // включить режим редактирования
        EditMode_FrameCache.setEditMode(true);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<FrameContainer> samePage = Page_FrameCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelFrameService.transferModel(
                model,
                samePage,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheFrameService.cacheAttributes(
                samePage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна

        return "displayFrames";
    }

    @GetMapping("api/editFrame_off")
    public String editMode_off(Model model) {

        System.out.println("___editMode_off();");

        EditMode_FrameCache.setEditMode(false);

        // Подготовка данных для модели
        // ----------------------------->
        // page должна остаться та же и по номеру и по spec
        Page<FrameContainer> samePage = Page_FrameCache.getPage();

        // Отправляем данные в модель
        // --------------------------->
        modelFrameService.transferModel(
                model,
                samePage,
                null,
                null,
                null,
                null);

        // Контрольное кэширование
        // ----------------------->
        cacheFrameService.cacheAttributes(
                samePage,
                null, // (specStatus) - определяется и кэшируется в методе createPageDependsOnSpecStatus
                null, // (filters) - не изменился в кэше
                null); // (framePayload) - не нужна

        return "displayFrames";
    }
}
