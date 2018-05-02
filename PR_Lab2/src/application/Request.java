package application;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class Request implements Callable<Response> {

	private URL url;
	private String key = "55193451-1409-4729-9cd4-7c65d63b8e76";
    public Request(URL url) {
        this.url = url;
    }

    @Override
    public Response call() throws Exception {
    	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    	connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept", "text/csv");
        connection.setRequestProperty  ("x-api-key", key);
        InputStream content = (InputStream)connection.getInputStream();
        return new Response(content);
    }
    
    
}
