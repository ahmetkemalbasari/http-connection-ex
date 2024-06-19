import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static ArrayList<Post> posts = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        URL myURL = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection myConnection = (HttpURLConnection) myURL.openConnection();

        myConnection.setRequestMethod("GET");
        if (myConnection.getResponseCode() == 200)
            System.out.println("Connection successfully established.");
        else {
            System.out.println("Connection failed");
            throw new Exception();
        }

        InputStream inStream = myConnection.getInputStream();
        Scanner scanner = new Scanner(inStream).useDelimiter("\\A");
        String jsonResponse = scanner.hasNext() ? scanner.next() : "";


        Gson gson = new Gson();
        Type postListType = new TypeToken<List<Post>>() {
        }.getType();
        posts = gson.fromJson(jsonResponse, postListType);

        System.out.println("Total post count: " + posts.size());
        for (Post a : posts) {
            System.out.println("used id: " + a.userId + "   post id: " + a.id);
        }
        //System.out.println("Press enter to exit");
        Scanner myScanner = new Scanner(System.in);
        //myScanner.nextLine();

        int commentCount = postCount("https://jsonplaceholder.typicode.com/comments");

        //POST
        String testJSON = "{\"postId\": 1, \"id\": " + ++commentCount + ", \"name\": \"randomName\", \"email\": \"random@gmail.com\", \"body\": \"random texts\"}";
        URL commentURL = new URL("https://jsonplaceholder.typicode.com/posts/1/comments");
        HttpURLConnection commentConnection = (HttpURLConnection) commentURL.openConnection();

        commentConnection.setRequestMethod("POST");
        commentConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        commentConnection.setRequestProperty("Accept", "application/json");
        commentConnection.setDoOutput(true);

        byte[] bytes = testJSON.getBytes("utf-8");

        OutputStream output = commentConnection.getOutputStream();
        output.write(bytes, 0, bytes.length);

        commentConnection.disconnect();


        //PUT
        URL commentURL2 = new URL("https://jsonplaceholder.typicode.com/comments/" + commentCount);
        HttpURLConnection commentConnection2 = (HttpURLConnection) commentURL2.openConnection();
        commentConnection2.setRequestMethod("PUT");
        commentConnection2.setRequestProperty("Content-Type", "application/json; utf-8");
        commentConnection2.setRequestProperty("Accept", "application/json");
        commentConnection2.setDoOutput(true);

        testJSON = "{\"postId\": 1, \"name\": \"randomName\", \"email\": \"random@gmail.com\", \"body\": \"new random texts\"}";

        bytes = testJSON.getBytes("utf-8");

        OutputStream output2 = commentConnection2.getOutputStream();
        output2.write(bytes, 0, bytes.length);

        System.out.println("Response code of PUT request: " + commentConnection.getResponseCode());


        //DELETE
        URL commentURL3 = new URL("https://jsonplaceholder.typicode.com/comments/" + commentCount);
        HttpURLConnection commentConnection3 = (HttpURLConnection) commentURL3.openConnection();

        commentConnection3.setRequestMethod("DELETE");

        System.out.println("Response code of DELETE request: " + commentConnection3.getResponseCode());


    }

    private static int postCount(String urlString) throws Exception {
        URL myURL = new URL(urlString);
        HttpURLConnection myConnection = (HttpURLConnection) myURL.openConnection();

        myConnection.setRequestMethod("GET");

        InputStream inStream = myConnection.getInputStream();
        Scanner scanner = new Scanner(inStream).useDelimiter("\\A");
        String jsonResponse = scanner.hasNext() ? scanner.next() : "";


        Gson gson = new Gson();
        Type postListType = new TypeToken<List<Post>>() {
        }.getType();
        posts = gson.fromJson(jsonResponse, postListType);

        return posts.size();
    }

}

