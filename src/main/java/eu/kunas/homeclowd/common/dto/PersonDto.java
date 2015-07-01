package eu.kunas.homeclowd.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ramazan on 13.04.15.
 */
public class PersonDto implements Serializable {
    private String firstname;
    private String lastname;

    private List<PersonDto> children;
    private PersonDto partner;

    public List<PersonDto> getChildren() {
        return children;
    }

    public void setChildren(List<PersonDto> children) {
        this.children = children;
    }

    public PersonDto getPartner() {
        return partner;
    }

    public void setPartner(PersonDto partner) {
        this.partner = partner;
    }

    public PersonDto() {

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
