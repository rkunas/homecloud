package eu.kunas.homeclowd.service;

import eu.kunas.homeclowd.dto.MediaDto;
import eu.kunas.homeclowd.model.HCConfigEntity;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.Attribute;
import javax.management.MXBean;
import javax.management.ObjectName;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ramazan on 24.04.15.
 */
@Service(value = "filesFolderService")
public class FilesFolderServiceImpl {

    @Inject
    @Named("configService")
    private ConfigServiceImpl configService;

    public HCConfigEntity getRootFolderEntity(){
        return configService.getAllHashMap().get("FOLDER_URL");
    }

    public List<MediaDto> getFolderItems(File f){

        List<MediaDto> list = new ArrayList<MediaDto>();
        try {
            File temp = null;

            if (f == null) {
                temp = new File(configService.getAllHashMap().get("FOLDER_URL").getValue());
            } else {
                temp = f;
            }


            for (final File fileEntry : temp.listFiles()) {
                MediaDto m = new MediaDto();
                m.setAbsolutePath(fileEntry.getAbsolutePath());
                m.setDescription(fileEntry.getName());

                m.setSize(fileEntry.length());

                if (fileEntry.isDirectory()) {
                    m.setType("Folder");
                } else {
                    m.setType("File");

                }

                list.add(m);
            }
        }catch (Exception exc){
            // do nothing
        }

        return list;
    }
}
