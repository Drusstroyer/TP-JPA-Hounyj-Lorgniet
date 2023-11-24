package org.tp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;

import org.tp.data.Author;
import org.tp.data.Book;
import org.tp.data.Category;

public class BookDAO implements CRUDRepository<Book> {
    private final EntityManagerFactory emf;

    public BookDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(Book book) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Book findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Book book = em.find(Book.class, id);

        return book;
        
    }

    
    @Override
    public List<Book> findAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ",Book.class);
        List<Book> books = query.getResultList();
        em.close();
        return books;
    }

    
    @Override
    public void update(Book book) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
        em.close();
    }

    
    @Override
    public void delete(Book book) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(book) ? book : em.merge(book));
        em.getTransaction().commit();
        em.close();
    }

    
    public List<Book> findBooksByAuthorId(Long authorId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId",Book.class);
        query.setParameter("authorId",authorId);
        List<Book> books = query.getResultList();
        em.close();
        return books;
    }

    
    public List<Book> findBooksByCategoryName(String categoryName) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b JOIN b.category c WHERE c.name = :categoryName",Book.class);
        query.setParameter("categoryName",categoryName);
        List<Book> books = query.getResultList();
        em.close();
        return books;
    }
}
