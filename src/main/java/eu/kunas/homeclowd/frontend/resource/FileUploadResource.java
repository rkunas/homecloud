package eu.kunas.homeclowd.frontend.resource;

import eu.kunas.homeclowd.backend.service.ConfigServiceImpl;
import eu.kunas.homeclowd.backend.service.FilesFolderServiceImpl;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.upload.FileItem;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ramazan on 11.07.15.
 */
public class FileUploadResource extends AbstractResource {

    @SpringBean
    private FilesFolderServiceImpl filesFolderService;

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {

        String uploadfolderParam = attributes.getParameters().get("uploadfolder").toString();

        String folder = filesFolderService.getRootFolderEntity().getValue() + uploadfolderParam;

        if(!folder.endsWith("/")){
            folder = folder + "/";
        }

        try {
            ServletWebRequest webRequest = (ServletWebRequest) attributes.getRequest();
            MultipartServletWebRequest multiPartRequest = webRequest.newMultipartWebRequest(getMaxSize(), "ignored");
            multiPartRequest.parseFileParts();
            Map<String, List<FileItem>> files = multiPartRequest.getFiles();

            Set<Map.Entry<String, List<FileItem>>> list = files.entrySet();

            for (Map.Entry<String, List<FileItem>> list2 : list) {
                for (FileItem fileItem : list2.getValue()) {
                    FileUtils.writeByteArrayToFile(new File(folder + fileItem.getName()),fileItem.get());
                }
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        ResourceResponse resourceResponse = new ResourceResponse();

        resourceResponse.setWriteCallback(new WriteCallback() {
            @Override
            public void writeData(Attributes attributes) throws IOException {

            }
        });

        return resourceResponse;


    }

    private Bytes getMaxSize() {
        return Bytes.gigabytes(1L);
    }
}
