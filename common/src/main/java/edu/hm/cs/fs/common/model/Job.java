package edu.hm.cs.fs.common.model;

import java.util.Date;

import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.simple.SimplePerson;

/**
 * @author Fabio
 */
public class Job {
    private String jobId;
    private String title;
    private String provider;
    private String description;
    private Study program;
    private SimplePerson contact;
    private Date expire;
    private String url;

    public String getId() {
        return jobId;
    }

    public void setId(final String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Study getProgram() {
        return program;
    }

    public void setProgram(final Study program) {
        this.program = program;
    }

    public SimplePerson getContact() {
        return contact;
    }

    public void setContact(final SimplePerson contact) {
        this.contact = contact;
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
