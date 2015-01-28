package streams;

import java.io.File;
import java.io.IOException;


/**
 * Created by devlakhova on 1/26/15.
 */
public class FileManager {

    public static void main(String[] args) throws IOException {

        File sourceFile = new File(args[0]);
        if (sourceFile.exists()) {
            System.out.println("File exist");
        }
        File destinationFile;
        int i = 2;
        do {
            destinationFile = new File(String.format("%s%d", sourceFile.getPath(),i++));
        } while (destinationFile.exists());


        try {
            FileUtils.copyAndDelete(sourceFile, destinationFile);
        } catch (SecurityException e) {
            if (!sourceFile.setWritable(true) | !destinationFile.setWritable(true)){
                System.out.println("You don't have permissions to change this files");
            } else {
                FileUtils.copyAndDelete(sourceFile, destinationFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }


}
