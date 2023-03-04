package com.rrs.spring_site.controllers;

import com.rrs.spring_site.models.Product;
import com.rrs.spring_site.repository.ProductRepository;
import com.rrs.spring_site.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

// Контролер отримує запити і надсилає відповіді
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    // Головна сторінка
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // Список товарів
    @GetMapping("/catalog")
    public String catalog(Model model) {
        Iterable<Product> product = productRepository.findAll();
        model.addAttribute("product", product);
        return "catalog";
    }


    // Сторінка додавання товару
    @GetMapping("/catalog/add")
    public String catalogAdd() {
        return "catalog-add";
    }

    // Додавання товару
    @PostMapping("/catalog/add")
    public String catalogPostAdd(@RequestParam("file1")MultipartFile file1,
                                 @RequestParam("file2")MultipartFile file2,
                                 @RequestParam("file3")MultipartFile file3, Product product) throws IOException {


        productService.addProduct(product, file1, file2, file3);
        return "redirect:/catalog";
    }




    // Детально про товар
    @GetMapping("/catalog/{id}")
    public String catalogDetails(@PathVariable(value = "id") long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("photos", product.getPhotos());
        return "catalog-details";
    }


    // Сторінка редагування товару
    @GetMapping("/catalog/{id}/edit")
    public String catalogEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        return "catalog-edit";
    }

    // Редагування товару
    @PostMapping("/catalog/{id}/edit")
    public String catalogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String description) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setTitle(title);
        product.setDescription(description);
        productRepository.save(product);
        return "redirect:/catalog";
    }

    // Видалення товару
    @PostMapping("/catalog/{id}/delete")
    public String catalogPostDelete(@PathVariable(value = "id") long id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return "redirect:/catalog";
    }

}