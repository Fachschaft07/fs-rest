package edu.hm.cs.fs.restapi.parser.cache;

import com.google.gson.reflect.TypeToken;
import edu.hm.cs.fs.common.model.Exam;
import edu.hm.cs.fs.restapi.parser.ExamParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by FHellman on 12.01.2016.
 */
public class CachedExamParser extends CachedParser<Exam> {
    private static final int UPDATETIME = 3;
    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    private static CachedExamParser instance;

    /**
     * Creates a cached parser with a specified interval.
     */
    private CachedExamParser() {
        super(new ExamParser(CachedPersonParser.getInstance(), CachedModuleParser.getInstance()),
                UPDATETIME, TIME_UNIT, UpdateType.FIXEDTIME);
    }

    @Override
    protected Type getType() {
        return new TypeToken<ArrayList<Exam>>() {
        }.getType();
    }

    public static CachedExamParser getInstance() {
        if (instance == null) {
            instance = new CachedExamParser();
        }
        return instance;
    }
}
