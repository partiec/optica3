package ru.frolov.optica3.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;
import ru.frolov.optica3.spec.FrameSpec;

import java.util.List;

@Controller
public class Minus1FrameController {

    private final FrameContainerService containerService;
    private final FrameService frameService;

    public Minus1FrameController(FrameContainerService containerService, FrameService frameService) {
        this.containerService = containerService;
        this.frameService = frameService;
    }


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/minus1")
    public String minus1(@RequestParam(name = "xId") Long xId,
                         // Нужна для:
                         // 1. Отображения текущих значений в поиске.
                         // 2. Для создания ТАКОЙ ЖЕ страницы по таким же критериям.
                         FramePayload current) {

        // Находим нужный контейнер по полученному id
        FrameContainer xContainer = this.containerService.findById(xId).get();
        List<Frame> xList = xContainer.getFrameList();

        // Если в контейнере осталась лишь одна оправа, то удалить весь контейнер из БД
        if (xList.size() <= 1) {
            this.containerService.delete(xContainer);
        } else {
            // Иначе удалить из контейнера только НУЛЕВУЮ (по-сути первую) оправу
            Frame firstElement = xContainer.getFrameList().get(0);
            xContainer.getFrameList().remove(firstElement);
            // сохранить контейнер в БД
            this.containerService.save(xContainer);
        }

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
