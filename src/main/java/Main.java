import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
        URL myURL = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection myConnection = (HttpURLConnection)myURL.openConnection();

        myConnection.setRequestMethod("GET");
        if(myConnection.getResponseCode() == 200)
            System.out.println("Connection successfully established.");

    }
}
