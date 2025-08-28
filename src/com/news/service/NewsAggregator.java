package com.news.service;

import com.news.model.User;
import com.news.model.Article;

import java.util.*;


public class NewsAggregator {

	private List<Article> articles = new ArrayList<>();
	
	public void addArticle(Article article) {
		articles.add(article);
	}
	
	public List<Article> getLatestNews(int n){
		return articles.stream()
				.sorted((a,b) -> b.getTimeStamp()
				.compareTo(a.getTimeStamp()))
				.limit(n)
				.toList();
	}
	
	public List<Article> getPersonalizedNews(User user){
		return articles.stream()
				.filter(a-> user.getInterests().contains(a.getCategory()))
				.sorted((a,b) -> b.getTimeStamp().compareTo(a.getTimeStamp()))
				.toList();
	}
}
