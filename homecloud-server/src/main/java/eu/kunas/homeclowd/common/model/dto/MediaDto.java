package eu.kunas.homeclowd.common.model.dto;

import java.io.Serializable;

/**
 * Created by ramazan on 17.04.15.
 */
public class MediaDto implements Serializable {

    private String type;

    private Long size;
    private String absolutePath;
    private String description;
    private String modified;

    public MediaDto() {

    }

     public MediaDto(String description) {
        this.description = description;
    }
    
    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

   

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
