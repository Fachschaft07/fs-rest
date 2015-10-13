package edu.hm.cs.fs.restapi.parser.cache;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.gson.reflect.TypeToken;

import edu.hm.cs.fs.common.model.BlackboardEntry;
import edu.hm.cs.fs.restapi.parser.BlackboardParser;

/**
 * @author Fabio
 */
public class CachedBlackboardParser extends CachedParser<BlackboardEntry> {
    private static final int INTERVAL = 1;
    private static final TimeUnit TIME_UNIT = TimeUnit.HOURS;

    private static CachedBlackboardParser instance;
    
    /**
     * Creates a cached person parser.
     */
    private CachedBlackboardParser() {
        super(new BlackboardParser(CachedPersonParser.getInstance()), INTERVAL, TIME_UNIT, UpdateType.INTERVAL);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ArrayList<BlackboardEntry>>() {
        }.getType();
    }
    
    public static CachedBlackboardParser getInstance(){
      if(instance==null){
        instance = new CachedBlackboardParser();
      }
      return instance;
    }
}
