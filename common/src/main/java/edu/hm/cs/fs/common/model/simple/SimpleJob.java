package edu.hm.cs.fs.common.model.simple;

import edu.hm.cs.fs.common.model.Job;

/**
 * @author Luca
 */
public class SimpleJob {
    private String jobId;
    private String title;
    private String provider;

    public SimpleJob() {
    }

    public SimpleJob(final Job job) {
        jobId = job.getId();
        title = job.getTitle();
        provider = job.getProvider();
    }

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

}
