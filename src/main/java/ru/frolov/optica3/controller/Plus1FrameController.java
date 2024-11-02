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
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;
import ru.frolov.optica3.spec.FrameSpec;

@Controller
public class Plus1FrameController {

    private final FrameContainerService containerService;
    private final FrameService frameService;

    public Plus1FrameController(FrameContainerService containerService, FrameService frameService) {
        this.containerService = containerService;
        this.frameService = frameService;
    }


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/plus1")
    public String plus1(@RequestParam(name = "xId") Long xId,
                        // Нужна для:
                        // 1. Отображения текущих значений в поиске.
                        // 2. Для создания ТАКОЙ ЖЕ страницы по таким же критериям.
                        FramePayload current,
                        RedirectAttributes ra) {

        // Находим нужный контейнер по полученному id
        final FrameContainer xContainer = this.containerService.findById(xId).get();

        // создаем новую оправу с такими же значениями полей, как у контейнера
        Frame newFrame = new Frame();
        newFrame.setFirm(xContainer.getFirm());
        newFrame.setModel(xContainer.getModel());
        newFrame.setDetails(xContainer.getDetails());
        newFrame.setPurchase(xContainer.getPurchase());
        newFrame.setSale(xContainer.getSale());

        // В оправе указываем ее контейнер, а в контейнере пополняем список оправ вновь созданной оправой
        newFrame.setFrameContainer(xContainer);
        xContainer.addToFrameList(newFrame);
        // сохраняем контейнер и оправу в БД
        this.frameService.save(newFrame);
        this.containerService.save(xContainer);




        Cache.setPayload(current);


        return "redirect:/api/display";
    }
}
