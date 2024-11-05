package ru.frolov.optica3.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;


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
                        @RequestParam(name = "pageNumber") Integer pageNumber) {

        System.out.println("___plus1()...");

        // Находим нужный контейнер по полученному id
        final FrameContainer xContainer = this.containerService.findById(xId).get();

        // создаем новую оправу с такими же значениями полей, как у контейнера
        Frame newFrame = new Frame();
        newFrame.setFirm(xContainer.getFirm());
        newFrame.setModel(xContainer.getModel());
        newFrame.setDetails(xContainer.getDetails());
        newFrame.setPurchase(xContainer.getPurchase());
        newFrame.setSale(xContainer.getSale());

        // Соединяем xContainer и newFrame
        newFrame.setFrameContainer(xContainer);
        xContainer.addToFrameList(newFrame);
        // сохраняем контейнер и оправу в БД
        //this.frameService.save(newFrame);  // надо ли ??????????????????
        this.containerService.save(xContainer);


        return "redirect:/api/display/unknownSpec/%d".formatted(pageNumber);
    }
}
