package eu.kunas.homeclowd.utils;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;

/**
 * Created by ramazan on 27.04.15.
 */
public class ExactErrorLevelFilter implements IFeedbackMessageFilter {
    private int errorLevel;

    public ExactErrorLevelFilter(int errorLevel) {
        this.errorLevel = errorLevel;
    }

    public boolean accept(FeedbackMessage message) {
        return message.getLevel() == errorLevel;
    }
}
