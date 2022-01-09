package net;

import controller.Operator;
import tools.Parser;
import tools.Protocol;

import java.io.*;
import java.net.Socket;

public class Client {
    static Socket s;
    static InputStream is;
    static OutputStream os;
    static BufferedWriter bw;
    static BufferedReader br;
    static Boolean isLogin = false;
    static Thread thread;
    public Client(){
//        try {
//            s = new Socket("127.0.0.1", 8000);
//            is = s.getInputStream();
//            os = s.getOutputStream();
//            bw = new BufferedWriter(new OutputStreamWriter(os));
//            br = new BufferedReader(new InputStreamReader(is));
//        }catch (IOException e){
//            e.printStackTrace();
//        }
    }

    public void init() {
        try {
            s = new Socket("127.0.0.1", 8000);
            is = s.getInputStream();
            os = s.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));
            br = new BufferedReader(new InputStreamReader(is));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sendMsg(String msg){
        try {
            bw.write(msg+"\n");
            bw.flush();
            System.out.println("客户端："+msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String login(String msg){
        try {
            bw.write(msg+"\n");
            bw.flush();
            System.out.println("客户端："+msg);

            String reply = br.readLine();
            String[] strs = reply.split(":");
            if(strs[0].equals("LOGIN_SUCCESS")){
//                long account = new Parser().loginGetAccount(strs[1]);
//                sendMsg(new Protocol().notifyAllOnline(account));
                thread = new Thread(() -> {
                    while(true){
                        try {
                            Operator operator = new Operator();

                            String serverMsg = br.readLine();
                            System.out.println("服务器："+serverMsg);

                            operator.operator(serverMsg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

                return strs[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean register(String msg){
        try {
            bw.write(msg+"\n");
            bw.flush();
            System.out.println("客户端："+msg);

            String reply = br.readLine();
            String[] strs = reply.split(":");
            if(strs[0].equals("REGISTER_SUCCESS")){
                return true;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
