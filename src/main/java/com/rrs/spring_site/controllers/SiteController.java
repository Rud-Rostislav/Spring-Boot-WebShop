package com.rrs.spring_site.controllers;

import com.rrs.spring_site.models.Post;
import com.rrs.spring_site.models_controller.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class SiteController {

    public SiteController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    private final PostRepository postRepository;

    @GetMapping("/catalog")
    public String catalog(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "catalog";
    }

    @GetMapping("/catalog/add")
    public String catalogAdd(Model model) {
        return "catalog-add";
    }

    @PostMapping("/catalog/add")
    public String catalogPostAdd(@RequestParam String title, @RequestParam String full_text, Model model) {
        Post post = new Post(title, full_text);
        postRepository.save(post);
        return "redirect:/catalog";
    }

    @GetMapping("/catalog/{id}")
    public String catalogDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "catalog-details";
    }

    @GetMapping("/catalog/{id}/edit")
    public String catalogEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "catalog-edit";
    }

    @PostMapping("/catalog/{id}/edit")
    public String catalogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/catalog";
    }

    @PostMapping("/catalog/{id}/delete")
    public String catalogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/catalog";
    }

}
