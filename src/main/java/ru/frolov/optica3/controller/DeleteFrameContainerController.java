package ru.frolov.optica3.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;
import ru.frolov.optica3.spec.FrameSpec;

@Controller
public class DeleteFrameContainerController {

    private final FrameContainerService containerService;
    private final FrameService frameService;

    public DeleteFrameContainerController(FrameContainerService containerService, FrameService frameService) {
        this.containerService = containerService;
        this.frameService = frameService;
    }


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/delete")
    public String minus1(@RequestParam(name = "xId") Long xId,
                         // Нужна для:
                         // 1. Отображения текущих значений в поиске.
                         // 2. Для создания ТАКОЙ ЖЕ страницы по таким же критериям.
                         FramePayload current,
                         RedirectAttributes ra) {


        // Находим нужный контейнер по полученному id и удаляем его
        FrameContainer xContainer = this.containerService.findById(xId).get();
        this.containerService.delete(xContainer);

        // нужна та же page, но с учетом вновь созданного продукта
        //--------------------------------------------------------
        // номер страницы берем из кэша
        int beforePageNumber = Cache.getPage().getNumber();

        // создаем page  в зависимости от Cache.isUseSpec()
        Page<FrameContainer> page;
        Pageable pageable = PageRequest.of(beforePageNumber, Defaults.PAGE_SIZE);
        if (Cache.specWasUsed()) {

            page = this.containerService.getPage(FrameSpec.allFieldsContains(
                            current.firm(),
                            current.model(),
                            current.details(),
                            current.purchase(),
                            current.sale()),
                    pageable);
        } else {
            page = this.containerService.getPage(pageable);
        }

        Cache.setPage(page);
        Cache.setPayload(current);


        return "redirect:/api/display";
    }
}
