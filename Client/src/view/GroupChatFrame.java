package view;

import controller.GroupMessageController;
import controller.MessageController;
import controller.UserController;
import tools.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class GroupChatFrame extends JFrame {
    private JPanel panel_north;//北部区域面板
    private JLabel jbl_touxiang;//头像
    private JLabel jbl_friendname;//好友名称
    private JButton btn_exit, btn_min;//最小化和关闭按钮
    //头像下方7个功能按钮（未实现）
    private JButton btn_func1_north, btn_func2_north, btn_func3_north, btn_func4_north, btn_func5_north, btn_func6_north, btn_func7_north;



    //聊天内容显示面板
    private JTextPane panel_Msg;

    public JTextPane getPanel_Msg() {
        return panel_Msg;
    }

    private JPanel panel_south;//南部区域面板
    private JTextPane jtp_input;//消息输入区
    //消息输入区上方9个功能按钮(未实现)
    private JButton btn_func1_south, btn_func2_south, btn_func3_south,btn_func4_south, btn_func5_south, btn_func6_south, btn_func7_south, btn_func8_south, btn_func9_south;
    private JButton recorde_search;//查看消息记录按钮
    private JButton btn_send, btn_close;//消息输入区下方关闭和发送按钮

    private JPanel panel_east;//东部面板
    private CardLayout cardLayout;//卡片布局
    private JTree tree; //显示群员列表
    private List<FriendNode> groupMateList; //存储管理群员节点
    private JPanel panel_east_all;//东边显示列表时总panel
    private JPanel panel_east_north;//公告panel
    private JPanel panel_east_south;//成员panel
    private JTextPane panel_Record;//聊天记录显示面板
    private JScrollPane scrollPane_Record;

    private boolean isDragged = false;//鼠标拖拽窗口标志
    private Point frameLocation;//记录鼠标点击位置
    private long myAccount;//本人账号
    private String myName;
    private long groupId;//好友账号
    private String groupName;
    private DateFormat df;//日期解析

    public GroupChatFrame(long myAccount, String myName, long groupId, String groupName, Boolean hasReadMessage, List<String> messages){
        this.myAccount = myAccount;
        this.myName = myName;
        this.groupId = groupId;
        this.groupName = groupName;

        ManageGroupChatFrame.addGroupChatFrame(""+groupId, this);

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Parser parser = new Parser();
        //获取窗口容器
        Container c = this.getContentPane();
        //设置布局
        c.setLayout(null);

        //北部面板
        panel_north = new JPanel();
        panel_north.setBounds(0, 0, 729, 92);
        panel_north.setLayout(null);
        //添加北部面板
        c.add(panel_north);
        //左上角灰色头像
        jbl_touxiang = new JLabel();
        ImageIcon image = new ImageIcon("Client/image/dialogimage/group_head.png");
        image.setImage(image.getImage().getScaledInstance(42, 42,Image.SCALE_DEFAULT));
        jbl_touxiang.setIcon(image);
        jbl_touxiang.setBounds(10, 10, 42, 42);
        panel_north.add(jbl_touxiang);
        //头像右方正在聊天的对方姓名
        jbl_friendname = new JLabel(groupName+"("+groupId+")");
        jbl_friendname.setBounds(62, 21, 105, 20);
        panel_north.add(jbl_friendname);
        //右上角最小化按钮
        btn_min = new JButton(new ImageIcon ("Client/image/dialogimage/min.png"));
        btn_min.addActionListener(e -> setExtendedState(JFrame.ICONIFIED));
        btn_min.setBounds(668, 0, 30, 30);
        panel_north.add(btn_min);
        //右上角关闭按钮
        btn_exit = new JButton(new ImageIcon ("Client/image/dialogimage/exit.jpg"));
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageGroupChatFrame.removeGroupChatFrame("" + groupId);
                System.out.println("remove chatFrame=" + groupId);
                dispose();
            }
        });
        btn_exit.setBounds(698, 0, 30, 30);
        panel_north.add(btn_exit);

        //头像下方功能按钮
        //功能按钮1
        btn_func1_north = new JButton(new ImageIcon("Client/image/dialogimage/yuyin.png"));
        btn_func1_north.setBounds(10, 62, 36, 30);
        panel_north.add(btn_func1_north);
        //功能按钮2
        btn_func2_north = new JButton(new ImageIcon("Client/image/dialogimage/shipin.png"));
        btn_func2_north.setBounds(61, 62, 36, 30);
        panel_north.add(btn_func2_north);
        //功能按钮3
        btn_func3_north = new JButton(new ImageIcon("Client/image/dialogimage/tranfile.jpg"));
        btn_func3_north.setBounds(112, 62, 36, 30);
        panel_north.add(btn_func3_north);
        //功能按钮4
        btn_func4_north = new JButton(new ImageIcon("Client/image/dialogimage/createteam.jpg"));
        btn_func4_north.setBounds(163, 62, 36, 30);
        panel_north.add(btn_func4_north);
        //功能按钮5
        btn_func5_north = new JButton(new ImageIcon("Client/image/dialogimage/yuancheng.png"));
        btn_func5_north.setBounds(214, 62, 36, 30);
        panel_north.add(btn_func5_north);
        //功能按钮6
        btn_func6_north = new JButton(new ImageIcon("Client/image/dialogimage/sharedisplay.png"));
        btn_func6_north.setBounds(265, 62, 36, 30);
        panel_north.add(btn_func6_north);
        //功能按钮7
        btn_func7_north = new JButton(new ImageIcon("Client/image/dialogimage/yingyong.jpg"));
        btn_func7_north.setBounds(318, 62, 36, 30);
        panel_north.add(btn_func7_north);
        //设置北部面板背景色
        //panel_north.setBackground(new Color(105, 197, 239));
        panel_north.setBackground(new Color(22, 154, 228));

        //中部聊天内容显示部分
        panel_Msg = new JTextPane();
        JScrollPane scrollPane_Msg = new JScrollPane(panel_Msg);
        scrollPane_Msg.setBounds(0, 92, 446, 270);
        c.add(scrollPane_Msg);

        //南部面板
        panel_south = new JPanel();
        panel_south.setBounds(0, 370, 446, 170);
        panel_south.setLayout(null);
        //添加南部面板
        c.add(panel_south);
        //内容输入区
        jtp_input = new JTextPane();
        jtp_input.setBounds(0, 34, 446, 105);
        //添加到南部面板
        panel_south.add(jtp_input);
        //文本输入区上方功能按钮
        //功能按钮1
        btn_func1_south = new JButton(new ImageIcon("Client/image/dialogimage/word.png"));
        btn_func1_south.setBounds(10, 0, 30, 30);
        panel_south.add(btn_func1_south);
        //功能按钮2
        btn_func2_south = new JButton(new ImageIcon("Client/image/dialogimage/biaoqing.png"));
        btn_func2_south.setBounds(47, 0, 30, 30);
        panel_south.add(btn_func2_south);
        //功能按钮3
        btn_func3_south = new JButton(new ImageIcon("Client/image/dialogimage/magic.jpg"));
        btn_func3_south.setBounds(84, 0, 30, 30);
        panel_south.add(btn_func3_south);
        //功能按钮4
        btn_func4_south = new JButton(new ImageIcon("Client/image/dialogimage/zhendong.jpg"));
        btn_func4_south.setBounds(121, 0, 30, 30);
        panel_south.add(btn_func4_south);
        //功能按钮5
        btn_func5_south = new JButton(new ImageIcon("Client/image/dialogimage/yymessage.jpg"));
        btn_func5_south.setBounds(158, 0, 30, 30);
        panel_south.add(btn_func5_south);
        //功能按钮6
        btn_func6_south = new JButton(new ImageIcon("Client/image/dialogimage/dgninput.jpg"));
        btn_func6_south.setBounds(195, 0,30, 30);
        panel_south.add(btn_func6_south);
        //功能按钮7
        btn_func7_south = new JButton(new ImageIcon("Client/image/dialogimage/sendimage.jpg"));
        btn_func7_south.setBounds(232, 0, 30, 30);
        panel_south.add(btn_func7_south);
        //功能按钮8
        btn_func8_south = new JButton(new ImageIcon("Client/image/dialogimage/diange.jpg"));
        btn_func8_south.setBounds(269, 0, 30, 30);
        panel_south.add(btn_func8_south);
        //功能按钮9
        btn_func9_south = new JButton(new ImageIcon("Client/image/dialogimage/jietu.jpg"));
        btn_func9_south.setBounds(306, 0, 30, 30);
        panel_south.add(btn_func9_south);
        //查询聊天记录
        recorde_search = new JButton(new ImageIcon("Client/image/dialogimage/recorde.png"));
        recorde_search.addActionListener(e-> {
            if(!scrollPane_Record.isVisible()){
                GroupMessageController groupMessageController = new GroupMessageController();
                groupMessageController.getGroupMessagesRecord(groupId);
            }
            cardLayout.next(panel_east);
        });
        recorde_search.setBounds(350, 0, 96, 30);
        panel_south.add(recorde_search);
        //消息关闭按钮
        btn_close = new JButton(new ImageIcon("Client/image/dialogimage/close.jpg"));
        btn_close.setBounds(290, 145, 64, 24);
        btn_close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageGroupChatFrame.removeGroupChatFrame("" + groupId);
