package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.restapi.parser.ModuleParser;

/**
 * @author Fabio
 */
public class CachedModuleParser extends ByIdCachedParser<Module> {
    private static final int INTERVAL = 31;
    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    /**
     * Creates a cached module parser.
     */
    public CachedModuleParser() {
        super(new ModuleParser(new CachedPersonParser()), INTERVAL, TIME_UNIT);
    }

    @Override
    protected String getId(Module item) {
        return item.getId();
    }

    @Override
    protected Type getType() {
        return new TypeToken<ArrayList<Module>>() {
        }.getType();
    }
}
