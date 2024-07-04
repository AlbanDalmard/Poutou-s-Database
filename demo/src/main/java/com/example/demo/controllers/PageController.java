package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/addCustomer")
    public String addCustomerForm() {
        return "addCustomer";
    }

    @PostMapping("/addCustomer")
    public String addCustomer(@RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam String country, @RequestParam String city, Model model) {
        model.addAttribute("message", "Customer added successfully!");
        return "success";
    }

    @GetMapping("/searchCustomer")
    public String searchCustomerForm() {
        return "searchCustomer";
    }

    @PostMapping("/searchCustomer")
    public String searchCustomer(@RequestParam String keyword, Model model) {
        model.addAttribute("message", "Customer search completed successfully!");
        return "success";
    }
}