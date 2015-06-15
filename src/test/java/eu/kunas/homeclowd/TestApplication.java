package eu.kunas.homeclowd;

import eu.kunas.homeclowd.HomePage;
import eu.kunas.homeclowd.WicketApplication;
import eu.kunas.homeclowd.dto.MediaDto;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class TestApplication
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(MediaPage.class);

		FormTester formTester = tester.newFormTester("loginForm");

		formTester.setValue("username","ADMIN");
		formTester.setValue("password","ADMIN");

		formTester.submit();

		//assert rendered page class
		tester.assertRenderedPage(MediaPage.class);
	}
}
