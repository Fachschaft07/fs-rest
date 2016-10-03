package edu.hm.cs.fs.restapi;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.*;

/**
 * Created by luca on 3/31/16.
 */
public class UrlHandler {

    public enum Url {
        BLACKBOARD,
        BOOKING,
        EXAM,
        JOB,
        LESSONFK7,
        LESSONFK10,
        LOSTFOUND,
        MEAL,
        MODULE,
        NEWS,
        OCCUPIED,
        PERSON,
        PUBLICTRANSPORT,
        TERMIN
    }

    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Map<String, ?> entries;

    public static UrlInfo getUrlInfo(Url url, String... opts){

        if(entries == null) {
            // load json file
            try {
                entries = gson.fromJson(new java.util.Scanner(new ClassPathResource("urls.json").getInputStream(),"UTF-8").useDelimiter("\\A").next(), Map.class);
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        Map<String, ?> entry = (Map<String, ?>)entries.get(url.toString().toLowerCase());

        return new UrlInfo(url, (String) entry.get("type"), extractRequestUrl(entry.get("url"), opts), (String) entry.get("root"));
    }

    private static String extractRequestUrl(Object entry, String... opts){
        if(entry == null || entry instanceof String)
            return (String)entry;

        Map<String, ?> current = (Map<String, ?>)entry;
        for(int i=0; i<opts.length; i++){
            if( current.get(opts[i]) != null ) {
                if(current.get(opts[i]) instanceof String) {
                    return (String) current.get(opts[i]);
                } else if( current.get(opts[i]) instanceof Map ){
                    if( i < opts.length - 1 ) {
                        current = (Map<String, ?>) current.get(opts[i]);
                    } else {
                        return null;
                    }
                }
            }
        }

        return null;
    }
}
