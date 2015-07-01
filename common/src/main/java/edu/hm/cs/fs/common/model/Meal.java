package edu.hm.cs.fs.common.model;

import edu.hm.cs.fs.common.constant.Additive;
import edu.hm.cs.fs.common.constant.MealType;

import java.util.Date;
import java.util.List;

/**
 * Created by Fabio on 18.02.2015.
 */
public class Meal  {
    private Date date;
    private MealType type;
    private String name;
    private List<Additive> additives;

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public MealType getType() {
        return type;
    }

    public void setType(final MealType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Additive> getAdditives() {
        return additives;
    }

    public void setAdditives(List<Additive> additives) {
        this.additives = additives;
    }
}
