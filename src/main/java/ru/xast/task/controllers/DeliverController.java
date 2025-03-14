package ru.xast.task.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xast.task.models.Deliver;
import ru.xast.task.models.Product;
import ru.xast.task.models.Supplier;
import ru.xast.task.services.DeliverService;
import ru.xast.task.services.ProductService;
import ru.xast.task.services.SupplierService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/deliver")
public class DeliverController {

    private final DeliverService deliverService;
    private final SupplierService supplierService;
    private final ProductService productService;

    @Autowired
    public DeliverController(DeliverService deliverService, SupplierService supplierService, ProductService productService) {
        this.deliverService = deliverService;
        this.supplierService = supplierService;
        this.productService = productService;
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
        List<Supplier> suppliers = supplierService.findAll();
        model.addAttribute("suppliers", suppliers);

        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        model.addAttribute("deliver", new Deliver());

        return "deliver/form";
    }

    @PostMapping("/create")
    public String createDeliver(@ModelAttribute("deliver") Deliver deliver,
                                @RequestParam("selectedProducts") List<UUID> selectedProductIds){
       List<Product> selectedProducts = productService.findAllById(selectedProductIds);
       deliver.setProducts(selectedProducts);

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
