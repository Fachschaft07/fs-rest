package edu.hm.cs.fs.restclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.restclient.typeadapter.DateTypeAdapter;
import edu.hm.cs.fs.restclient.typeadapter.GroupTypeAdapter;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import java.util.Date;

/**
 * A factory to build the controller interfaces.
 *
 * @author Fabio
 */
public final class FsRestClient {
    private static final String ENDPOINT_ULR = "http://fs.cs.hm.edu";
    private static RestClientV1 instance;
    private static String connectionUrl;

    private FsRestClient() {
    }

    /**
     * Create an interface to communicate with the rest api.
     *
     * @return the controller.
     */
    public static RestClientV1 getV1() {
        return getV1(ENDPOINT_ULR);
    }

    /**
     * Create an interface to communicate with the rest api.
     *
     * @param url to connect to.
     * @return the controller.
     */
    public static RestClientV1 getV1(final String url) {
        if (instance == null || !connectionUrl.equals(url)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .registerTypeAdapter(Group.class, new GroupTypeAdapter())
                    .create();

            connectionUrl = url;
            instance = new RestAdapter.Builder()
                    .setEndpoint(url)
                    .setConverter(new GsonConverter(gson))
                    .build()
                    .create(RestClientV1.class);
        }
        return instance;
    }
}
