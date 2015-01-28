package streams;

import com.mycompany.app.threads.ExecutionPool;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by devlakhova on 1/27/15.
 */
public class URLReader {


    public static final String DIR_TO_SAVE = "/Users/devlakhova/Downloads/trash";
    private ExecutionPool executionPool = new ExecutionPool(5);

    public static void main(String[] args) {
        try {
            URL url = new URL(args[0]);
            URLReader urlReader = new URLReader();
            urlReader.readURL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readURL(URL url) throws IOException {
        URLConnection yc = url.openConnection();
        BufferedReader in = null;
        try {
             in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                if (inputLine.contains("\"http:") && !inputLine.contains("DOCTYPE") && !inputLine.contains("xml") && !inputLine.contains("script")) {
                    String urlString = inputLine.substring(inputLine.indexOf("http:"), inputLine.indexOf("\"", inputLine.indexOf("http:")));
                    if(!urlString.equals(url.toString())) {
                        executionPool.submit(new FileDownloader(new URL(urlString), DIR_TO_SAVE));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }


    /*public static void downloadFile(URL link) throws IOException {
        String fileName = link.getFile(); //The file that will be saved on your computer

        if (!fileName.contains(".")) {
            return;
        }
        fileName = fileName.substring(fileName.lastIndexOf("/")+1);
        String saveFilePath = DIR_TO_SAVE + File.separator + fileName;

        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = new BufferedInputStream(link.openStream());
            out = new FileOutputStream(saveFilePath);
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf)))
            {
                out.write(buf, 0, n);
            }
        } catch (IOException e) {
            throw new IOException("Can't download data from the specified url");
        }
        finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        System.out.println("Finished");
    }*/


}
