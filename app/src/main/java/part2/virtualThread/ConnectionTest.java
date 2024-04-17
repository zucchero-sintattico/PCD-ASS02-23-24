package part2.virtualThread;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ConnectionTest {
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            Thread t = Thread.ofVirtual().unstarted(
                    () -> {
                        try {
//                            OkHttpClient client = new OkHttpClient();
//                            Request request = new Request.Builder()
//                               .url("https://www.google.com")
//                               .build();
//                            Response response = client.newCall(request).execute();
                            HttpURLConnection conn = (HttpURLConnection) (new URL("https://www.google.com").openConnection());
                            InputStream is = conn.getInputStream();
                            String str = new String(is.readAllBytes());
                            is.close();
                            System.out.println("CONNECTION DONE");
                        } catch (Exception e) {
                            System.out.println("EXCEPTION"+ e);
                        }
                    }
            );
            threads.add(t);
        }
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("DONE");
    }
}
