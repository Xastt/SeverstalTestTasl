package ru.xast.task.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xast.task.models.Deliver;
import ru.xast.task.services.DeliverService;

import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/deliver")
public class DeliverController {

    private final DeliverService deliverService;

    @Autowired
    public DeliverController(DeliverService deliverService) {
        this.deliverService = deliverService;
    }

    @GetMapping()
    public String index(Model model) {
        try {
            model.addAttribute("delivers", deliverService.findAll());
            return "deliver/index";
        }catch (Exception e) {
            log.error("Error loading deliver index page", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/form")
    public String openDeliveryForm(Model model){
        model.addAttribute("deliver", new Deliver());
        model.addAttribute("suppliers", deliverService.getAllSuppliers());
        return "deliver/form";
    }

    @PostMapping("/create")
    public String createDeliver(@ModelAttribute Deliver deliver){
        deliverService.save(deliver);
        return "redirect:/deliver";
    }

    @GetMapping("/report")
    public String getReport(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                            Model model) {
        if (startDate == null || endDate == null) {
            startDate = LocalDate.now().minusDays(7);
            endDate = LocalDate.now();
        }
        model.addAttribute("deliveries", deliverService.getDeliveriesByDateRange(startDate, endDate));
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "deliver/report";
    }
}
