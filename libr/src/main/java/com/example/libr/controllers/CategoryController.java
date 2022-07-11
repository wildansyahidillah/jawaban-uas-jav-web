package com.example.libr.controllers;

import com.example.libr.entities.Category;
import com.example.libr.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    private Category category;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("title", "Master Category");
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories/index";
    }

    @PostMapping("/save")
    public String save(Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "categories/form";
        }
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/add")
    public String add(Category category, Model model) {
        model.addAttribute("title", "Add Category");
        return "categories/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid Category Id" + id)
        );
        model.addAttribute("title", "Edit Category");
        model.addAttribute("category", category);
        return "categories/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid Category Id" + id)
        );
        categoryRepository.delete(category);
        return "redirect:/categories";
    }
}