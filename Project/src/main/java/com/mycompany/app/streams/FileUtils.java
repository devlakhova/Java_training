package com.mycompany.app.streams;

import java.io.*;


/**
 * Created by devlakhova on 1/26/15.
 */
public class FileUtils {

    public static void copyAndDelete (File source, File destination) throws IOException {
        if (source.isDirectory()) {
            File[] files = source.listFiles();
            for (File subFile : files) {
                copyAndDelete(subFile, destination);
            }
        } else {
            System.out.println("Copying file " + source.getName());
            copy(source, destination);
            System.out.println("Deleting file " + source.getName());
            delete(source);
        }
        if (!destination.renameTo(source)) {
           throw new SecurityException("Couldn't rename file");
        }
    }


    public static void delete(File file) throws IOException {

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                delete(subFile);
            }
        }
        if (!file.delete()) {
            throw new SecurityException();
        }
    }

    public static void copy(File source, File destination) throws IOException {

        if( source.isDirectory() ) {
            copyDirectory(source, destination);
        } else {
            copyFile(source, destination);
        }
    }

    private static void copyDirectory(File source, File destination) throws IOException {
        if( !source.isDirectory() ) {
            throw new IllegalArgumentException("Source (" + source.getPath() + ") must be a directory.");
        }

        if( !source.exists() ) {
            throw new IllegalArgumentException("Source directory (" + source.getPath() + ") doesn't exist.");
        }

        if( destination.exists() ) {
            throw new IllegalArgumentException("Destination (" + destination.getPath() + ") exists.");
        }

        if (!destination.mkdirs()) {
            throw new IOException("Can't make directories");
        }
        File[] files = source.listFiles();

        for( File file : files ) {
            if( file.isDirectory() ) {
                copyDirectory( file, new File( destination, file.getName() ) );
            } else {
                copyFile( file, new File( destination, file.getName() ) );
            }
        }
    }

    private static void copyFile(File source, File destination) throws IOException {
//        try ( InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(destination) ) {
//            byte[] buffer = new byte[1024];
//            int len = in.read(buffer);
//            while (len != -1) {
//                out.write(buffer, 0, len);
//                len = in.read(buffer);
//            }
//            in.close();
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
}
