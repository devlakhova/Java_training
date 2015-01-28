package streams;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by devlakhova on 1/28/15.
 */
public class FileDownloader implements Runnable {

    private URL url;
    private String directoryToSave;

    public FileDownloader(URL url, String directoryToSave) {
        this.url = url;
        this.directoryToSave = directoryToSave;
    }

    @Override
    public void run() {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Accept-Encoding", "gzip");
            int responseCode = con.getResponseCode();
            Map<String,List<String>> map = con.getHeaderFields();
            List<String> value = map.get("Content-Encoding");
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            String file = fileForUrl(url);
            if(file == null) {
                return;
            }
            con.connect();

            String encoding = con.getContentEncoding();

            String contentType = con.getContentType();
            System.out.println("Content-Type" + contentType);

            InputStream inputStream = null;
            if (encoding != null && encoding.equals("gzip")) {
                inputStream = new GZIPInputStream(con.getInputStream());
            } else {
                inputStream = con.getInputStream();
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1!=(n=inputStream.read(buf)))
                {
                    out.write(buf, 0, n);
                }
            } catch (IOException e) {
                throw new IOException("Can't download data from the specified url");
            }
            finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(out);
            }
            System.out.println("Finished");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String fileForUrl(URL url) {
        String fileName = url.getFile();
        if (!fileName.contains(".")) {
            return null;
        }

        fileName = fileName.substring(fileName.lastIndexOf("/")+1);
        String saveFilePath = directoryToSave + File.separator + fileName;
        return saveFilePath;
    }
}
