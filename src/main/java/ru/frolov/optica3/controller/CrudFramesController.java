package ru.frolov.optica3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.Defaults;
import ru.frolov.optica3.service.FrameService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/crud")
public class CrudFramesController {
    /*
    CrudFramesController выполняет crud операции и
    перенаправляет в ReceiveFramesController. (Кроме begin(), который сразу отдает оправы представлению).

    - create
    - begin (read)
    - update
    - delete
 */

    private final FrameService frameService;


    // 1.
    @PostMapping("createFrame")
    @Transactional
    public String createFrame(@Valid FramePayload framePayload,
                              BindingResult bindingResult,
                              RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            ra.addFlashAttribute("errors", errors);
        } else {
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
        return "redirect:/api/receive/createdFrame";
    }

    // 2.
    @GetMapping("read")
    public String begin(Model model) {

        fillModelAttrs(model, Defaults.PAGE_NUMBER, Defaults.PAGE_SIZE, Defaults.SORT_FIELD);

        return "storage";
    }


    // 3.
    @PostMapping("updateFrame/{pageNumber:\\d+}")
    @Transactional
    public String updateFrame(
            @RequestParam(name = "firm", required = false) String firm,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "details", required = false) String details,

            @RequestParam(name = "purchasePrice", required = false) BigDecimal purchasePrice,
            @RequestParam(name = "salePrice", required = false) BigDecimal salePrice,

            @RequestParam(name = "whichField") String whichField,
            @RequestParam(name = "id") Long id,
            @PathVariable(name = "pageNumber", required = false) int pageNumber
    ) {

//////////////
        System.out.println("--- in crud updateFrame()...; pageNumber=" + pageNumber);
//////////////
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
        }
        this.frameService.save(frame);

        return "redirect:/api/receive/updatedFrame/".concat(String.valueOf(pageNumber));
    }


    // 4.
    @PostMapping("deleteFrame/{id:\\d+}/{pageNumber:\\d+}")
    @Transactional
    public String deleteFrame(
            @PathVariable("id") Long id,
            @PathVariable("pageNumber") int pageNumber
    ) {

        this.frameService.deleteById(id);

        return "redirect:/api/receive/deletedFrame/".concat(String.valueOf(pageNumber));
    }

    // help
    private void fillModelAttrs(Model model, int pageNumber, int pageSize, String sortField) {
        Page<Frame> page = this.frameService.getSortedPage(pageNumber, pageSize, sortField);

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("frames", page.getContent());
        model.addAttribute("totalFrames", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
    }
}
