package tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Protocol {
    public String loginMeg(Long account, String password){
        return "LOGIN:" + account + "," + password;
    }

    public String sendMsg(long myAccount, long friendAccount, String msg, Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "SEND_MSG:" + myAccount + "|" + friendAccount + "|" + msg + "|" + df.format(date);
    }

    public String registerMsg(long account, String name, String password){
        return "REGISTER:" + account + "," + name + "," + password;
    }

    public String getGroupsInfo(long account){
        return "GET_GROUPS:" + account;
    }

    public String getGroupMatesInfo(long groupId) {
        return "GET_GROUPMATES:" + groupId;
    }

    public String getHasNotReadGroupMessages(long groupId, long account){
        return "GET_NOT_READ_GROUP_MESSAGES:" + account + "," + groupId;
    }

    public String sendGroupMessage(long groupId, long sendAccount, String content, Date sendDate){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "SEND_GROUP_MESSAGE:" + groupId + "," + sendAccount + "," + content + "," + df.format(sendDate);
    }

    public String getMessageRecord(long myAccount, long friendAccount){
        return "GET_MESSAGE_RECORD:" + myAccount + "," + friendAccount;
    }

    public String getGroupMessageRecord(long groupId){
        return "GET_GROUP_MESSAGE_RECORD:" + groupId;
    }
}
