package edu.hm.cs.fs.common.model.simple;

import edu.hm.cs.fs.common.model.BlackboardEntry;
import edu.hm.cs.fs.common.model.Group;

import java.util.Date;
import java.util.List;

/**
 * Created by Fabio on 11.10.2016.
 */
public class SimpleBlackboardEntry {
    private String id;
    private SimplePerson author;
    private String subject;
    private List<Group> groups;
    private Date publish;

    public SimpleBlackboardEntry() {

    }

    public SimpleBlackboardEntry(final BlackboardEntry entry) {
        id = entry.getId();
        author = entry.getAuthor();
        subject = entry.getSubject();
        groups = entry.getGroups();
        publish = entry.getPublish();
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public SimplePerson getAuthor() {
        return author;
    }

    public void setAuthor(final SimplePerson author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(final List<Group> groups) {
        this.groups = groups;
    }

    public Date getPublish() {
        return publish;
    }

    public void setPublish(final Date publish) {
        this.publish = publish;
    }
}
