package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.SimpleModule;
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
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public Optional<SimpleModule> findByIdSimple(String moduleId) throws MalformedURLException, IOException {
        return findById(moduleId).map(module -> {
            SimpleModule sModule = new SimpleModule();
            sModule.setId(module.getId());
            sModule.setName(module.getName());
            return sModule;
        });
    }

    /**
     *
     * @param moduleId
     * @return
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public Optional<Module> findById(String moduleId) throws MalformedURLException, IOException {
        return parse().parallelStream()
                .filter(module -> moduleId.equals(module.getId()))
                .findFirst();
    }

    @Override
    public Type getType() {
        return new TypeToken<ArrayList<Module>>() {
        }.getType();
    }
}
