package eu.kunas.homeclowd.frontend.pages;

import eu.kunas.homeclowd.frontend.template.TemplatePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Bytes;

import java.io.File;
import java.io.IOException;

public class HomePage extends TemplatePage {



    private static final long serialVersionUID = 1L;

    private FileUploadField fileUploadField;

    public HomePage(final PageParameters parameters) {
        super("HOMECLOWD - HOMEPAGE - WICKET SAMPLE");

        fileUploadField = new FileUploadField("fileUploadField");

        Form form = new Form("uploadForm") {
            @Override
            protected void onSubmit() {
                super.onSubmit();

                FileUpload fileUpload = fileUploadField.getFileUpload();

                try {
                    File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileUpload.getClientFileName());

                    fileUpload.writeTo(file);

                    System.out.println(file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        form.setMultiPart(true);
        form.setMaxSize(Bytes.kilobytes(100));
        form.add(fileUploadField);
        add(new FeedbackPanel("feedbackPanel"));
        add(form);


        RepeatingView listItems = new RepeatingView("listItems");listItems.add(new Label(listItems.newChildId(), "green"));
        listItems.add(new Label(listItems.newChildId(), "blue"));
        listItems.add(new Label(listItems.newChildId(), "red"));
        add(listItems);


    }
}
