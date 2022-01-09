package tools;

import view.ChatFrame;
import view.FriendListFrame;

import java.util.HashMap;
import java.util.Hashtable;

public class ManageFriendListFrame {
    private static HashMap<Long, FriendListFrame> friendListFrames = new HashMap<>();

    public static void addFriendListFrame(long frameName,FriendListFrame fl){
        friendListFrames.put(frameName,fl);
    }

    public static FriendListFrame getFriendListFrame(long frameName){
        return friendListFrames.get(frameName);
    }

    public static FriendListFrame getFriendListFrame(){
        for(long key : friendListFrames.keySet()){
            return friendListFrames.get(key);
        }
        return null;
    }

    public static FriendListFrame removeFriendListFrame(long frameName){
        return friendListFrames.remove(frameName);
    }
}
