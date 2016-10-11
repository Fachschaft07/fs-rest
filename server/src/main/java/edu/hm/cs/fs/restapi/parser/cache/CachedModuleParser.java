package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.restapi.parser.ModuleParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Fabio
 */
public class CachedModuleParser extends ByIdCachedParser<Module> {
    private static final int UPDATETIME = 3;
    private static final TimeUnit TIME_UNIT = TimeUnit.HOURS;

    private static CachedModuleParser instance;

    /**
     * Creates a cached module parser.
     */
    private CachedModuleParser() {
        super(new ModuleParser(CachedPersonParser.getInstance()), UPDATETIME, TIME_UNIT, UpdateType.FIXEDTIME);
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

    public static CachedModuleParser getInstance() {
        if (instance == null) {
            instance = new CachedModuleParser();
        }
        return instance;
    }
}
