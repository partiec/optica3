package ru.frolov.optica3.controller.frame_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.frame_caches.Page_FrameCache;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.frame_payloads.FramePayload;
import ru.frolov.optica3.service.frame_services.Cache_FrameService;
import ru.frolov.optica3.service.frame_services.FrameContainerService;
import ru.frolov.optica3.service.frame_services.Model_FrameService;
import ru.frolov.optica3.service.frame_services.Pagination_FrameService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Update_FrameController {


    private final FrameContainerService containerService;
    private final Pagination_FrameService paginationFrameService;
    private final Model_FrameService modelFrameService;
    private final Cache_FrameService cacheFrameService;


    //------------------------------------------------------------

    @PostMapping("api/updateFrame")
    public String updateFrame(@RequestParam(name = "xId") Long xId,
                              FramePayload framePayload,
                              Model model) {

        System.out.println("___updateFrame()...: FramePayload("+
                framePayload.firm() + "," +
                framePayload.model() + "," +
                framePayload.useType() + "," +
                framePayload.installType() + "," +
                framePayload.material() + "," +
                framePayload.details() + "," +
                framePayload.purchase() + "," +
                framePayload.sale() + ");");

        // находим нужный контейнер по xId
        FrameContainer xContainer = this.containerService.findById(xId).get();


        // если во framePayload какое-то из полей не null, значит
        // измененное значение именно этого поля пришло из формы.
        // Устанавливаем это значение в контейнер, а старое значение затирается.
        if (framePayload.firm() != null) {
            xContainer.setFirm(framePayload.firm());
        }
        if (framePayload.model() != null) {
            xContainer.setModel(framePayload.model());
        }
        /////////////////////////////////////////////////////////
        // раскрывающиеся списки
        if (framePayload.useType() != null) {
            xContainer.setUseType(framePayload.useType());
        }
        if (framePayload.installType() != null) {
            xContainer.setInstallType(framePayload.installType());
        }
        if (framePayload.material() != null) {
            xContainer.setMaterial(framePayload.material());
        }
        /////////////////////////////////////////////////////////
        if (framePayload.details() != null) {
            xContainer.setDetails(framePayload.details());
        }
        if (framePayload.purchase() != null) {
            xContainer.setPurchase(framePayload.purchase());
        }
        if (framePayload.sale() != null) {
            xContainer.setSale(framePayload.sale());
        }


        // пытаемся найти контейнеры-дубли
        List<FrameContainer> dubls = this.containerService.dubls(xContainer);
        // если есть дубли
        if (!dubls.isEmpty()) {
            // применить метод из containerService, который
            //  - извлекает frames из контейнеров-дублей (дубли удаляет),
            //  - переименовывает их поля, чтоб были как у xContainer
            //  - вставляет обновленные frames в xContainer (xContainer становится единственным)
            this.containerService.killDubls(xContainer, dubls);
        }

        // сохраняем измененный контейнер
        this.containerService.save(xContainer);

        // Подготовка данных для модели
        // ---------------------------->
        // page должна остаться с тем же номером и spec, но обновлена (для info html)
        int pageNumber = Page_FrameCache.getPage().getNumber();
        Page<FrameContainer> actualPage = this.paginationFrameService.createPageDependsOnSpecStatusAndCacheSpecStatus(pageNumber);

        // Отправляем данные в модель
        // --------------------------->
        modelFrameService.transferModel(
                model,
                actualPage,
                pageNumber,
                xId,
                null);

        // Контрольное кэширование
        // ------------------------->
        cacheFrameService.cacheAttributes(
                actualPage,
                null,
                null,
                framePayload);


        return "displayFrames";
    }


}
