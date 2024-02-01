package lk.ijse.controller;

import com.google.gson.Gson;
import lk.ijse.dto.TransferData;

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

                    try {
                        InputStream inputStream = accept.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String msg = "";

                        while ((msg = bufferedReader.readLine()) != null) {
                            TransferData td = new Gson().fromJson(msg, TransferData.class);
                            msgForward(td, accept);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            ob.start();
        }
    }

    public synchronized static void msgForward(TransferData td, Socket usr) {

        if (td.getCommand().equals("TEXT")) {
            txtMsgForward(usr, td.getMsg());
        }

        if (td.getCommand().equals("IMAGE")) {
            imgMsgForward(usr, td.getMsg());
        }
    }

    public synchronized static void imgMsgForward(Socket usr, String msg) {

        for (Socket socket : sockets) {
            if (socket.getPort() == usr.getPort()) {
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

    public synchronized static void txtMsgForward(Socket usr, String msg) {
        for (Socket socket : sockets) {

            if (socket.getPort() == usr.getPort()) {
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