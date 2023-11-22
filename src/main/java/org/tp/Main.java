package org.tp;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import org.tp.service.*;
import org.tp.data.*;

public class Main {
    public static void main(String[] args) {
        // Initialisation de l'EntityManagerFactory (utilisez H2 pour les tests et Oracle pour le déploiement)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");

        // Création d'une catégorie
        CategoryService categoryService = new CategoryServiceImpl(emf);
        Category fictionCategory = new Category();
        fictionCategory.setName("Fiction");
        categoryService.createCategory(fictionCategory);

        AuthorService authorService = new AuthorServiceImpl(emf);
        Author author1 = new Author();
        author1.setName("Author 1");
        authorService.createAuthor(author1);

        Author author2 = new Author();
        author2.setName("Author 2");
        authorService.createAuthor(author2);

        BookService bookService = new BookServiceImpl(emf);
        // Ajouter les auteurs Author1 et Author2 comme auteurs de ce livre
        PaperBook Pbook1 = new PaperBook();
        Set<Author> L_Authors = new HashSet<Author>();
        L_Authors.add(author1);
        L_Authors.add(author2);
        Pbook1.setAuthors(L_Authors);
        Pbook1.setCategory(fictionCategory);

        //  Faire persister ce livre à l'aide du service bookService (c'est une création)
        bookService.createBook(Pbook1);

        //  refaire la même chose avec un Ebook de titre EBook1 et de format PDF.
        Ebook Ebook1 = new Ebook();
        L_Authors = new HashSet<Author>();
        L_Authors.add(author1);
        L_Authors.add(author2);
        Ebook1.setAuthors(L_Authors);
        Ebook1.setCategory(fictionCategory);
        bookService.createBook(Ebook1);

        //  Recherche d'un livre par son Id (par exemple avec l'ID du paperbook précédent)
        // ET on afficha sa catégorie

        Book foundBook = bookService.findBookById(Pbook1.getId());// On utilise le service bookService;
        Category foundCategory = foundBook.getCategory();// On extrait la catégorie à l'aide d'un getter;
        System.out.println("Category of PaperBook 1: " + foundCategory.getName());

        // Recherche d'auteurs d'un livre
        Set<Author> foundAuthors = foundBook.getAuthors();
        System.out.println("Authors of PaperBook 1:");
        for (Author author : foundAuthors) {
            System.out.println("Author Name: " + author.getName());
        }

        // ToDo: Recherche de livres par catégorie (Fiction par exemple), on affiche juste les titres
        // On utilise la méthode de bookService pour une recherche
        // On parcours la liste résultat pour afficher les titres de livres
        List<Book> foundbooks = bookService.findBooksByCategoryName("Fiction");
        for (Book book : foundbooks) {
            System.out.println("Book Title: " + book.getTitle());
        }
        // ToDo: Recherche de livres par auteur (par exemple ar l'Author 1 créé avant), on affiche juste les titres
        // idem recherche par catégorie dans le principe
        foundbooks = bookService.findBooksByAuthorId(author1.getId());
        for (Book book : foundbooks) {
            System.out.println("Book Title: " + book.getTitle());
        }
        // Fermeture de l'EntityManagerFactory
        emf.close();
    }
}
