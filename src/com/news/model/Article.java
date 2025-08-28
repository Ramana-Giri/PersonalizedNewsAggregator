package com.news.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {
	private String author;
	private String title;
	private String content;
	private String category;
	private LocalDateTime timeStamp;

	{
		timeStamp = LocalDateTime.now();
	}

	public Article(String title, String content, String category, String author) {
		super();
		this.author = author;
		this.title = title;
		this.content = content;
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getCategory() {
		return category;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	@Override
	public String toString() {
		return "Title : " + title + "\n" + "Category : " + category + "\n" + "Time stamp : " + timeStamp;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		Article a1 = (Article) o;
		if(this.title.equals(a1.title) && this.author.equals(a1.author)) return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(title, author, content, category, timeStamp);
	}
	
	
}
