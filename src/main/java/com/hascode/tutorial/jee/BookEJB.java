package com.hascode.tutorial.jee;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class BookEJB {
	@PersistenceContext(unitName = "defaultPersistenceUnit")
	private EntityManager em;

	public Book saveBook(final Book book) {
		em.persist(book);
		return book;
	}

	public List<Book> findAllBooks() {
		final Query query = em
				.createQuery("SELECT b FROM Book b ORDER BY b.title ASC");
		List<Book> entries = query.getResultList();
		if (entries == null) {
			entries = new ArrayList<Book>();
		}
		return entries;
	}

	public void deleteBook(Book book) {
		book = em.merge(book);
		em.remove(book);
	}
}
