package ru.xast.task.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xast.task.models.Product;
import ru.xast.task.models.Supplier;
import ru.xast.task.services.ProductService;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String index(Model model) {
        try {
            model.addAttribute("product", productService.findAll());
            return "product/index";
        } catch (Exception e) {
            log.error("Error loading product index page", e);
            return "redirect:/error/retry";
        }
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") UUID id, Model model) {
        try {
            model.addAttribute("product", productService.findOne(id));
            return "product/show";
        } catch (Exception e) {
            log.error("Error loading product with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/new")
    public String newProduct(@ModelAttribute("product") Product product) {
        return "product/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "product/new";
            }

            productService.save(product);
            return "redirect:/product";
        } catch (Exception e) {
            log.error("Error creating product", e);
            return "redirect:/error/retry";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {
        try {
            model.addAttribute("product", productService.findOne(id));
            return "product/edit";
        } catch (Exception e) {
            log.error("Error loading edit page for product with id: {}", id, e);
            return "redirect:/error/retry";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "product/edit";
            }

            productService.update(id, product);
            return "redirect:/product";
        } catch (Exception e) {
            log.error("Error updating product with id: {}", id, e);
            return "redirect:/error/retry";
        }

    }
}
