package com.codegym.customermanagement.controller;

import com.codegym.customermanagement.model.Customer;
import com.codegym.customermanagement.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customers", customerList);
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("customer") Customer customer, RedirectAttributes redirect) {
        customerService.save(customer);
        redirect.addFlashAttribute("success", "Lưu khách hàng thành công!");
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("customer") Customer customer, RedirectAttributes redirect) {
        customerService.save(customer);
        redirect.addFlashAttribute("success", "Cập nhật khách hàng thành công!");
        return "redirect:/customers";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirect) {
        customerService.remove(id);
        redirect.addFlashAttribute("success", "Xóa khách hàng thành công!");
        return "redirect:/customers";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "view";
    }
}
