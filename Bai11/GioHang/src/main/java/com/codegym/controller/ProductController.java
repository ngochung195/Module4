package com.codegym.controller;

import com.codegym.model.Cart;
import com.codegym.model.Product;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@SessionAttributes("cart")
public class ProductController {

    @Autowired
    private IProductService productService;

    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @GetMapping({"/", "/shop"})
    public ModelAndView showShop() {
        ModelAndView modelAndView = new ModelAndView("shop");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }

    @GetMapping({"/view/{id}", "/product/{id}"})
    public ModelAndView viewProduct(@PathVariable("id") Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            return new ModelAndView("error_404");
        }
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("product", productOptional.get());
        return modelAndView;
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long id,
                            @ModelAttribute("cart") Cart cart,
                            @RequestParam(value = "action", defaultValue = "") String action,
                            RedirectAttributes redirectAttributes) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            return "error_404";
        }
        Product product = productOptional.get();
        if (action.equals("show")) {
            cart.addProduct(product);
            return "redirect:/shopping-cart";
        }
        if (action.equals("decrease")) {
            cart.decreaseProduct(product);
            return "redirect:/shopping-cart";
        }
        cart.addProduct(product);
        redirectAttributes.addFlashAttribute("successMessage",
                "Đã thêm \"" + product.getName() + "\" vào giỏ hàng!");
        return "redirect:/shop";
    }

    @PostMapping("/update-quantity")
    public String updateQuantity(@RequestParam("id") Long id,
                                 @RequestParam("quantity") int quantity,
                                 @ModelAttribute("cart") Cart cart,
                                 RedirectAttributes redirectAttributes) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            return "error_404";
        }
        Product product = productOptional.get();
        cart.updateProductQuantity(product, quantity);
        redirectAttributes.addFlashAttribute("successMessage",
                "Đã cập nhật số lượng cho sản phẩm \"" + product.getName() + "\".");
        return "redirect:/shopping-cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable("id") Long id,
                                 @ModelAttribute("cart") Cart cart,
                                 RedirectAttributes redirectAttributes) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isEmpty()) {
            return "error_404";
        }
        Product product = productOptional.get();
        cart.removeProduct(product);
        redirectAttributes.addFlashAttribute("successMessage",
                "Đã xóa \"" + product.getName() + "\" khỏi giỏ hàng.");
        return "redirect:/shopping-cart";
    }
}

