package controller;

import tools.ManageChatFrame;
import tools.ManageFriendListFrame;
import tools.ManageGroupChatFrame;
import tools.Parser;
import view.ChatFrame;
import view.GroupChatFrame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Operator {
    public void operator(String msg){
        Parser parser = new Parser();
        String msgType = parser.getMessageType(msg);
        if(msgType.equals("RECEIVE")){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Vector<Object> values = parser.getSendMsgValues(msg);
            long fromAccount = (long)values.get(0);
            long toAccount = (long)values.get(1);
            String content = (String) values.get(2);
            Date sendTime = (Date) values.get(3);
            ChatFrame chatFrame = ManageChatFrame.getChatFrame(toAccount + "" + fromAccount);
            if(chatFrame != null){
                //直接显示在聊天框
                chatFrame.showMessage(chatFrame.getPanel_Msg(), content, df.format(sendTime), false);
            }else{
                //在好友列表提示
                ManageFriendListFrame.getFriendListFrame(toAccount).receiveMsg(fromAccount, fromAccount+"*"+toAccount+"*"+content+"*"+df2.format(sendTime));
            }
        }else if(msgType.equals("NOTIFY_ALL")){
            Vector<Object> values = parser.getNotifyValues(msg);
            long sendAccount = (long)values.get(0);
            long acceptAccount = (long)values.get(1);
            Boolean state;
            if(((String)values.get(2)).equals("Online")){
                state = true;
            }else{ //下线
                state = false;
            }

            ManageFriendListFrame.getFriendListFrame(acceptAccount).receiveNotify(sendAccount, state);
        }else if(msgType.equals("GROUPS_INFO")){
            ManageFriendListFrame.getFriendListFrame().receiveGroupsInfo(parser.getGroupsInfo(msg));
        }else if(msgType.equals("GROUPMATES_INFO")){
            //System.out.println(msg);
            long groupId = parser.getGroupIdInGroupMatesReply(msg);
            Vector<Vector<Object>> groupMates = parser.getGroupMatesValues(msg);
            ManageGroupChatFrame.getGroupChatFrame(""+groupId).showGroupMates(groupMates);
        }else if(msgType.equals("NOT_READ_GROUP_MESSAGES")){
            //System.out.println(msg);
            long groupId = parser.getGroupIdInNotReadMessagesReply(msg);
            Vector<Vector<Object>> messages = parser.getGroupMessagesValues(msg);
            ManageGroupChatFrame.getGroupChatFrame(""+groupId).showHasNotReadGroupMessages(messages);
        }else if(msgType.equals("RECEIVE_GROUP_MESSAGE")){
            Vector<Object> values = parser.getReceiveGroupMessageValues(msg);
            if(ManageGroupChatFrame.getGroupChatFrame(""+values.get(0)) == null){
                //没打开聊天窗口
                ManageFriendListFrame.getFriendListFrame().receiveGroupMsg((long) values.get(0), msg);
            }else {
                //打开了聊天窗口
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                GroupChatFrame groupChatFrame = ManageGroupChatFrame.getGroupChatFrame(""+values.get(0));
                groupChatFrame.showMessage(groupChatFrame.getPanel_Msg(), (String) values.get(3), df.format((Date) values.get(4)), (long)values.get(1), (String)values.get(2));
            }
        }else if(msgType.equals("REPLY_FRIEND_MESSAGE_RECORD")){
            long myAccount = parser.getMessageRecordMyAccount(msg);
            long friendAccount = parser.getMessageRecordFriendAccount(msg);
            Vector<Vector<Object>> messages = parser.getMessageRecordMessages(msg);
            ManageChatFrame.getChatFrame(myAccount + "" + friendAccount).showMessagesRecord(messages);
        }else if(msgType.equals("REPLY_GROUP_MESSAGE_RECORD")){
            long groupId = parser.getGroupMessageRecordGroupId(msg);
            Vector<Vector<Object>> groupMessages = parser.getGroupMessageRecordMessages(msg);
            ManageGroupChatFrame.getGroupChatFrame(""+groupId).showGroupMessagesRecord(groupMessages);
        }
    }
}
