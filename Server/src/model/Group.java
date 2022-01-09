package model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private long groupId;
    private String name;
    private List<User> groupMates;
    private List<GroupMessage> messages;
    //这个变量在用户取自己的群组时  表示此用户读到了这个群的哪一条消息的id
    private long hasReadMessageId;

    public Group(){
//        groupMates = new ArrayList<>();
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getGroupMates() {
        return groupMates;
    }

    public void setGroupMates(List<User> groupMates) {
        this.groupMates = groupMates;
    }

    public List<GroupMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<GroupMessage> messages) {
        this.messages = messages;
    }

    public long getHasReadMessageId() {
        return hasReadMessageId;
    }

    public void setHasReadMessageId(long hasReadMessageId) {
        this.hasReadMessageId = hasReadMessageId;
    }
}
