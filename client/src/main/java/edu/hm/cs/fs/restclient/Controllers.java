package edu.hm.cs.fs.restclient;

import java.io.IOException;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.hm.cs.fs.common.constant.Letter;
import edu.hm.cs.fs.common.constant.Semester;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Group;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * A factory to build the controller interfaces.
 *
 * @author Fabio
 */
public final class Controllers {
    private static final String ENDPOINT_ULR = "http://fs.cs.hm.edu/";

    private Controllers() {
    }

    /**
     * Create an interface to communicate with the rest api.
     *
     * @param controllerInterface
     *         an interface of an controller.
     * @param <T>
     *         the type of controller.
     *
     * @return the controller.
     */
    public static <T> T create(final Class<T> controllerInterface) {
        return create(ENDPOINT_ULR, controllerInterface);
    }

    /**
     * Create an interface to communicate with the rest api.
     *
     * @param endpointUrl
     *         the url to connect to.
     * @param controllerInterface
     *         an interface of an controller.
     * @param <T>
     *         the type of controller.
     *
     * @return the controller.
     */
    public static <T> T create(final String endpointUrl, final Class<T> controllerInterface) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new TypeAdapter<Date>() {
                    @Override
                    public void write(final JsonWriter out, final Date value) throws IOException {
                        out.value(value.getTime());
                    }

                    @Override
                    public Date read(final JsonReader in) throws IOException {
                        return new Date(in.nextLong());
                    }
                })
                .registerTypeAdapter(Group.class, new TypeAdapter<Group>() {
                    @Override
                    public void write(final JsonWriter out, final Group value) throws IOException {
                        out.value(value.toString());
                    }

                    @Override
                    public Group read(final JsonReader in) throws IOException {
                        in.beginObject();
                        in.nextName();
                        Letter letter = null;
                        try {
                            letter = Letter.valueOf(in.nextString());
                        } catch (Exception ignored) {
                        }

                        in.nextName();
                        Semester semester = null;
                        try {
                            semester = Semester.valueOf(in.nextString());
                        } catch (Exception ignored) {
                        }

                        in.nextName();
                        Study study = null;
                        try {
                            study = Study.valueOf(in.nextString());
                        } catch (Exception ignored) {
                        }
                        in.endObject();

                        return new Group(study, semester, letter);
                    }
                })
                .create();

        return new RestAdapter.Builder()
                .setEndpoint(endpointUrl)
                .setConverter(new GsonConverter(gson))
                .build()
                .create(controllerInterface);
    }
}
