package com.news.app;

import com.news.model.User;
import com.news.model.Article;
import com.news.dao.UserDAO;
import com.news.dao.ArticleDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        ArticleDAO articleDAO = new ArticleDAO();

        String[] roles = {"Admin", "Editor", "Reader"};

        boolean running = true;
        while (running) {
            System.out.println("\n=== News Aggregator App ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                // 🔹 Login Flow
                System.out.print("Enter username: ");
                String username = scanner.nextLine();

                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                User user = userDAO.getUserByUsername(username);

                if (user != null && user.getPassword().equals(password)) {
                    System.out.println("✅ Login successful");

                    // 🎯 Role-based actions
                    switch (user.getRole().toLowerCase()) {
                        case "admin":
                            adminMenu(user.getUsername(), scanner, articleDAO);
                            break;
                        case "editor":
                            editorMenu(user.getUsername(), scanner, articleDAO);
                            break;
                        case "reader":
                            readerMenu(scanner, articleDAO);
                            break;
                        default:
                            System.out.println("⚠️ Unknown role.");
                    }

                } else {
                    System.out.println("❌ Invalid username or password.");
                }

            } else if (choice == 2) {
                // 🔹 SignUp Flow
                System.out.print("Choose username: ");
                String username = scanner.nextLine();

                System.out.print("Choose password: ");
                String password = scanner.nextLine();

                System.out.println("Select role: ");
                System.out.println("1. Admin");
                System.out.println("2. Editor");
                System.out.println("3. Reader");

                int roleChoice = scanner.nextInt();
                scanner.nextLine();
                String role = roles[roleChoice - 1];

                User newUser = new User(0, username, password, role);
                boolean success = userDAO.addUser(newUser);

                if (success) {
                    System.out.println("🎉 User registered successfully! You can now log in.");
                } else {
                    System.out.println("❌ Failed to register user.");
                }

            } else if (choice == 3) {
                System.out.println("👋 Exiting News Aggregator App...");
                running = false;
            } else {
                System.out.println("⚠️ Invalid choice, try again.");
            }
        }

        scanner.close();
    }

    // 🔹 Admin Menu
    private static void adminMenu(String author, Scanner scanner, ArticleDAO articleDAO) throws SQLException {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Article");
            System.out.println("2. View All Articles");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter title: ");
                String title = scanner.nextLine();

                System.out.print("Enter content: ");
                String content = scanner.nextLine();

                System.out.print("Enter category: ");
                String category = scanner.nextLine();

                Article article = new Article(0, title, content, author, category);
                articleDAO.addArticle(article);
                System.out.println("✅ Article added successfully!");
            } else if (choice == 2) {
                List<Article> articles = articleDAO.getAllArticles();
                for (Article a : articles) {
                    System.out.println(a);
                }
            } else if (choice == 3) {
                System.out.println("👋 Logging out...");
                break; // exit menu loop, go back to login/signup
            } else {
                System.out.println("⚠️ Invalid choice, try again.");
            }
        }
    }

    // 🔹 Editor Menu
    private static void editorMenu(String author, Scanner scanner, ArticleDAO articleDAO) throws SQLException {
        while (true) {
            System.out.println("\n=== Editor Menu ===");
            System.out.println("1. Add Article");
            System.out.println("2. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter title: ");
                String title = scanner.nextLine();

                System.out.print("Enter content: ");
                String content = scanner.nextLine();

                System.out.print("Enter category: ");
                String category = scanner.nextLine();

                Article article = new Article(0, title, content, author, category);
                articleDAO.addArticle(article);
                System.out.println("✅ Article added successfully!");
            } else if (choice == 2) {
                System.out.println("👋 Logging out...");
                break;
            } else {
                System.out.println("⚠️ Invalid choice, try again.");
            }
        }
    }

    // 🔹 Reader Menu
    private static void readerMenu(Scanner scanner, ArticleDAO articleDAO) throws SQLException {
        while (true) {
            System.out.println("\n=== Reader Menu ===");
            System.out.println("1. View All Articles");
            System.out.println("2. View Articles by Category");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                List<Article> articles = articleDAO.getAllArticles();
                for (Article a : articles) {
                    System.out.println(a.getId() + " | " + a.getTitle() + " | " + a.getCategory());
                }
            } else if (choice == 2) {
                System.out.print("Enter category: ");
                String category = scanner.nextLine();

                List<Article> articles = articleDAO.getArticlesByCategory(category);
                for (Article a : articles) {
                    System.out.println(a.getId() + " | " + a.getTitle() + " | " + a.getCategory());
                }
            } else if (choice == 3) {
                System.out.println("👋 Logging out...");
                break;
            } else {
                System.out.println("⚠️ Invalid choice, try again.");
            }
        }
    }
}
