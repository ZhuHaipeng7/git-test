package tools;

import view.ChatFrame;
import view.GroupChatFrame;

import java.util.HashMap;

public class ManageGroupChatFrame {
    private static HashMap<String, GroupChatFrame> groupChatFrames = new HashMap<>();

    public static void addGroupChatFrame(String frameName, GroupChatFrame chatFrame){
        //System.out.println("打开了窗口:" + frameName);
        groupChatFrames.put(frameName,chatFrame);
    }

    public static GroupChatFrame getGroupChatFrame(String frameName){
        //System.out.println("获取窗口:" + groupChatFrames.get(frameName));
        return groupChatFrames.get(frameName);
    }

    public static String removeGroupChatFrame(String frameName){
        if(groupChatFrames.get(frameName) == null){
            //System.out.println("没有这个聊天界面，删除失败");
            return "没有这个聊天界面，删除失败";
        }else {
            //System.out.println(frameName);
            groupChatFrames.remove(frameName);
            return "删除成功";
        }
    }

    public static int getGroupChatCounts(){
        return groupChatFrames.size();
    }
}
