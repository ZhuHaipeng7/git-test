import tools.ManageChatFrame;
import view.ChatFrame;
import view.FriendListFrame;
import view.GroupChatFrame;
import view.LoginFrame;

import java.util.ArrayList;

public class test {
    public static void main(String[] args){
//        Client client = new Client();
//        client.sendMsg("test");
        //new LoginFrame();
//       new FriendListFrame(111111, "zhp", "123123,hh|123456,zz,offline,NULL;7879789,tt,online,你好#你好");

        //    new ChatFrame(123456,"me",999999, "him");


//        ManageChatFrame.addChatFrame("111111" + "123456", new ChatFrame(111111, "zzz", Long.parseLong("123456"), "hh"));
//        System.out.println(ManageChatFrame.getChatCounts());
        //ManageChatFrame.removeChatFrame("111111" + "123456");
        //System.out.println(ManageChatFrame.getChatCounts());
        new GroupChatFrame(111111, "zhp", 123123, "测试", true, new ArrayList<>());
    }
}

