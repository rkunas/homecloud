package eu.kunas.homeclowd;

import org.apache.wicket.IRequestCycleProvider;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleContext;

/**
 * Created by Kunas on 25.06.2015.
 */
public class VideoRequestCylceListener implements IRequestCycleListener {

    @Override
    public void onBeginRequest(RequestCycle requestCycle) {

    }

    @Override
    public void onEndRequest(RequestCycle requestCycle) {

    }

    @Override
    public void onDetach(RequestCycle requestCycle) {

    }

    @Override
    public void onRequestHandlerResolved(RequestCycle requestCycle, IRequestHandler iRequestHandler) {

    }

    @Override
    public void onRequestHandlerScheduled(RequestCycle requestCycle, IRequestHandler iRequestHandler) {

    }

    @Override
    public IRequestHandler onException(RequestCycle requestCycle, Exception e) {
        return null;
    }

    @Override
    public void onExceptionRequestHandlerResolved(RequestCycle requestCycle, IRequestHandler iRequestHandler, Exception e) {

    }

    @Override
    public void onRequestHandlerExecuted(RequestCycle requestCycle, IRequestHandler iRequestHandler) {

    }

    @Override
    public void onUrlMapped(RequestCycle requestCycle, IRequestHandler iRequestHandler, Url url) {

    }
}