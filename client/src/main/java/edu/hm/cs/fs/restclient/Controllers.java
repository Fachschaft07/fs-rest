package edu.hm.cs.fs.restclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.restclient.typeadapter.DateTypeAdapter;
import edu.hm.cs.fs.restclient.typeadapter.GroupTypeAdapter;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * A factory to build the controller interfaces.
 *
 * @author Fabio
 */
public final class Controllers {
    private static final String ENDPOINT_ULR = "http://fs.cs.hm.edu";

    private Controllers() {
    }

    /**
     * Create an interface to communicate with the rest api.
     *
     * @param controllerInterface an interface of an controller.
     * @param <T>                 the type of controller.
     * @return the controller.
     */
    public static <T> T create(final Class<T> controllerInterface) {
        return create(ENDPOINT_ULR, controllerInterface);
    }

    /**
     * Create an interface to communicate with the rest api.
     *
     * @param endpointUrl         the url to connect to.
     * @param controllerInterface an interface of an controller.
     * @param <T>                 the type of controller.
     * @return the controller.
     */
    public static <T> T create(final String endpointUrl, final Class<T> controllerInterface) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .registerTypeAdapter(Group.class, new GroupTypeAdapter())
                .create();

        return new RestAdapter.Builder()
                .setEndpoint(endpointUrl)
                .setConverter(new GsonConverter(gson))
                .build()
                .create(controllerInterface);
    }
}
