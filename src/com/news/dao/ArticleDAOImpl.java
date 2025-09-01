package com.news.dao;

import com.news.model.Article;
import com.news.service.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleDAOImpl implements ArticleDAO {

    @Override
    public void addArticle(Article article) {
        String sql = "INSERT INTO articles (title, content, author, category) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, toTitleCase(article.getTitle()));
            stmt.setString(2, article.getContent());
            stmt.setString(3, article.getAuthor());
            stmt.setString(4, toTitleCase(article.getCategory()));
            stmt.executeUpdate();
            System.out.println("✅ Article added successfully!");
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "\n" + e.getSQLState());
        }
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM articles";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("author"),
                        rs.getString("category") // 👈 category
                );
                articles.add(article);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "\n" + e.getSQLState());
        }
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategory(String category) {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE category = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Article article = new Article(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getString("author"),
                            rs.getString("category")
                    );
                    articles.add(article);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "\n" + e.getSQLState());
        }
        return articles;
    }


    @Override
    public void deleteArticle(int id) {
        String sql = "DELETE FROM articles WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Article deleted successfully!");
            } else {
                System.out.println("No article found with that ID.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "\n" + e.getSQLState());
        }
    }

    private String toTitleCase(String title){
        return Arrays.stream(title.split("\\s+"))
                .map(i-> Character.toUpperCase(i.charAt(0) )+ i.substring(1))
                        .collect(Collectors.joining(" "));
    }
}
