package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domein.Article;

/**
 * 
 * articlesを操作するリポジトリ.
 * @author fuka
 *
 */
@Repository
public class ArticleRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * Articleオブジェクトを生成するローマッパー.
	 */
	private static RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};
	
	
	/**
	 * 投稿を全件検索.
	 * @return 記事一覧
	 */
	public List<Article> findAll(){
		String sql ="SELECT id,name,content FROM articles ORDER BY id DESC ";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		return articleList;
	}
	
	/**
	 * 投稿を投稿.
	 * @param article　記事
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String insertSql = "INSERT INTO articles(name,content) VALUES (:name,:content)";
		template.update(insertSql, param);
	}
	
	
	/**
	 * 投稿を削除.
	 * @param id ID
	 */
	public void deleteById(Integer id) {
		String deleteSql = "DELETE FROM articles WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(deleteSql, param);
	}
	
}