//                System.out.println(ManageChatFrame.getChatCounts() + "!");
//                System.out.println("remove chatFrame="+myAccount + friendAccount);
                dispose();
            }
        });
        panel_south.add(btn_close);
        //消息发送按钮
        btn_send = new JButton(new ImageIcon("Client/image/dialogimage/send.jpg"));
        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessage(panel_Msg, jtp_input.getText(), df.format(new Date()), myAccount, myName);
                GroupMessageController groupMessageController = new GroupMessageController();
                groupMessageController.sendGroupMessage(groupId, myAccount, jtp_input.getText(), new Date());
//                messageController.sendMeg(myAccount, friendAccount, jtp_input.getText());
                jtp_input.setText(null);
            }
        });
        btn_send.setBounds(381, 145, 64, 24);
        panel_south.add(btn_send);

        //东部面板(图片和聊天记录)
        panel_east = new JPanel();
        panel_east_all = new JPanel();
        panel_east_north = new JPanel();
        panel_east_south = new JPanel();


        //卡片布局
        cardLayout = new CardLayout(2,2);
        panel_east.setLayout(cardLayout);
        panel_east.setBounds(444, 91, 285, 418);

        //xin
        ImageIcon image2 = new ImageIcon("Client/image/dialogimage/QQshow.gif");
        image2.setImage(image2.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT));
        JLabel jlb_gonggao = new JLabel(image2);
        jlb_gonggao.setBounds(35, 5, 200, 200);
        panel_east_north.add(jlb_gonggao);
        panel_east_north.setLayout(null);
        panel_east_north.setBounds(0, 0, 270, 200);
        panel_east_all.add(panel_east_north);


        panel_east_south.setBounds(0, 210, 270, 200);
        //panel_east_south.setBackground(Color.WHITE);
        panel_east_south.setLayout(null);
        //panel_east_south.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        panel_east_all.add(panel_east_south);

        panel_east_all.setLayout(null);
        //添加东部面板
        c.add(panel_east);
        //显示聊天记录面板
        panel_Record = new JTextPane();
        panel_Record.setText("-----------------------------聊天记录--------------------------\n\n");
        scrollPane_Record = new JScrollPane(panel_Record);
        scrollPane_Record.setBounds(2, 2, 411, 410);
        //添加到东部面板
