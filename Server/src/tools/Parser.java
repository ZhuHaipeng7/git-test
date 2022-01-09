package tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Parser {
    public String getMessageType(String msg){
        return msg.split(":")[0];
    }
    //CLIENT_EXIT:account
    public long getAccount_Exit(String msg){
        return Long.parseLong(msg.split(":")[1]);
    }
    //获取发送消息的信息  SEND_MSG:myAccount|friendAccount|msg|yyyy-MM-dd a hh:mm:ss
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

    public Vector<Object> getRegisterValues(String msg){
        Vector<Object> vector = new Vector<>();
        String[] values = msg.split(":")[1].split(",");
        vector.add(Long.parseLong(values[0]));//账号
        vector.add(values[1]);//昵称
        vector.add(values[2]);//密码
        return vector;
    }
    //msg:  "GET_GROUPS:account" 返回account
    public long getAccountInSeekGroups(String msg){
        return Long.parseLong(msg.split(":")[1]);
    }
    //msg: "GET_GROUPMATES:groupId" 返回groupId
    public long getGroupIdInSeekGroupMates(String msg){
        return Long.parseLong(msg.split(":")[1]);
    }

    //msg: "GET_NOT_READ_GROUP_MESSAGES:account,groupId" 返回account和groupId
    //msg: "GET_MESSAGE_RECORD:myAccount,friendAccount" 返回myAccount和friendAccount
    public long[] getAccountAndGroupId(String msg){
        long[] result = new long[2];
        String[] values = msg.split(":")[1].split(",");
        result[0] = Long.parseLong(values[0]);
        result[1] = Long.parseLong(values[1]);
        return result;
    }

    //msg: "SEND_GROUP_MESSAGE:groupId,sendAccount,content,sendDate" 返回groupId,sendAccount,content,sendDate
    public Vector<Object> getSendGroupMessageValues(String msg){
        Vector<Object> vector = new Vector<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] values = msg.substring(19).split(",");
        vector.add(Long.parseLong(values[0])); //groupId
        vector.add(Long.parseLong(values[1])); //sendAccount
        vector.add(values[2]); //content
        try {
            vector.add(df.parse(values[3])); //sendDate
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return vector;
    }

    public long getGroupIdInGetGroupMessageRecord(String msg){
        return Long.parseLong(msg.split(":")[1]);
    }
}
