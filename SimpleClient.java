package IO;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SimpleClient {

    public final static int SOCKET_PORT = 12345;
    public final static String SERVER = "127.0.0.1";
    public final static String FILE_TO_BE_RECEIVED = "./common/webinar_99267017412_0.MP4";
    public final static int FILE_SIZE = 6022386;

    public static void main (String [] args ) throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket socket = null;
        try {
            socket = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Соединение...");

            // Получение файла
            byte [] arr  = new byte [FILE_SIZE];
            InputStream is = socket.getInputStream();
            fos = new FileOutputStream(FILE_TO_BE_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(arr,0,arr.length);
            current = bytesRead;

            do {
                bytesRead = is.read(arr, current, (arr.length - current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(arr, 0 , current);
            bos.flush();
            System.out.println(FILE_TO_BE_RECEIVED + " загружен (" + current + " байт считано)");
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (socket != null) socket.close();
        }
    }
}
