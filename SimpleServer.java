package IO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public final static int SOCKET_PORT = 12345;
    public final static String FILE_TO_BE_SENT = "./common/server/IMG_4439.JPG";

    public static void main (String [] args ) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(SOCKET_PORT);
            while (true) {
                System.out.println("Ожидание...");
                try {
                    socket = serverSocket.accept();
                    System.out.println("Соединение принято: " + socket);
                    // Отсылка файла
                    File file = new File (FILE_TO_BE_SENT);
                    byte [] arr = new byte [(int) file.length()];
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    bis.read(arr,0, arr.length);
                    os = socket.getOutputStream();
                    System.out.println("Отсылка " + FILE_TO_BE_SENT + "(" + arr.length + " байт)");
                    os.write(arr,0, arr.length);
                    os.flush();
                    System.out.println("Файл выслан");
                }
                finally {
                    if (bis != null) bis.close();
                    if (os != null) os.close();
                    if (socket!=null) socket.close();
                }
            }
        }
        finally {
            if (serverSocket != null) serverSocket.close();
        }
    }
}
