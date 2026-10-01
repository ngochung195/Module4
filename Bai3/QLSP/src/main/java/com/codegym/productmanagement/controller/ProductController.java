package com.codegym.productmanagement.controller;

import com.codegym.productmanagement.model.Product;
import com.codegym.productmanagement.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping({"/products", "/"})
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Product> products;
        if (search != null && !search.trim().isEmpty()) {
            products = productService.searchByName(search);
            model.addAttribute("searchKeyword", search);
        } else {
            products = productService.findAll();
        }
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/products/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) {
        productService.save(product);
        redirectAttributes.addFlashAttribute("success", "Thêm mới sản phẩm thành công!");
        return "redirect:/products";
    }

    @GetMapping("/products/{id}/edit")
    public String showEditForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.findById(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm có ID: " + id);
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "edit";
    }

    @PostMapping("/products/update")
    public String updateProduct(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) {
        productService.update(product.getId(), product);
        redirectAttributes.addFlashAttribute("success", "Cập nhật sản phẩm thành công!");
        return "redirect:/products";
    }

    @GetMapping("/products/{id}/delete")
    public String showDeleteForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.findById(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm có ID: " + id);
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "delete";
    }

    @PostMapping("/products/delete")
    public String deleteProduct(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        productService.remove(id);
        redirectAttributes.addFlashAttribute("success", "Xoá sản phẩm thành công!");
        return "redirect:/products";
    }

    @GetMapping("/products/{id}/view")
    public String viewProduct(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.findById(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm có ID: " + id);
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "view";
    }
}
