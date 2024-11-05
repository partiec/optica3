package ru.frolov.optica3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;

@Controller
public class UpdateFrameController {

    private final FrameService frameService;
    private final FrameContainerService containerService;

    public UpdateFrameController(FrameService frameService, FrameContainerService containerService) {
        this.frameService = frameService;
        this.containerService = containerService;
    }
    //------------------------------------------------------------

    @PostMapping("api/updateFrame")
    public String updateFrame(@RequestParam(name = "xId") Long xId,
                              @RequestParam(name = "pageNumber") Integer pageNumber,
                              FramePayload framePayload) {

        System.out.println("___updateFrame()...");
        System.out.println("///xId = " + xId);
        System.out.println("///pageNumber = " + pageNumber);
        System.out.print("///framePayload(");
        System.out.print(framePayload.firm() + ", ");
        System.out.print(framePayload.model() + ", ");
        System.out.print(framePayload.details() + ", ");
        System.out.print(framePayload.purchase() + ", ");
        System.out.print(framePayload.sale() + ")");


        FrameContainer xContainer = this.containerService.findById(xId).get();


        if (framePayload.firm() != null) {
            xContainer.setFirm(framePayload.firm());
        }
        if (framePayload.model() != null) {
            xContainer.setModel(framePayload.model());
        }
        if (framePayload.details() != null) {
            xContainer.setDetails(framePayload.details());
        }
        if (framePayload.purchase() != null) {
            xContainer.setPurchase(framePayload.purchase());
        }
        if (framePayload.sale() != null) {
            xContainer.setSale(framePayload.sale());
        }

        this.containerService.save(xContainer);



        //Cache.setPayload(current);

        return "redirect:/api/display/unknownSpec/%d".formatted(pageNumber);
    }
}
