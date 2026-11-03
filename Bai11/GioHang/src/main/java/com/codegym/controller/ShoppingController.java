package com.codegym.controller;

import com.codegym.model.Cart;
import com.codegym.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@SessionAttributes("cart")
public class ShoppingController {

    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @GetMapping("/shopping-cart")
    public ModelAndView showCart(@ModelAttribute("cart") Cart cart) {
        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("cart", cart);
        return modelAndView;
    }

    @PostMapping("/checkout")
    public ModelAndView checkout(@ModelAttribute("cart") Cart cart,
                                 @RequestParam(value = "customerName", defaultValue = "Khách hàng") String customerName,
                                 @RequestParam(value = "phone", defaultValue = "") String phone,
                                 @RequestParam(value = "address", defaultValue = "") String address,
                                 @RequestParam(value = "note", defaultValue = "") String note,
                                 RedirectAttributes redirectAttributes) {
        if (cart.getProducts().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Giỏ hàng của bạn đang trống, vui lòng chọn sản phẩm trước khi thanh toán!");
            return new ModelAndView("redirect:/shopping-cart");
        }

        Map<Product, Integer> orderedItems = new LinkedHashMap<>(cart.getProducts());
        int totalItems = cart.countItemQuantity();
        int totalQuantity = cart.countProductQuantity();
        float totalPayment = cart.countTotalPayment();

        cart.clearCart();

        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("cart", cart);
        modelAndView.addObject("checkoutSuccess", true);
        modelAndView.addObject("customerName", customerName);
        modelAndView.addObject("phone", phone);
        modelAndView.addObject("address", address);
        modelAndView.addObject("note", note);
        modelAndView.addObject("orderedItems", orderedItems);
        modelAndView.addObject("orderedItemCount", totalItems);
        modelAndView.addObject("orderedProductQuantity", totalQuantity);
        modelAndView.addObject("orderedTotalPayment", totalPayment);
        return modelAndView;
    }

    @GetMapping("/checkout")
    public ModelAndView checkoutGet(@ModelAttribute("cart") Cart cart,
                                    RedirectAttributes redirectAttributes) {
        return checkout(cart, "Khách hàng", "", "", "", redirectAttributes);
    }
}

