package lk.ijse.service;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import lk.ijse.dto.ReceivedData;
import lk.ijse.dto.TransferData;

import java.io.*;
import java.net.Socket;

public class ClientSide extends Task<ReceivedData> {

    Socket socket;

    public ClientSide() throws IOException {
        socket = new Socket("localhost", 3000);
    }

    @Override
    protected ReceivedData call() throws Exception {

        while (true) {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String msg = "";

            while ((msg = reader.readLine()) != null) {
                updateValue(new ReceivedData(msg));
            }
        }
    }

    public void sendClient(TransferData msg) throws IOException {
        PrintStream out = new PrintStream(socket.getOutputStream(), true);
        String data = new Gson().toJson(msg);
        out.println(data);
    }
}