package lk.ijse.controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerFormController {

    static ArrayList<Socket> sockets = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(3000);

        while (true) {

            Socket accept = serverSocket.accept();
            sockets.add(accept);

            Thread ob = new Thread() {

                @Override
                public void run() {
                    InputStream inputStream = null;

                    try {
                        inputStream = accept.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String msg = "";

                        while ((msg = bufferedReader.readLine()) != null) {
                            msgForward(accept, msg);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            ob.start();
        }
    }

    public synchronized static void  msgForward(Socket receive, String msg) {
        for (Socket socket : sockets) {

            if (socket.getPort() == receive.getPort()) {
                continue;
            }
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}