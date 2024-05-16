import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.InputStream;

public class Main {

    public static ArrayList<Post> posts = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        URL myURL = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection myConnection = (HttpURLConnection)myURL.openConnection();

        myConnection.setRequestMethod("GET");
        if(myConnection.getResponseCode() == 200)
            System.out.println("Connection successfully established.");
        else{
            System.out.println("Connection failed");
            throw new Exception();
        }

        InputStream inStream = myConnection.getInputStream();
        Scanner scanner = new Scanner(inStream).useDelimiter("\\A");
        String jsonResponse = scanner.hasNext() ? scanner.next() : "";


        Gson gson = new Gson();
        Type postListType = new TypeToken<List<Post>>(){}.getType();
        posts = gson.fromJson(jsonResponse, postListType);

        System.out.println("Total post count: " + posts.size());
        for(Post a : posts){
            System.out.println("used id: " + a.userId + "   post id: " + a.id);
        }
        System.out.println("Press enter to exit");
        Scanner myScanner = new Scanner(System.in);
        myScanner.nextLine();
    }
}
