package com.hascode.tutorial.jee;

import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("bookControllerBean")
@SessionScoped
public class BookControllerBean {
	@Inject
	private BookEJB bookEJB;

	private Book book = new Book();

	public String save() {
		bookEJB.saveBook(book);
		return "/books.xhtml";
	}

	public List<Book> getBooks() {
		return bookEJB.findAllBooks(); // we're lazy here ;)
	}

	public Book getBook() {
		return book;
	}

	public void setBook(final Book book) {
		this.book = book;
	}

}
