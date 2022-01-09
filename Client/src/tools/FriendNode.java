package tools;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FriendNode extends DefaultMutableTreeNode {

    private String kind;// 好友分类
    private long account;// 好友账号
    private String name; // 好友昵称
    private String says; // 好友个性签名
    private int head; // 好友头像(不同的数字对应不同的图片）
    private List<String> messages; //未查看的消息

    private boolean onLineState = false;// 在线状态
    private boolean hasNoReadMessage = false; //是否又未读消息

    public FriendNode(String kind, long account, String name, String says, int head) {
        super(name + says);//文本框放置昵称和个性签名
        this.kind = kind;
        this.account = account;
        this.name = name;
        this.says = says;
        this.head = head;
    }

    public FriendNode(long account, String name, Boolean onLineState, List<String> messages) {
        super(name + "(" + account + ")");
        this.account = account;
        this.name = name;
        this.onLineState = onLineState;
        this.messages = messages;
    }

    public FriendNode(long groupId, String name, Boolean hasNoReadMessage) {
        super(name + "(" + groupId + ")");
        this.account = groupId;
        this.name = name;
        this.hasNoReadMessage = hasNoReadMessage;
        this.messages = new ArrayList<>();
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void clearMessages(){
        this.messages.clear();
    }

    public void addMessage(String msg){
        this.messages.add(msg);
    }

    public String getKind() {
        return kind;
    }

    public long getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getSays() {
        return says;
    }

    public boolean isHasNoReadMessage() {
        return hasNoReadMessage;
    }

    public void setHasNoReadMessage(boolean hasNoReadMessage) {
        this.hasNoReadMessage = hasNoReadMessage;
    }

    public ImageIcon getImageIcon() {

        if (onLineState) {
            return new ImageIcon("Client/image/friendlist/touxiang.jpg");
        } else {
            return getGrayImage(new ImageIcon("Client/image/friendlist/touxiang.jpg"));
        }
    }

    public boolean getOnLineState() {
        return onLineState;
    }

    //图片灰度化处理
    public ImageIcon getGrayImage(ImageIcon icon){
        int w=icon.getIconWidth();
        int h=icon.getIconHeight();
        BufferedImage buff=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics g=buff.getGraphics();
        g.drawImage(icon.getImage(),0,0,null);
        for (int i=0;i<w;i++){
            for(int j=0;j<h;j++){
                int red,green,blue;
                int pixel=buff.getRGB(i,j);
                red=(pixel>>16)&0xFF;
                green=(pixel>>8)&0xFF;
                blue=(pixel>>0)&0xFF;
                int sum=(red+green+blue)/3;
                g.setColor(new Color(sum,sum,sum));
                g.fillRect(i,j,1,1);
            }
        }
        return new ImageIcon(buff);
    }

    // 更改在线状态
    public void setState(boolean onLineState) {
        this.onLineState = onLineState;
    }

}
