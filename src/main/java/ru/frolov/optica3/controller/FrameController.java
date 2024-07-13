package ru.frolov.optica3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.dto.FrameDTO;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.service.FrameService;

import java.math.BigDecimal;


@Controller
@RequiredArgsConstructor
public class FrameController {


    private final FrameService frameService;


    @GetMapping(value = "frame")
    public String getView(Model model) {
        model.addAttribute("frames", this.frameService.findAllFrames());
        return "storage";
    }


    @PostMapping("createFrame")
    public String createNewFrameAndSave(FrameDTO frameDTO, Model model) {
        if (frameDTO != null) {
            Frame frame = new Frame();
//            frame.setFrameId(this.frameService.findAllFrames().stream()
//                    .map(Frame::getFrameId)
//                    .max(Long::compareTo)
//                    .orElse(0L) + 1);

//            frame.setFrameCreatedAt(LocalDateTime.now());
//            frame.setFrameChangedAt(LocalDateTime.now());

            frame.setFirm(frameDTO.getFirm());
            frame.setModel(frameDTO.getModel());
            frame.setDetails(frameDTO.getDetails());
            frame.setPurchasePrice(frameDTO.getPurchasePrice());
            frame.setSalePrice(frameDTO.getSalePrice());
            this.frameService.saveFrame(frame);
        }
        return "redirect:/frame";
    }


    @PostMapping("updateFrame")
    public String updateFrameAndSave(
            @RequestParam(name = "firm", required = false) String firm,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "details", required = false) String details,

            @RequestParam(name = "purchasePrice", required = false) BigDecimal purchasePrice,
            @RequestParam(name = "salePrice", required = false) BigDecimal salePrice,

            @RequestParam(name = "field") String field,
            @RequestParam(name = "id") Long id) {

        ///////////////////////////////////
        System.out.println("--->>>firm:" + firm);
        System.out.println("--->>>model:" + model);
        System.out.println("--->>>details:" + details);
        System.out.println("--->>>purchasePrice:" + purchasePrice);
        System.out.println("--->>>salePrice:" + salePrice);
        System.out.println("--->>>field:" + field);
        System.out.println("--->>>id:" + id);
        ///////////////////////////////////
        Frame frame = this.frameService.findById(id).get();

        if (field.equals("firm")){
            frame.setFirm(firm);
        }else if (field.equals("model")){
            frame.setModel(model);
        }else if (field.equals("details")){
            frame.setDetails(details);
        }else if (field.equals("purchasePrice")){
            frame.setPurchasePrice(purchasePrice);
        }else if (field.equals("salePrice")){
            frame.setSalePrice(salePrice);
        }

        this.frameService.saveFrame(frame);

        return "redirect:/frame";
    }


}

