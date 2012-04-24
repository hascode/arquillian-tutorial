package com.hascode.tutorial.jee;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thoughtworks.selenium.DefaultSelenium;

@RunWith(Arquillian.class)
public class CreateBookSeleniumTest {
	private static final String WEBAPP_SRC = "src/main/webapp";

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "books.war")
				.addClasses(Book.class, BookEJB.class, BookControllerBean.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsWebResource(new File(WEBAPP_SRC, "books.xhtml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "faces-config.xml");
	}

	@Drone
	DefaultSelenium browser;

	@ArquillianResource
	URL deploymentURL;

	@Test
	public void should_login_successfully() {
		// this will be http://localhost:8181/books/books.xhtml
		browser.open(deploymentURL + "books.xhtml");
		browser.waitForPageToLoad("20000");
		browser.type("id=createBookForm:title", "My book title");
		browser.type("id=createBookForm:author", "The author");
		browser.click("id=createBookForm:saveBook");
		browser.waitForPageToLoad("20000");

		assertTrue(
				"Book title present",
				browser.isElementPresent("xpath=//li[contains(text(), 'My book title by The author')]"));
	}
}
