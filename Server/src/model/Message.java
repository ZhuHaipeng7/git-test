package model;

import java.util.Date;

public class Message {
    private long sendAccount;
    private long acceptAccount;
    private String content;
    private Date sendTime;
    private Boolean state;

    public Message(){

    }

    public Message(long sendAccount, long acceptAccount, String content, Date sendTime) {
        this.sendAccount = sendAccount;
        this.acceptAccount = acceptAccount;
        this.content = content;
        this.sendTime = sendTime;
    }

    public Message(long sendAccount, long acceptAccount, String content, Date sendTime, Boolean state) {
        this.sendAccount = sendAccount;
        this.acceptAccount = acceptAccount;
        this.content = content;
        this.sendTime = sendTime;
        this.state = state;
    }

    public long getSendAccount() {
        return sendAccount;
    }

    public void setSendAccount(long sendAccount) {
        this.sendAccount = sendAccount;
    }

    public long getAcceptAccount() {
        return acceptAccount;
    }

    public void setAcceptAccount(long acceptAccount) {
        this.acceptAccount = acceptAccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }


}
