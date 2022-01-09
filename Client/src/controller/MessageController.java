package controller;

import net.Client;
import tools.Protocol;

import java.util.Date;

public class MessageController {
    public void sendMeg(long myAccount, long friendAccount, String msg){
        Protocol protocol = new Protocol();
        Client.sendMsg(protocol.sendMsg(myAccount, friendAccount, msg, new Date()));
    }

    public void getMessageRecord(long myAccount, long friendAccount){
        Protocol protocol = new Protocol();
        Client.sendMsg(protocol.getMessageRecord(myAccount, friendAccount));
    }
}
