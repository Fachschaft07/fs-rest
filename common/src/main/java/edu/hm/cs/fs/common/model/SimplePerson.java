package edu.hm.cs.fs.common.model;

/**
 * @author Fabio
 */
public class SimplePerson {
    private String id;
    private String lastName;
    private String firstName;
    private String title;

    public SimplePerson() {
    }

    public SimplePerson(final Person teacher) {
        id = teacher.getId();
        lastName = teacher.getLastName();
        firstName = teacher.getFirstName();
        title = teacher.getTitle();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
