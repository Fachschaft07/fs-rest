package edu.hm.cs.fs.common.model.simple;

import edu.hm.cs.fs.common.model.Person;

/**
 * @author Fabio
 */
public class SimplePerson {
    private String id;
    private String name;

    public SimplePerson() {
    }

    public SimplePerson(final Person teacher) {
        setId(teacher.getId());
        StringBuilder nameBuilder = new StringBuilder();
        if(teacher.getTitle() != null) {
            nameBuilder.append(teacher.getTitle()).append(" ");
        }
        nameBuilder.append(teacher.getLastName()).append(" ").append(teacher.getFirstName());
        setName(nameBuilder.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
