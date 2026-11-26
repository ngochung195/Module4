package com.codegym.productmanagement.controller;

import com.codegym.productmanagement.model.Product;
import com.codegym.productmanagement.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String redirectToProducts() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String listProducts(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("products", productService.searchByName(keyword.trim()));
            model.addAttribute("keyword", keyword.trim());
        } else {
            model.addAttribute("products", productService.findAll());
            model.addAttribute("keyword", "");
        }
        return "product/list";
    }

    @GetMapping("/products/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/create";
    }

    @PostMapping("/products/create")
    public String createProduct(@Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/create";
        }
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Thêm sản phẩm mới thành công!");
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm với ID: " + id);
            return "redirect:/products";
        }
        model.addAttribute("product", productOptional.get());
        return "product/edit";
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            product.setId(id);
            return "product/edit";
        }
        product.setId(id);
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin sản phẩm thành công!");
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm với ID: " + id);
            return "redirect:/products";
        }
        model.addAttribute("product", productOptional.get());
        return "product/delete";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            productService.remove(id);
            redirectAttributes.addFlashAttribute("message", "Xoá sản phẩm thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm với ID: " + id);
        }
        return "redirect:/products";
    }

    @GetMapping("/products/view/{id}")
    public String viewProduct(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm với ID: " + id);
            return "redirect:/products";
        }
        model.addAttribute("product", productOptional.get());
        return "product/view";
    }
}
