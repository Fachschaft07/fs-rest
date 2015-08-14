package edu.hm.cs.fs.common.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Fabio on 18.02.2015.
 */
public class BlackboardEntry {
    
    private String id;
    private Person author;
    private String subject;
    private String text;
    private List<Person> teachers;
    private List<Group> groups;
    private Date publish;
    private Date expire;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(final Person author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public List<Person> getTeachers() {
        return teachers;
    }

    public void setTeachers(final List<Person> teachers) {
        this.teachers = teachers;
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

    public Date getExpire() {
        return expire;
    }

    public void setExpire(final Date expire) {
        this.expire = expire;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
