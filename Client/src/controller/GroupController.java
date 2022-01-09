package controller;

import net.Client;
import tools.Protocol;

public class GroupController {
    public void getGroupsInfo(long account){
        Protocol protocol = new Protocol();
        Client.sendMsg(protocol.getGroupsInfo(account));
    }
}
