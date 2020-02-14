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

/**
 * 
 * 投稿内容、コメント内容を操作するコントローラー.
 * @author fuka
 *
 */
@Controller
@RequestMapping("")
	public class ArticleController {
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	
	
	/**
	 * 入力画面を表示させる操作.
	 * @param model
	 * @return　投稿一覧の表示
	 */
	@RequestMapping("/ex4")
	public String index(Model model) {
	List<Article> articleList = articleRepository.findAll();
	for(Article article:articleList) {
		Integer articleId = article.getId();
		List<Comment> commentList = commentRepository.findByArticleId(articleId);
		article.setCommentList(commentList);
	}
	model.addAttribute("articleList" , articleList);
		return "ex4";
	}
	
	
	/**
	 * articleRepositoryの呼び出し.
	 * @param form
	 * @param model
	 * @return　投稿一覧の表示
	 */
	@RequestMapping("/insertArticle")
	public String insertArticle(ArticleForm form, Model model) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);
		return "redirect:/ex4";
//		return index(model);
	}
	
	/**
	 * commentRepositoryの呼び出し.
	 * @param form　フォーム（リクエストパラメータ）
	 * @param model　リクエストスコープ
	 * @return　投稿一覧の表示
	 */
	@RequestMapping("/insertComment")
	public String insertComment(CommentForm form, Model model) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		comment.setArticleId(Integer.parseInt(form.getArticleId()));
		commentRepository.insert(comment);
		return "redirect:/ex4";
//		return index(model);
		
	}
	

	/**
	 * 投稿内容(+コメント内容)削除.
	 * @param form
	 * @return 投稿一覧の表示
	 */
	@RequestMapping("/deleteArticle")
	public String deleteArticle(String articleId) {
		int articleIdInt = Integer.parseInt(articleId);
		commentRepository.deleteByArticleId(articleIdInt);
		articleRepository.deleteById(articleIdInt);
		
		return "redirect:/ex4";
	}
	
	
	
	
}
