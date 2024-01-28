package lk.ijse.service;

import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

public class ClientSide extends Task<String> {

    Socket socket;

    public ClientSide() throws IOException {
        socket = new Socket("localhost", 3000);
    }

    @Override
    protected String call() throws Exception {

        while (true) {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String msg = "";

            while ((msg = reader.readLine()) != null) {
                updateValue(msg);
            }
        }
    }

    public void sendClient(String msg) throws IOException {
        PrintStream out = new PrintStream(socket.getOutputStream(), true);
        out.println(msg);
    }
}