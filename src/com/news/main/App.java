package com.news.main;

import com.news.model.*;
import com.news.service.*;

import java.time.LocalDateTime;
import java.util.*;

public class App {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		NewsAggregator aggregator = new NewsAggregator();
		User user = new User("User", List.of("Tech", "Politics"));

		System.out.println(user.getName());

		System.out.println("Welcome " + user.getName() + ", to the Personalized News Aggregator App");

		while (true) {
			System.out.println("-----------------------------------");
			System.out.println("1. Add Article");
			System.out.println("2. Show Latest Articles");
			System.out.println("3. Show Personalized News for " + user.getName());
			System.out.println("4. Quit");
			System.out.print("Choose option: ");

			int choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
				case 1 -> {
					System.out.println("Enter title: ");
					String title = sc.nextLine();
	
					System.out.println("Enter category: ");
					String category = sc.nextLine();
	
					System.out.println("Enter author: ");
					String author = sc.nextLine();
	
					System.out.println("Enter content: ");
					String content = sc.nextLine();
	
					LocalDateTime date = LocalDateTime.now();
	
					aggregator.addArticle(new Article(title, content, category, author));
					System.out.println("✅ Article added!");
				}
				case 2 -> {
						System.out.println("Latest Articles:");
						aggregator.getLatestNews(5).forEach(System.out::println);
				}
				case 3 -> {
						System.out.println("Personalized News:");
						aggregator.getPersonalizedNews(user).forEach(System.out::println);
				}
				case 4 -> {
						System.out.println("👋 Exiting...");
						sc.close();
						return;
				}
				default -> System.out.println("❌ Invalid choice, try again.");
			}
		}
	}
}
