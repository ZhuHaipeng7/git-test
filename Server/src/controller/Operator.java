package controller;

import model.Group;
import model.GroupMessage;
import model.Message;
import model.User;
import service.GroupMessageService;
import service.GroupService;
import service.MessageService;
import service.UserService;
import tools.ManageClientThread;
import tools.Parser;
import tools.Protocol;
import tools.ServerThread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Operator {
    public String operator(String msg, ServerThread fromThread){
        UserService userService= new UserService();
        MessageService messageService = new MessageService();
        GroupService groupService = new GroupService();
        GroupMessageService groupMessageService = new GroupMessageService();
        Parser parser = new Parser();
        Protocol protocol = new Protocol();
        String msgType = parser.getMessageType(msg);
        //System.out.println("msgType:"+msgType);
        if(msgType.equals("CLIENT_EXIT")){
            long sendAccount = parser.getAccount_Exit(msg);
            fromThread.notifyOthers(sendAccount, new UserService().getFriendsByAccount(sendAccount), false);
            fromThread.myStop();
            ManageClientThread.removeClientThread(sendAccount);
            return "OVER";
        }else if(msgType.equals("SEND_MSG")){
            //System.out.println("发送了");
            Vector<Object> values = parser.getSendMsgValues(msg);
            long fromAccount = (long)values.get(0);
            long toAccount = (long)values.get(1);
            String content = (String) values.get(2);
            Date sendTime = (Date) values.get(3);
            Boolean state = false;
            ServerThread toThread = ManageClientThread.getClientThread(toAccount);
            //System.out.println(toThread.toString()+"----");
            if(toThread != null){
                state = true;
                toThread.sendToClient(protocol.sendMsg(fromAccount, toAccount, content, sendTime));
            }
            messageService.addMessage(new Message(fromAccount, toAccount, content, sendTime, state));
            return "OVER";
        }else if(msgType.equals("GET_GROUPS")){
            long account = parser.getAccountInSeekGroups(msg);
            List<Group> groups = groupService.getGroupsByAccount(account);
            for(Group group : groups){
                group.setMessages(groupMessageService.getHasNoReadMessages(group.getGroupId(), group.getHasReadMessageId()));
            }
            return protocol.replyGroupsInfo(groups);
        }else if(msgType.equals("GET_GROUPMATES")){
            long groupId = parser.getGroupIdInSeekGroupMates(msg);
            ArrayList<User> groupMates = userService.getGroupMatesByGroupId(groupId);
            return protocol.replyGroupMatesInfo(groupMates, groupId);
        }else if(msgType.equals("GET_NOT_READ_GROUP_MESSAGES")){
            long[] values = parser.getAccountAndGroupId(msg); //values[0]是account,values[1]是groupId
            List<GroupMessage> groupMessages = groupMessageService.getHasNoReadMessagesByAccount(values[1], values[0]);
            return protocol.replyGroupHasNoReadMessages(groupMessages, values[1]);
        }else if(msgType.equals("SEND_GROUP_MESSAGE")){
            Vector<Object> values = parser.getSendGroupMessageValues(msg);
            groupMessageService.addGroupMessage((long) values.get(0), (long)values.get(1), (String) values.get(2), (Date) values.get(3));
            //转发消息
            ArrayList<User> groupMates = userService.getGroupMatesByGroupId((long) values.get(0));
            for(User groupMate : groupMates){
                if(ManageClientThread.getClientThread(groupMate.getAccount()) != null){
                    groupMessageService.updateLastReadMessageId((long) values.get(0), groupMate.getAccount()); //更新已读消息id
                    if(groupMate.getAccount() != (long)values.get(1)){
                        ManageClientThread.getClientThread(groupMate.getAccount()).sendToClient(protocol.transmitGroupMessage((long) values.get(0), (long)values.get(1), userService.getUserByAccount((long)values.get(1)).getName(), (String) values.get(2), (Date) values.get(3)));
                    }
                }
            }
            return "OVER";
        }else if(msgType.equals("GET_MESSAGE_RECORD")){
            long[] values = parser.getAccountAndGroupId(msg); //values[0]是myAccount,values[1]是friendAccount
            List<Message> messages = messageService.getMessages(values[0], values[1]);
            return protocol.replyFriendMessageRecord(messages, values[0], values[1]);
        }else if(msgType.equals("GET_GROUP_MESSAGE_RECORD")){
            long groupId = parser.getGroupIdInGetGroupMessageRecord(msg);
            List<GroupMessage> groupMessages = groupMessageService.getGroupMessagesByGroupId(groupId);
            return protocol.replyGroupMessageRecord(groupMessages, groupId);
        }
        return "?";
    }
}
