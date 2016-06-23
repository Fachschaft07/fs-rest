package edu.hm.cs.fs.restapi;

/**
 * Created by luca on 5/12/16.
 */
public class UrlInfo {

    private final UrlHandler.Url url;
    private final String type;
    private final String requestUrl;
    private final String root;

    public UrlInfo(UrlHandler.Url url, String type, String requestUrl, String root){
        this.url = url;
        this.type = type;
        this.requestUrl = requestUrl;
        this.root = root;
    }

    public UrlHandler.Url getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return url.toString() + ", " + type + ", " + requestUrl + ", " + root;
    }
}
