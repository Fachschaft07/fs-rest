package edu.hm.cs.fs.common.model;

import edu.hm.cs.fs.common.constant.Faculty;
import edu.hm.cs.fs.common.model.simple.SimplePerson;

import java.util.List;

/**
 * @author Fabio
 */
public class Event {

    private String id;
    private String title;
    private String description;
    private SimplePerson responsible;
    private Faculty faculty;
    private boolean sticky;
    private boolean internal;
    private String url;
    private List<Booking> bookings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SimplePerson getResponsible() {
        return responsible;
    }

    public void setResponsible(SimplePerson responsible) {
        this.responsible = responsible;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public boolean isSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
