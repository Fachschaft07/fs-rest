package edu.hm.cs.fs.restapi.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.hm.cs.fs.common.model.Presence;

/**
 * Created by Fabio on 18.02.2015.
 */
public class PresenceParser extends AbstractJsonParser<Presence> {
    private final static String URL = "http://fs.cs.hm.edu/presence/?app=true";

    public PresenceParser() {
        super(URL);
    }

    @Override
    public List<Presence> convert(JSONObject data) {
        List<Presence> result = new ArrayList<>();
        try {
            JSONArray jsonArray = data.getJSONArray("persons");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject person = jsonArray.getJSONObject(i);

                Presence presenceImpl = new Presence();
                presenceImpl.setName(person.getString("nickName"));
                presenceImpl.setStatus(person.getString("status"));

                result.add(presenceImpl);
            }
        } catch (JSONException e) {
            Logger.getGlobal().log(Level.SEVERE, "", e);
        }
        return result;
    }
}
