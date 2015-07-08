package edu.hm.cs.fs.restapi.parser.cache;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.google.gson.reflect.TypeToken;
import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.restapi.parser.ModuleParser;

/**
 * Created by Fabio on 08.07.2015.
 */
public class CachedModuleParser extends CachedParser<Module> {
    private static final int INTERVAL = 31;
    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    /**
     * Creates a cached module parser.
     */
    public CachedModuleParser() {
        super(new ModuleParser(), INTERVAL, TIME_UNIT);
    }

    /**
     *
     * @param moduleId
     * @return
     */
    public Optional<Module> findById(String moduleId) {
        return parse().parallelStream()
                .filter(module -> module.getModulCodes().parallelStream()
                        .anyMatch(moduleCode -> moduleCode.getModul().equalsIgnoreCase(moduleId)))
                .findFirst();
    }

    @Override
    public Type getType() {
        return new TypeToken<ArrayList<Module>>() {
        }.getType();
    }
}
