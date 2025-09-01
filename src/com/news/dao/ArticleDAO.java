package com.news.dao;

import com.news.model.Article;
import java.util.List;

public interface ArticleDAO {
    void addArticle(Article article);
    List<Article> getAllArticles();
    List<Article> getArticlesByCategory(String category);
    void deleteArticle(int id);
}
