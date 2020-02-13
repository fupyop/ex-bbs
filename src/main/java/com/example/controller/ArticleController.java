package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domein.Article;
import com.example.domein.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("")
	public class ArticleController {
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	@ModelAttribute
	public ArticleForm setUpForm() {
		return new ArticleForm();
	}
	@ModelAttribute
	public CommentForm setUpForm2() {
		return new CommentForm();
	}
	
	
	
	@RequestMapping("/ex4")
	public String index(Model model) {
	List<Article> articleList = articleRepository.findAll();
	List<Comment> 
	model.addAttribute("articleList" , articleList);
		return "ex4";
	}
	
	@RequestMapping("/insertArticle")
	public String insertArticle(ArticleForm form, Model model) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);
		return "redirect:/ex4";
//		return index(model);
	}
	
	@RequestMapping("/insertComment")
	public String insertComment(CommentForm form, Model model) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		commentRepository.insert(comment);
		return "ex4";
	}
	
	
	
	
	
}
