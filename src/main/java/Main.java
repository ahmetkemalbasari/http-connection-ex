import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Post> posts = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        URL myURL = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection myConnection = (HttpURLConnection)myURL.openConnection();

        myConnection.setRequestMethod("GET");
        if(myConnection.getResponseCode() == 200)
            System.out.println("Connection successfully established.");

        BufferedReader logReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
        Scanner myScanner = new Scanner(System.in);

        while(logReader.readLine() != null && logReader.readLine() != null){
            try {
                int userId = Integer.parseInt(logReader.readLine().substring(14).replace(",",""));
                int messageId = Integer.parseInt(logReader.readLine().substring(10).replace(",", ""));
                String title = logReader.readLine().substring(13);
                String body = logReader.readLine().substring(12);
                posts.add(new Post(userId, messageId, title, body));
            }catch (Exception e){
                break;
            }

        }
        logReader.close();

        System.out.println("Total post count: " + posts.size());
        for(Post a : posts){
            System.out.println("used id: " + a.userId + "   post id: " + a.id);
        }
        System.out.println("Press enter to exit");
        myScanner.nextLine();
    }
}
