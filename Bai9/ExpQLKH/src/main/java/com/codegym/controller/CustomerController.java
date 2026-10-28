package com.codegym.controller;

import com.codegym.exception.DuplicateEmailException;
import com.codegym.model.Customer;
import com.codegym.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping({ "", "/" })
    public String list(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "create";
    }

    @PostMapping("/create")
    public String saveCustomer(Customer customer, RedirectAttributes redirectAttributes) throws DuplicateEmailException {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message", "Thêm mới khách hàng thành công!");
        return "redirect:/customers";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy khách hàng với ID: " + id);
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "update";
    }

    @PostMapping("/update")
    public String updateCustomer(Customer customer, RedirectAttributes redirectAttributes) throws DuplicateEmailException {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin khách hàng thành công!");
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy khách hàng với ID: " + id);
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "delete";
    }

    @PostMapping("/delete")
    public String deleteCustomer(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        customerService.remove(id);
        redirectAttributes.addFlashAttribute("message", "Xóa khách hàng thành công!");
        return "redirect:/customers";
    }

    @GetMapping("/view/{id}")
    public String viewCustomer(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy khách hàng với ID: " + id);
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "view";
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ModelAndView showInputsNotAcceptable(DuplicateEmailException e) {
        ModelAndView modelAndView = new ModelAndView("inputs-not-acceptable");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler({org.springframework.dao.DataIntegrityViolationException.class, org.hibernate.exception.ConstraintViolationException.class})
    public ModelAndView showDataIntegrityViolation(Exception e) {
        ModelAndView modelAndView = new ModelAndView("inputs-not-acceptable");
        modelAndView.addObject("errorMessage", "Email đã tồn tại trong hệ thống!");
        return modelAndView;
    }
}
