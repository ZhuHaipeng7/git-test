package tools;

import view.ChatFrame;

import java.util.HashMap;

public class ManageChatFrame {
    private static HashMap<String, ChatFrame> chatFrames = new HashMap<>();

    public static void addChatFrame(String frameName, ChatFrame chatFrame){
        chatFrames.put(frameName,chatFrame);
    }

    public static ChatFrame getChatFrame(String frameName){
        return chatFrames.get(frameName);
    }

    public static String removeChatFrame(String frameName){
        if(chatFrames.get(frameName) == null){
            //System.out.println("没有这个聊天界面，删除失败");
            return "没有这个聊天界面，删除失败";
        }else {
            //System.out.println(frameName);
            chatFrames.remove(frameName);
            return "删除成功";
        }
    }

    public static int getChatCounts(){
        return chatFrames.size();
    }
}
