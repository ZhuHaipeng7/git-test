package tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class Parser {
    public Vector<Vector<Object>> getFriends(String msg){
        Vector<Vector<Object>> result = new Vector<>();
        //"123123,hh|123456,zz,offline,你好#你好#你好;7879789,tt,online,"
//        System.out.println(msg.split("\\|")[1]);
//        System.out.println(msg.split("\\|").length);
        if(msg.split("\\|").length == 1){
            return result;
        }
        String[] friends = msg.split("\\|")[1].split(";");

//        System.out.println(friends[0]);
        for(String friend : friends){
            System.out.println(friend);
            Vector<Object> vector = new Vector<>();
            String[] values = friend.split(",");
            vector.add(Long.parseLong(values[0]));
            vector.add(values[1]);
            if(values[2].equals("online")){
                vector.add(true);
            }else{
                vector.add(false);
            }
            //添加未读消息
            ArrayList<String> messageList = new ArrayList<>();
            if(!values[3].equals("NULL")){
                String[] messages = values[3].split("#");
                for(int index=0; index < messages.length; index++){
                    messageList.add(messages[index]);
                }
            }

            vector.add(messageList);


            result.add(vector);
        }
        return result;
    }

    //"name(account)"
    public String[] getAccountAndName(String str){
        String[] result = str.split("\\(");
        result[1] = result[1].substring(0, result[1].length()-1);
        return result;
    }

    //登录返回消息获取name
    public String loginGetName(String msg){
        return msg.split("\\|")[0].split(",")[1];
    }

    //登录返回消息获取account
    public long loginGetAccount(String msg){
        return Long.parseLong(msg.split("\\|")[0].split(",")[2]);
    }

    //获取消息的内容
    public String getMessageContent(String msg){
        //System.out.println("msg:"+msg);
        return msg.split("\\*")[2];
    }
    //获取消息的发送时间
    public String getMessageDate(String msg){
        String result = "";
        String date = msg.split("\\*")[3];
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            result = df2.format(df1.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    //获取消息内型
    public String getMessageType(String msg){
        return msg.split(":")[0];
    }

    //获取发送消息的信息  :myAccount|friendAccount|msg|yyyy-MM-dd a hh:mm:ss
    public Vector<Object> getSendMsgValues(String msg){
        //System.out.println(msg.split("\\|")[msg.split("\\|").length-1]);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Vector<Object> vector = new Vector<>();
        String[] values = msg.split(":")[1].split("\\|");
        vector.add(Long.parseLong(values[0]));
        vector.add(Long.parseLong(values[1]));
        vector.add(values[2]);
        try {
            //vector.add(df.parse(values[3] + ":" + msg.split(":")[2] + ":" + msg.split(":")[3]));
            vector.add(df.parse(msg.split("\\|")[msg.split("\\|").length-1]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return vector;
    }

    public Vector<Object> getNotifyValues(String msg){
        Vector<Object> vector = new Vector<>();
        String[] values = msg.split(":")[1].split(",");
        vector.add(Long.parseLong(values[0])); // sendAccount
        vector.add(Long.parseLong(values[1])); // acceptAccount
        vector.add(values[2]); // Online || Offline
        return vector;
    }

    //GROUPS_INFO:groupID,groupName,hasNewMessage;groupID,groupName,hasNewMessage;
    public Vector<Vector<Object>> getGroupsInfo(String msg){
        Vector<Vector<Object>> result = new Vector<>();
        if( msg.split(":").length == 1){
            return result;
        }
        String[] infos = msg.split(":")[1].split(";");
        for(String info : infos){
            Vector<Object> vector = new Vector<>();
            String[] values = info.split(",");
            vector.add(Long.parseLong(values[0]));
            vector.add(values[1]);
            if(values[2].equals("hasNewMessage")){
                vector.add(true);
            }else {
                vector.add(false);
            }
            result.add(vector);
        }
        return result;
    }

    public long getGroupIdInGroupMatesReply(String msg){
        return Long.parseLong(msg.split(":")[1].split("\\|")[0]);
    }

    public Vector<Vector<Object>> getGroupMatesValues(String msg){
        Vector<Vector<Object>> result = new Vector<>();
        String[] groupMates = msg.split(":")[1].split("\\|")[1].split(";");
        for(int index=0; index<groupMates.length; index++){
            Vector<Object> vector = new Vector<>();
            String[] values = groupMates[index].split(",");
            vector.add(Long.parseLong(values[0])); //groupMate_Account
            vector.add(values[1]); //groupMate_Name
            if(values[2].equals("OnLine")){
                vector.add(true);
            }else{
                vector.add(false);
            }

            result.add(vector);
        }
        return result;
    }

    public long getGroupIdInNotReadMessagesReply(String msg){
        return Long.parseLong(msg.substring(24).split("\\|")[0]);
    }

    public Vector<Vector<Object>> getGroupMessagesValues(String msg){
        Vector<Vector<Object>> result = new Vector<>();
        String[] messages = msg.substring(24).split("\\|")[1].split(";");
        for(int index=0; index<messages.length; index++){
            Vector<Object> vector = new Vector<>();
            String[] values = messages[index].split(",");
            vector.add(Long.parseLong(values[0])); //sendUserAccount
            vector.add(values[1]); //sendUserName
            vector.add(values[2]); //content
            vector.add(values[3]); //sendTime

            result.add(vector);
        }
        return result;
    }

    //msg: "RECEIVE_GROUP_MESSAGE:groupId,sendAccount,content,sendDate" 返回groupId,sendAccount,content,sendDate
    public Vector<Object> getReceiveGroupMessageValues(String msg){
        Vector<Object> vector = new Vector<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] values = msg.substring(22).split(",");
        vector.add(Long.parseLong(values[0])); //groupId
        vector.add(Long.parseLong(values[1])); //sendAccount
        vector.add(values[2]); //sendUserName
        vector.add(values[3]); //content
        try {
            vector.add(df.parse(values[4])); //sendDate
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return vector;
    }

    //msg: "REPLY_FRIEND_MESSAGE_RECORD:myAccount,friendAccount|content,date,isISend;content,date,isISend;"
    public long getMessageRecordMyAccount(String msg){
        return Long.parseLong(msg.substring(28).split("\\|")[0].split(",")[0]);
    }

    public long getMessageRecordFriendAccount(String msg){
        return Long.parseLong(msg.substring(28).split("\\|")[0].split(",")[1]);
    }

    public Vector<Vector<Object>> getMessageRecordMessages(String msg){
        Vector<Vector<Object>> result = new Vector<>();
        String[] messages = msg.substring(28).split("\\|")[1].split(";");
        for(int index=0; index<messages.length; index++){
            Vector<Object> vector = new Vector<>();
            String[] values = messages[index].split(",");
            vector.add(values[0]); //content
            vector.add(values[1]); //date
            if(values[2].equals("ISend")){ //IsISend
                vector.add(true);
            }else{
                vector.add(false);
            }
            result.add(vector);
        }
        return result;
    }

    //msg: "REPLY_GROUP_MESSAGE_RECORD:groupId|sendAccount,sendUserName,content,date;sendAccount,sendUserName,content,date;"
    public long getGroupMessageRecordGroupId(String msg){
        return Long.parseLong(msg.substring(27).split("\\|")[0]);
    }

    public Vector<Vector<Object>> getGroupMessageRecordMessages(String msg){
        Vector<Vector<Object>> result = new Vector<>();
        String[] messages = msg.substring(27).split("\\|")[1].split(";");
        for(int index=0; index<messages.length; index++){
            Vector<Object> vector = new Vector<>();
            String[] values = messages[index].split(",");
            vector.add(Long.parseLong(values[0])); //sendAccount
            vector.add(values[1]); //sendUserName
            vector.add(values[2]); //content
            vector.add(values[3]); //date

            result.add(vector);
        }
        return result;
    }
}
