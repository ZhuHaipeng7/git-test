package controller;

import net.Client;
import tools.Protocol;

import java.util.Date;

public class GroupMessageController {
    public void getHasNoReadMessages(long groupId, long account){
        Protocol protocol = new Protocol();
        Client.sendMsg(protocol.getHasNotReadGroupMessages(groupId, account));
    }

    public void sendGroupMessage(long groupId, long sendAccount, String content, Date sendDate){
        Protocol protocol = new Protocol();
        Client.sendMsg(protocol.sendGroupMessage(groupId, sendAccount, content, sendDate));
    }

    public void getGroupMessagesRecord(long groupId){
        Protocol protocol = new Protocol();
        Client.sendMsg(protocol.getGroupMessageRecord(groupId));
    }
}
