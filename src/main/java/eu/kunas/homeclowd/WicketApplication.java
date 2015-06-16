package eu.kunas.homeclowd;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ThemeProvider;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;
import eu.kunas.homeclowd.utils.SpringContext;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.core.request.mapper.CryptoMapper;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.lang.Bytes;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 */
public class WicketApplication extends AuthenticatedWebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return MediaPage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return BasicAuthenticationSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return SignInPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();

		new BeanValidationConfiguration().configure(this);
		getApplicationSettings().setDefaultMaximumUploadSize(Bytes.kilobytes(100));

		configureBootstrap();

		setHeaderResponseDecorator(new JavaScriptToBucketResponseDecorator("footer-container"));

		// setRootRequestMapper(new CryptoMapper(getRootRequestMapper(), this));

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringContext.class);

		//Scan package for annotated beans
		ctx.scan("eu.kunas.homeclowd");
		getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));

	}

	public void configureBootstrap(){
		final IBootstrapSettings settings = new BootstrapSettings();
		final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Paper);

		settings.setJsResourceFilterName("footer-container")
				.setThemeProvider(themeProvider);

		Bootstrap.install(this, settings);
	}

	static class JavaScriptToBucketResponseDecorator implements IHeaderResponseDecorator
	{
		private String bucketName;
		public JavaScriptToBucketResponseDecorator(String bucketName) {
			this.bucketName = bucketName;
		}
		@Override
		public IHeaderResponse decorate(IHeaderResponse response) {
			return new JavaScriptFilteredIntoFooterHeaderResponse(response, bucketName);
		}
	}
}
