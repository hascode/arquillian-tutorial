package com.hascode.tutorial.jee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BookServiceIT {
	@Inject
	private BookEJB bookEJB;

	@Deployment
	public static JavaArchive createArchiveAndDeploy() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(BookEJB.class, Book.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void testFetchBooks() {
		assertTrue(bookEJB.findAllBooks().isEmpty());
		Book b1 = new Book();
		b1.setAuthor("tim");
		b1.setTitle("A fascinating book");
		Book b2 = new Book();
		b2.setAuthor("tom");
		b2.setTitle("I R Coder");
		Book b3 = new Book();
		b3.setAuthor("maria");
		b3.setTitle("Some book");
		Book b4 = new Book();
		b4.setAuthor("tim");
		b4.setTitle("Another fascinating book");
		bookEJB.saveBook(b1);
		bookEJB.saveBook(b2);
		bookEJB.saveBook(b3);
		bookEJB.saveBook(b4);
		assertEquals(4, bookEJB.findAllBooks().size());
		bookEJB.deleteBook(b4);
		assertEquals(3, bookEJB.findAllBooks().size());
	}
}
