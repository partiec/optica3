package ru.frolov.optica3.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;

import java.util.List;

@Controller
public class CreateFrameController {

    private final FrameService frameService;
    private final FrameContainerService containerService;

    public CreateFrameController(FrameService frameService, FrameContainerService containerService) {
        this.frameService = frameService;
        this.containerService = containerService;
    }
    //------------------------------------------------------------------------------------------------------------

    @PostMapping("api/createFrame")
    public String createFrame(FramePayload payload) {


        // создать new frame
        Frame newFrame = new Frame();
        newFrame.setFirm(payload.firm());
        newFrame.setModel(payload.model());
        newFrame.setDetails(payload.details());
        newFrame.setPurchase(payload.purchase());
        newFrame.setSale(payload.sale());
        // создать новый контейнер
        FrameContainer xContainer = new FrameContainer();
        xContainer.setFirm(payload.firm());
        xContainer.setModel(payload.model());
        xContainer.setDetails(payload.details());
        xContainer.setPurchase(payload.purchase());
        xContainer.setSale(payload.sale());

        // установить, что новая оправа принадлежит новому контейнеру
        newFrame.setFrameContainer(xContainer);
        // добавить новую оправу в список
        xContainer.addToFrameList(newFrame);
        // сохранить контейнер
        this.containerService.save(xContainer);

        // после создания надо отобразить страницу, содержащую вновь созданный контейнер (без spec)
        List<FrameContainer> content = this.containerService.all();
        int totalPages = content.size() / Defaults.PAGE_SIZE + 1;
        Page<FrameContainer> xPage = null;
        boolean catchXPage = false;
        for (int i = 0; i < totalPages; i++) {
            // Создать страницу номер i
            Pageable xPageable = PageRequest.of(i, Defaults.PAGE_SIZE);
            xPage = this.containerService.getPage(xPageable);

            // перебирать content каждой страницы пока не найдем содержащую xContainer
            for (FrameContainer x : xPage.getContent()) {
                if (x == xContainer) {
                    catchXPage = true;
                    break;
                }
            }
            if (catchXPage) break;
        }

        Cache.setPage(xPage);


        return "redirect:/api/display";
    }
}
