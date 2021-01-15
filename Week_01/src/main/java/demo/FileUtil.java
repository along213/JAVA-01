package demo;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Description TODO
 * @Author LHL
 * @Date 18:46:47
 */
public class FileUtil {

    public static byte[] readDeal(File file) {
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        try {
            fileInputStream = new FileInputStream(file);
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            close(fileInputStream);
        }
        return bytes;
    }

    //关闭流
    private static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (null != closeable) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
