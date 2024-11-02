package ru.frolov.optica3.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
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

import java.math.BigDecimal;
import java.util.Iterator;

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
                              FramePayload current,
                              RedirectAttributes ra) {


        // values from payload
        String firm = current.firm();
        String model = current.model();
        String details = current.details();
        BigDecimal purchase = current.purchase();
        BigDecimal sale = current.sale();

        FrameContainer xContainer = this.containerService.findById(xId).get();
        Iterator<Frame> xIterator;

        if (firm != null) {
            xContainer.setFirm(firm);
            xIterator = xContainer.getFrameList().iterator();
            while (xIterator.hasNext()) {
                xIterator.next().setFirm(firm);
            }
        } else if (model != null) {
            xContainer.setModel(model);
            xIterator = xContainer.getFrameList().iterator();
            while (xIterator.hasNext()) {
                xIterator.next().setModel(model);
            }
        } else if (details != null) {
            xContainer.setDetails(details);
            xIterator = xContainer.getFrameList().iterator();
            while (xIterator.hasNext()) {
                xIterator.next().setDetails(details);
            }
        } else if (purchase != null) {
            xContainer.setPurchase(purchase);
            xIterator = xContainer.getFrameList().iterator();
            while (xIterator.hasNext()) {
                xIterator.next().setPurchase(purchase);
            }
        } else if (sale != null) {
            xContainer.setSale(sale);
            xIterator = xContainer.getFrameList().iterator();
            while (xIterator.hasNext()) {
                xIterator.next().setSale(sale);
            }
        }

        this.containerService.save(xContainer);


        Pageable pageable = PageRequest.of(
                Cache.getPage().getNumber(),
                Defaults.PAGE_SIZE);
        Specification<FrameContainer> spec = FrameSpec.allFieldsContains(
                current.firm(),
                current.model(),
                current.details(),
                current.purchase(),
                current.sale());
        Page<FrameContainer> page = this.containerService.getPage(spec, pageable);

        Cache.setPage(page);
        Cache.setPayload(current);

        ra.addFlashAttribute("payload", current);

        return "redirect:/api/display";
    }
}