//        panel_east.add(label1);
        panel_east.add(panel_east_all);
        panel_east.add(scrollPane_Record);

        //注册鼠标事件监听器
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //鼠标释放
                isDragged = false;
                //光标恢复
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                //鼠标按下
                //获取鼠标相对窗体位置
                frameLocation = new Point(e.getX(),e.getY());
                isDragged = true;
                //光标改为移动形式
                if(e.getY() < 92)
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        //注册鼠标事件监听器
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //指定范围内点击鼠标可拖拽
                if(e.getY() < 92){
                    //如果是鼠标拖拽移动
                    if(isDragged) {
                        Point loc = new Point(getLocation().x+e.getX()-frameLocation.x,
                                getLocation().y+e.getY()-frameLocation.y);
                        //保证鼠标相对窗体位置不变,实现拖动
                        setLocation(loc);
                    }
                }
            }
        });


        if(!hasReadMessage){
            for(String message : messages){
                Vector<Object> values = parser.getReceiveGroupMessageValues(message);
                showMessage(panel_Msg, (String) values.get(3) , df.format((Date) values.get(4)), (long)values.get(1), (String) values.get(2));
            }
        }
        init(hasReadMessage); //初始化

        //设置窗口在屏幕中间
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        this.setBounds(screenWidth/3, screenHeight/3, screenWidth/2, screenHeight/2);

        this.setIconImage(new ImageIcon("Client/image/login/Q.png").getImage());//修改窗体默认图标
        this.setSize(728, 553);//设置窗体大小
        this.setUndecorated(true);//去掉自带装饰框
        this.setVisible(true);//设置窗体可见
    }

    private void init(Boolean hasReadMessage){
        initGroupMate();
        if(hasReadMessage){
            initGroupMessages();
        }
    }

    private void initGroupMate(){
        UserController userController = new UserController();
        userController.getGroupMates(groupId);
    }

    private void initGroupMessages(){
        GroupMessageController groupMessageController = new GroupMessageController();
        groupMessageController.getHasNoReadMessages(groupId, myAccount);
    }

    public void showMessage(JTextPane jtp,String content, String sendTime, long sendAccount, String sendName) {
        //设置显示格式
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrset, "仿宋");
        StyleConstants.setFontSize(attrset,14);
        Document docs = jtp.getDocument();
        String info = null;
        try {
            if(sendAccount == myAccount){//发出去的消息内容
                info = "我  ";//自己账号：紫色
                StyleConstants.setForeground(attrset, Color.MAGENTA);
                docs.insertString(docs.getLength(), info, attrset); StyleConstants.setForeground(attrset, Color.red);
                info = sendTime+":\n";//发送时间：绿色
                StyleConstants.setForeground(attrset, Color.black);
                docs.insertString(docs.getLength(), info, attrset);
                info = " "+ content +"\n";//发送内容：黑色
                StyleConstants.setFontSize(attrset,16);
                StyleConstants.setForeground(attrset, Color.green);
                docs.insertString(docs.getLength(), info, attrset);
            }else{//接收到的消息内容
                info = sendName +"("+sendAccount+")  ";//对方账号：红色
                StyleConstants.setForeground(attrset, Color.red);
                docs.insertString(docs.getLength(), info, attrset); StyleConstants.setForeground(attrset, Color.red);
                info = sendTime+":\n";//发送时间：绿色
                StyleConstants.setForeground(attrset, Color.black);
                docs.insertString(docs.getLength(), info, attrset);
                info = " "+content+"\n";//发送内容：蓝色
                StyleConstants.setFontSize(attrset,16);
                StyleConstants.setForeground(attrset, Color.blue);
                docs.insertString(docs.getLength(), info, attrset);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    public void showGroupMates(Vector<Vector<Object>> groupMates){
        groupMateList = new ArrayList<>();
        //显示群员列表
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        tree = new JTree(root);
        //tree.setToggleClickCount(1);

        for(Vector<Object> groupMate : groupMates){
            long account = (long) groupMate.get(0);
            String name = (String) groupMate.get(1);
            Boolean isOnline = (Boolean) groupMate.get(2);
            System.out.println(name + " state:"+isOnline);
            FriendNode node = new FriendNode(account, name, isOnline, new ArrayList<>());
            root.add(node);//界面列表
            groupMateList.add(node);//存储列表
        }

        tree.setRootVisible(false);
        tree.expandPath(new TreePath(root));
        tree.setOpaque(false);
        tree.setShowsRootHandles(false);
        tree.setLocation(15,10);

        tree.setCellRenderer(new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                this.setBorder(BorderFactory.createEmptyBorder(7, 5, 0, 0));
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if(!value.toString().equals("")){
                    FriendNode people = (FriendNode) node;
                    setIcon(new ImageIcon(people.getImageIcon().getImage().getScaledInstance(20, 20, JFrame.DO_NOTHING_ON_CLOSE)));
                }
                // 设置未选中节点时背景色为白色且完全透明，0表示透明,255表示正常
                setBackgroundNonSelectionColor(new Color(255, 255, 255, 0));
                // 设置选中节点时背景色为白色，透明度改为100，来区分未选中状态
                setBackgroundSelectionColor(new Color(255, 255, 255, 100));
                //
                return this;
            }
        });

        JScrollPane jsp = new JScrollPane(tree);
        //jsp.setLayout(null);
        jsp.getViewport().setOpaque(false);
        //jsp.setOpaque(false);
        jsp.setBorder(BorderFactory.createLoweredBevelBorder());
        jsp.setBackground(Color.WHITE);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jsp.setBounds(30, 40, 210, 163);
        panel_east_south.add(jsp);
    }

    public void showHasNotReadGroupMessages(Vector<Vector<Object>> messages){
        for(Vector message : messages){
            showMessage(panel_Msg, (String) message.get(2), (String)message.get(3), (long)message.get(0), (String) message.get(1));
        }
    }

    public void showGroupMessagesRecord(Vector<Vector<Object>> groupMessages){
        panel_Record.setText("------------------------聊天记录---------------------\n\n");
        for(Vector<Object> groupMessage : groupMessages){
            showMessage(panel_Record, (String) groupMessage.get(2), (String) groupMessage.get(3), (long) groupMessage.get(0), (String) groupMessage.get(1));
        }
    }
}
