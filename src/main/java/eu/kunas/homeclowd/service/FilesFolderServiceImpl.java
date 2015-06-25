package eu.kunas.homeclowd.service;

import eu.kunas.homeclowd.dto.MediaDto;
import eu.kunas.homeclowd.model.HCConfigEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramazan on 24.04.15.
 */
@Service(value = "filesFolderService")
public class FilesFolderServiceImpl {

    @Inject
    @Named("configService")
    private ConfigServiceImpl configService;

    public HCConfigEntity getRootFolderEntity() {
        return configService.getAllHashMap().get("FOLDER_URL");
    }

    public byte[] readFile(String s) {

        try {
            File file = new File(s);

            byte[] fileBytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);

            fileInputStream.read(fileBytes);

            fileInputStream.close();

            return fileBytes;
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return null;
    }

    public List<MediaDto> getFolderItems(File toFolder) {

        List<MediaDto> list = new ArrayList<MediaDto>();
        try {
            File temp = null;

            if (toFolder == null) {
                temp = new File(configService.getAllHashMap().get("FOLDER_URL").getValue());
            } else {
                temp = toFolder;
            }

            if (toFolder != null) {
                Boolean isRoot = getRootFolderEntity().getValue().equals(toFolder.getAbsolutePath());

                if (!isRoot) {
                    MediaDto parentFolder = new MediaDto();
                    parentFolder.setType("Folder");
                    parentFolder.setDescription("/..");

                    parentFolder.setAbsolutePath(toFolder.getParentFile().getAbsolutePath());

                    parentFolder.setSize(0L);
                    list.add(parentFolder);
                }
            }


            for (final File fileEntry : temp.listFiles()) {
                MediaDto m = new MediaDto();
                m.setAbsolutePath(fileEntry.getAbsolutePath());
                m.setDescription(fileEntry.getName());


                if (fileEntry.isDirectory()) {
                    m.setType("Folder");
                    m.setSize(0L);
                } else {
                    m.setType("File");
                    m.setSize(fileEntry.length());

                }

                list.add(m);
            }
        } catch (Exception exc) {
            // do nothing
        }

        return list;
    }
}
