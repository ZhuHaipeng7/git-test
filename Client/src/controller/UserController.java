package controller;

import net.Client;
import tools.Protocol;

public class UserController {
    public void clientExit(long account){
        Client.sendMsg("CLIENT_EXIT:"+account);
    }

    public void getGroupMates(long groupId){
        Protocol protocol = new Protocol();
        Client.sendMsg(protocol.getGroupMatesInfo(groupId));
    }
}
