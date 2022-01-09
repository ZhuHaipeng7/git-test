package net;

import controller.UserController;
import model.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private Socket client;

    public Server(){
        try {
            ServerSocket server = new ServerSocket(8000);
            while (true){
                //new Thread(() -> {
                    try {
                        Socket client = server.accept();
                        UserController userController = new UserController();
                        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        String mess = br.readLine();
                        System.out.println("客户端："+mess);


                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                        String reply = userController.login(mess, client);
                        System.out.println(reply);
                        bw.write(reply+"\n");
                        bw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                //}).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
