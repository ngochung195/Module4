package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class CustomerViewController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping({"/", "/customers"})
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/list";
    }

    @GetMapping("/customers/create")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @PostMapping("/customers/create")
    public String saveCustomer(@ModelAttribute("customer") Customer customer,
                               RedirectAttributes redirectAttributes) {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("successMessageKey", "customer.msg.created");
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customer/edit";
        }
        return "redirect:/customers";
    }

    @PostMapping("/customers/edit")
    public String updateCustomer(@ModelAttribute("customer") Customer customer,
                                 RedirectAttributes redirectAttributes) {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("successMessageKey", "customer.msg.updated");
        return "redirect:/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customer/delete";
        }
        return "redirect:/customers";
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer,
                                 RedirectAttributes redirectAttributes) {
        customerService.remove(customer.getId());
        redirectAttributes.addFlashAttribute("successMessageKey", "customer.msg.deleted");
        return "redirect:/customers";
    }
}

