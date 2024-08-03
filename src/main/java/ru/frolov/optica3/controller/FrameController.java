package ru.frolov.optica3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("api")
public class FrameController {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_SIZE_OF_PAGE = 5;
    private static final String DEFAULT_FIELD_FOR_SORTING = "createdAt";


    private final FrameService frameService;


    // 1.
    @GetMapping("frames")
    public String all(Model model) {
        model.addAttribute("frames", this.frameService.findAll());
        return "storage";
    }

    // 2. sort
    @GetMapping("sortedFrames/{field}")
    public String sorting(
            Model model,
            @PathVariable(name = "field") String field) {

        model.addAttribute("frames", this.frameService.sortBy(field));
        return "storage";
    }

    @GetMapping(value = "pageOfFrames/{offset}/{size}")
    public String paging(
            Model model,
            @PathVariable(name = "offset") int offset,
            @PathVariable(name = "size") int size) {

        Page<Frame> page = this.frameService.getPage(offset, size);

        List<Frame> pageContent = page.getContent();
        long totalFrames = page.getTotalElements();
        long totalPages = page.getTotalPages();


        model.addAttribute("frames", pageContent);
        model.addAttribute("totalFrames", totalFrames);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("offset", offset);

        return "storage";
    }

    //============================================================================================================================
    @GetMapping("begin")
    public String begin(Model model) {
        System.out.println("ps()");
        helpExtractAttrsFromPageObjAndFillModel(model, DEFAULT_PAGE_NUMBER);

        return "storage";
    }

    private void helpExtractAttrsFromPageObjAndFillModel(Model model, int pageNumber) {
        Page<Frame> page = this.frameService.getSortedPage(pageNumber, DEFAULT_SIZE_OF_PAGE, DEFAULT_FIELD_FOR_SORTING);

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("frames", page.getContent());
        model.addAttribute("totalFrames", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
    }

    @GetMapping("doneFrameOperationPage/{pageNumber:\\d+}")
    public String doneCreateFrame(
            Model model,
            @PathVariable("pageNumber") int pageNumber
    ) {
        System.out.println("doneCreateFrame(), pageNumber="  + pageNumber);

        helpExtractAttrsFromPageObjAndFillModel(model, pageNumber);

        Map map = model.asMap();
        System.out.println("!model iteration:");
        for (Object key : map.keySet()) {
            Object mAttr = map.get(key);
            System.out.println("\t\t." + mAttr);
        }

        return "storage";
    }
    //----------------->>>

    @PostMapping("createFrame")
    public String createFrame(@Valid FramePayload framePayload,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes ra) {
        System.out.println("createFrame()");

        if (bindingResult.hasErrors()) {
            System.out.println("if has errors");
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();

            ra.addFlashAttribute("errors", errors);

            ra.addFlashAttribute("framePayload", framePayload);

        } else {
            System.out.println("else");
            if (framePayload != null) {
                Frame frame = new Frame();

                frame.setFirm(framePayload.firm());
                frame.setModel(framePayload.model());
                frame.setDetails(framePayload.details());
                frame.setPurchasePrice(framePayload.purchasePrice());
                frame.setSalePrice(framePayload.salePrice());
                this.frameService.save(frame);
            }


        }
        System.out.println("return from createFrame()");
        return "redirect:/api/doneFrameOperationPage/".concat(String.valueOf(DEFAULT_PAGE_NUMBER));
    }


    @PostMapping("updateFrame")
    public String updateFrame(
            @RequestParam(name = "firm", required = false) String firm,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "details", required = false) String details,

            @RequestParam(name = "purchasePrice", required = false) BigDecimal purchasePrice,
            @RequestParam(name = "salePrice", required = false) BigDecimal salePrice,

            @RequestParam(name = "whichField") String whichField,
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "pageNumber") int pageNumber
    ) {


        Frame frame = this.frameService.findById(id).get();

        if (whichField.equals("firm") && (!firm.isBlank())) {
            frame.setFirm(firm);
        } else if (whichField.equals("model") && (!model.isBlank())) {
            frame.setModel(model);
        } else if (whichField.equals("details")) {
            frame.setDetails(details);
        } else if (whichField.equals("purchasePrice")) {
            frame.setPurchasePrice(purchasePrice);
        } else if (whichField.equals("salePrice")) {
            frame.setSalePrice(salePrice);
        } else {
            return "redirect:/api/doneFrameOperationPage/".concat(String.valueOf(pageNumber));
        }
        this.frameService.save(frame);

        return "redirect:/api/doneFrameOperationPage/".concat(String.valueOf(pageNumber));
    }

    @PostMapping("deleteFrame/{id:\\d+}/{pageNumber:\\d+}")
    public String deleteFrame(
            Model model,
            @PathVariable("id") Long id,
            @PathVariable("pageNumber") int pageNumber
    ) {

        this.frameService.deleteById(id);

        return "redirect:/api/doneFrameOperationPage/".concat(String.valueOf(pageNumber));
    }

}

