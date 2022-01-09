package tools;

import model.Group;
import model.GroupMessage;
import model.Message;
import model.User;
import service.UserService;

import javax.print.DocFlavor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Protocol {
    public String usersToString(ArrayList<User> users){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String result = "";
        for(User user : users){
            result = result + user.getAccount() + "," + user.getName();
            if(ManageClientThread.getClientThread(user.getAccount()) != null){
                result = result + ",online,";
            }else {
                result = result + ",offline,";
            }
            if(user.getMessages().size() == 0){
                result = result + "NULL";
            }else{
                for(Message message : user.getMessages()){
                    result = result + message.getSendAccount() + "*" + message.getAcceptAccount() + "*" + message.getContent() + "*" + df.format(message.getSendTime())+"#";
                }
            }

            result = result + ";";
        }
        return result.substring(0, result.length());
    }

    public String sendMsg(long fromAccount, long toAccount, String content, Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "RECEIVE:" + fromAccount + "|" + toAccount + "|" + content + "|" + df.format(date);
    }

    public String notifyAll(long sendAccount, long acceptAccount, Boolean state){
        if(state){
            return "NOTIFY_ALL:" + sendAccount + "," + acceptAccount + ",Online";
        }else {
            return "NOTIFY_ALL:" + sendAccount + "," + acceptAccount + ",Offline";
        }

    }

    public String replyGroupsInfo(List<Group> groups){
        String result = "GROUPS_INFO:";
        for(Group group : groups){
            result = result + group.getGroupId() + ",";
            result = result + group.getName() + ",";
            if(group.getMessages().size() != 0){
                result = result + "hasNewMessage;";
            }else {
                result = result + "hasNoNewMessage;";
            }
        }
        return result;
    }

    public String replyGroupMatesInfo(List<User> groupMates, long groupId){
        String result = "GROUPMATES_INFO:" + groupId + "|";
        for(User groupMate : groupMates){
            result = result + groupMate.getAccount() + ",";
            result = result + groupMate.getName() + ",";
            if(ManageClientThread.getClientThread(groupMate.getAccount()) == null){
                result = result + "OffLine;";
            }else {
                result = result + "OnLine;";
            }
        }
        return result;
    }

    public String replyGroupHasNoReadMessages(List<GroupMessage> groupMessages, long groupId){
        String result = "NOT_READ_GROUP_MESSAGES:" + groupId + "|";
        UserService service = new UserService();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(GroupMessage groupMessage : groupMessages){
            result = result + groupMessage.getSendAccount() + ",";
            result = result + service.getUserByAccount(groupMessage.getSendAccount()).getName() + ",";
            result = result + groupMessage.getContent() + ",";
            result = result + df.format(groupMessage.getSendDate()) + ";";
        }
        return result;
    }

    public String transmitGroupMessage(long groupId, long sendAccount, String sendUserName, String content, Date sendDate){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "RECEIVE_GROUP_MESSAGE:" + groupId + "," + sendAccount + "," + sendUserName + "," + content + "," +  df.format(sendDate);
    }

    public String replyFriendMessageRecord(List<Message> messages, long myAccount, long friendAccount){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "REPLY_FRIEND_MESSAGE_RECORD:" + myAccount + "," + friendAccount + "|";
        for(Message message : messages){
            result = result + message.getContent() + "," + df.format(message.getSendTime()) + ",";
            if(message.getSendAccount() == myAccount){
                result = result + "ISend;";
            }else {
                result = result + "FriendSend;";
            }
        }
        return result;
    }

    public String replyGroupMessageRecord(List<GroupMessage> groupMessages, long groupId){
        UserService userService = new UserService();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "REPLY_GROUP_MESSAGE_RECORD:" + groupId + "|";
        for (GroupMessage groupMessage : groupMessages){
            result = result + groupMessage.getSendAccount() + "," + userService.getUserByAccount(groupMessage.getSendAccount()).getName() + "," + groupMessage.getContent() + "," + df.format(groupMessage.getSendDate()) + ";";
        }
        return result;
    }
}
