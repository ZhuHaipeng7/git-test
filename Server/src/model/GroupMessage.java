package model;

import java.util.Date;

public class GroupMessage {
    private long sendAccount;
    private long GroupId;
    private String content;
    private Date sendDate;

    public long getSendAccount() {
        return sendAccount;
    }

    public void setSendAccount(long sendAccount) {
        this.sendAccount = sendAccount;
    }

    public long getGroupId() {
        return GroupId;
    }

    public void setGroupId(long groupId) {
        GroupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
