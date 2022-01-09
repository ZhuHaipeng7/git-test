package tools;

import controller.Operator;
import model.User;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerThread extends Thread{

    private Socket client;
//    private BufferedReader br;
//    private BufferedWriter bw;
    private volatile boolean isRunning;

    public ServerThread(Socket client){
        this.client = client;
        this.isRunning = true;
//        this.br = br;
//        this.bw = bw;
    }

    public Socket getClient(){
        return client;
    }

    public void myStop(){
        isRunning = false;
    }
    //state为true时通知上线信息,反之下线信息
    public void notifyOthers(long account, List<User> friends, Boolean state){
        for(User friend : friends){
            ServerThread thread = ManageClientThread.getClientThread(friend.getAccount());
            if(thread != null){
                thread.sendToClient(new Protocol().notifyAll(account, friend.getAccount(), state));
            }
        }
    }

    @Override
    public void run(){
        try{
            while (isRunning){
                Operator operator = new Operator();
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String mess = br.readLine();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                System.out.println("客户端：" + mess);

                String reply = operator.operator(mess, this);
                if(!reply.equals("OVER")){
                    bw.write(reply + "\n");
                    bw.flush();
                }

            }
        }catch (IOException e){

        }
    }

    public void sendToClient(String msg){
        BufferedWriter bw = null;
        try {
            System.out.println("服务器："+msg);
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            bw.write(msg + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
