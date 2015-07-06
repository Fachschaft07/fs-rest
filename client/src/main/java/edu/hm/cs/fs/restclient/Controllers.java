package edu.hm.cs.fs.restclient;

import retrofit.RestAdapter;

/**
 * A factory to build the controller interfaces.
 *
 * @author Fabio
 */
public class Controllers {
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
    public static <T extends Controller> T create(final Class<T> controllerInterface) {
        return new RestAdapter.Builder()
                .setEndpoint(ENDPOINT_ULR)
                .build()
                .create(controllerInterface);
    }
}
