package edu.hm.cs.fs.common.model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Fabio
 */
public class PublicTransport {
    private String line;
    private String destination;
    private Date departure;

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getDepartureIn(TimeUnit timeUnit) {
        return timeUnit.convert(departure.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }
}
