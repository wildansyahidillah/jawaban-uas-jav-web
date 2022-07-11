package com.example.libr.controllers;

import com.example.libr.entities.Book;
import com.example.libr.repositories.BookRepository;
import com.example.libr.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private Book book;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("title", "Master Book");
        model.addAttribute("books", bookRepository.findAll());
        return "books/index";
    }

    @PostMapping("/save")
    public String save(Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/form";
        }
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/add")
    public String add(Book book, Model model) {
        model.addAttribute("title", "Add Book");
        model.addAttribute("categories", categoryRepository.findAll());
        return "books/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid Book Id " + id)
        );
        model.addAttribute("title", "Edit Book");
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        return "books/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid Book Id " + id)
        );
        bookRepository.delete(book);
        return "redirect:/books";
    }
}
