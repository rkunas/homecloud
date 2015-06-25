package eu.kunas.homeclowd;

import eu.kunas.homeclowd.service.ConfigServiceImpl;
import eu.kunas.homeclowd.utils.SpringContext;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.core.request.mapper.CryptoMapper;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.lang.Bytes;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 */
public class WicketApplication extends AuthenticatedWebApplication {


    @SpringBean
    private ConfigServiceImpl configService;

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return MediaPage.class;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return BasicAuthenticationSession.class;
    }

    private SpringComponentInjector inj =null;

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

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringContext.class);

        ctx.scan("eu.kunas.homeclowd");
        inj = new SpringComponentInjector(this, ctx);
        getComponentInstantiationListeners().add(inj);


        mountPage("/login", SignInPage.class);
        mountPage("/settings", Settings.class);
        mountPage("/media", MediaPage.class);

        mountPage("/player", PlayerPage.class);

        ResourceReference resourceReference = new ResourceReference("videoProducer"){
            VideoProducerResource videoResource = new VideoProducerResource();



            @Override
            public IResource getResource() {
                inj.inject(videoResource);
                return videoResource;
            }};
        mountResource("/videoroot?videofile=${videofile}", resourceReference);
        

        getApplicationSettings().setAccessDeniedPage(AccessDenied.class);

        new BeanValidationConfiguration().configure(this);
        getApplicationSettings().setDefaultMaximumUploadSize(Bytes.kilobytes(100));

        configureBootstrap();

        setHeaderResponseDecorator(new JavaScriptToBucketResponseDecorator("footer-container"));

        IRequestCycleListener videoRequest = new VideoRequestCylceListener();

        getRequestCycleListeners().add(videoRequest);

        //  setRootRequestMapper(new CryptoMapper(getRootRequestMapper(), this));


    }

    private void configureBootstrap() {
    }


    static class JavaScriptToBucketResponseDecorator implements IHeaderResponseDecorator {
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
