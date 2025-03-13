package ru.xast.task.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xast.task.models.Supplier;
import ru.xast.task.services.SupplierService;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping()
    public String index(Model model) {
        try {
            model.addAttribute("supplier", supplierService.findAll());
            return "supplier/index";
        } catch (Exception e) {
            log.error("Error loading supplier index page", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {
        try {
            model.addAttribute("supplier", supplierService.findOne(id));
            return "supplier/show";
        } catch (Exception e) {
            log.error("Error loading supplier with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/new")
    public String newSupplier(@ModelAttribute("supplier") Supplier supplier) {
        return "supplier/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("supplier") @Valid Supplier supplier, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "supplier/new";
            }

            supplierService.save(supplier);
            return "redirect:/supplier";
        } catch (Exception e) {
            log.error("Error creating supplier", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {
        try {
            model.addAttribute("supplier", supplierService.findOne(id));
            return "supplier/edit";
        } catch (Exception e) {
            log.error("Error loading edit page for supplier with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute("supplier") @Valid Supplier supplier, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "supplier/edit";
            }

            supplierService.update(id, supplier);
            return "redirect:/supplier";
        } catch (Exception e) {
            log.error("Error updating supplier with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }
}
