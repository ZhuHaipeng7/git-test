package view;


import controller.GroupController;
import controller.UserController;
import tools.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FriendListFrame extends JFrame {
    private Container c;//本窗口面板
    private Point tmp,loc;//记录位置
    private boolean isDragged = false;//是否拖拽
    private long myAccount;//本人qq
    private String myName;//本人昵称
    private JTree contacts_tree;//树组件显示好友列表
    private JTree group_tree;//树组件显示群聊列表
    private ArrayList<FriendNode> friendNodes;
    private ArrayList<FriendNode> groupNodes;
    private UserController userController;
    private GroupController groupController;
    private DefaultMutableTreeNode groupList;

    public FriendListFrame(long myAccount, String myName, String msg){
        this.myAccount = myAccount;
        this.myName = myName;

        userController = new UserController();
        groupController = new GroupController();
        ManageFriendListFrame.addFriendListFrame(myAccount,this);
        //获取本窗体容器
        c =  this.getContentPane();
        //设置窗体大小
        this.setSize(274,600);
        //设置布局
        c.setLayout(null);
        //右上角最小化按钮
        JButton btn_min = new JButton(new ImageIcon("Client/image/friendlist/fmin.png"));
        btn_min.setBounds(217, 0, 28, 28);
        btn_min.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //窗体最小化
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        c.add(btn_min);
        //右上角退出按钮
        JButton btn_exit = new JButton(new ImageIcon("Client/image/friendlist/fexit.png"));
        btn_exit.setBounds(245, 0, 28, 28);
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userController.clientExit(myAccount);
                System.exit(0);
            }
        });
        c.add(btn_exit);
        //左上角qq标签
        JLabel jbl_leftTop = new JLabel(new ImageIcon("Client/image/friendlist/biaoti.png"));
        jbl_leftTop.setBounds(0, 0, 44, 21);
        c.add(jbl_leftTop);
        //qq头像
        JLabel jbl_photo = new JLabel();
        ImageIcon image = new ImageIcon("Client/image/login/touxiang.jpg");
        image.setImage(image.getImage().getScaledInstance(82, 78,Image.SCALE_DEFAULT));
        jbl_photo.setIcon(image);
        jbl_photo.setBounds(15, 28, 70, 70);
        c.add(jbl_photo);
        //qq昵称
        JLabel jbl_qqName = new JLabel(myName+"("+myAccount+")");
        jbl_qqName.setBounds(109, 32, 68, 21);
        c.add(jbl_qqName);
        //个性签名
        JLabel jbl_personalSign = new JLabel("个性签名");
        jbl_personalSign.setBounds(110, 63, 167, 21);
        c.add(jbl_personalSign);
        //在线状态选择列表
        String[] status = {"在线","隐身","离线"};
        JComboBox<String> online_status = new JComboBox<>(status);
        online_status.setSelectedIndex(0);
        online_status.setBounds(195, 32, 63, 21);
        c.add(online_status);
        //头像下方七个功能按钮
        //按钮1
        JButton btn_h1 = new JButton(new ImageIcon("Client/image/friendlist/tubiao1.png"));
        btn_h1.setBounds(0, 111, 20, 23);
        c.add(btn_h1);
        //按钮2
        JButton btn_h2 = new JButton(new ImageIcon("Client/image/friendlist/tubiao2.png"));
        btn_h2.setBounds(28, 111, 20, 23);
        c.add(btn_h2);
        //按钮3
        JButton btn_h3 = new JButton(new ImageIcon("Client/image/friendlist/tubiao3.png"));
        btn_h3.setBounds(58, 111, 20, 23);
        c.add(btn_h3);
        //按钮4
        JButton btn_h4 = new JButton(new ImageIcon("Client/image/friendlist/tubiao4.png"));
        btn_h4.setBounds(88, 111, 20, 23);
        c.add(btn_h4);
        //按钮5
        JButton btn_h5 = new JButton(new ImageIcon("Client/image/friendlist/tubiao5.png"));
        btn_h5.setBounds(118, 111, 20, 23);
        c.add(btn_h5);
        //按钮6
        JButton btn_h6 = new JButton(new ImageIcon("Client/image/friendlist/tubiao6.png"));
        btn_h6.setBounds(148, 111, 20, 23);
        c.add(btn_h6);
        //按钮7
        JButton btn_h7 = new JButton(new ImageIcon("Client/image/friendlist/tubiao7.png"));
        btn_h7.setBounds(178, 111, 20, 23);
        c.add(btn_h7);
        //搜索框
        JTextField jtf_search = new JTextField();
        jtf_search.setBounds(0, 134, 247, 23);
        c.add(jtf_search);
        //搜索按钮
        JButton btn_search = new JButton(new ImageIcon("Client/image/friendlist/search.png"));
        btn_search.setBounds(247, 134, 30, 23);
        c.add(btn_search);

        //上半部分背景图
        JLabel jbl_background = new JLabel(new ImageIcon("Client/image/friendlist/beijing.png"));
        jbl_background.setBounds(0, 0, 277, 156);
        c.add(jbl_background);
        //底部8个功能按钮
        //按钮1
        JButton btn_l1 = new JButton(new ImageIcon("Client/image/friendlist/mainmenue.png"));
        btn_l1.setBounds(0, 577, 30, 23);
        c.add(btn_l1);

        //按钮2
        ImageIcon image_addFriend = new ImageIcon("Client/image/friendlist/add_friend.png");
        image_addFriend.setImage(image_addFriend.getImage().getScaledInstance(23, 18,Image.SCALE_DEFAULT));
        JButton btn_l2 = new JButton(image_addFriend);
        btn_l2.setBounds(30, 577, 30, 23);
        c.add(btn_l2);
        //按钮3
        JButton btn_l3 = new JButton(new ImageIcon("Client/image/friendlist/messagemanage.png"));
        btn_l3.setBounds(60, 577, 30, 23);
        c.add(btn_l3);
        //按钮4
        JButton btn_l4 = new JButton(new ImageIcon("Client/image/friendlist/filehleper.png"));
        btn_l4.setBounds(90, 577, 30, 23);
        c.add(btn_l4);
        //按钮5
        JButton btn_l5 = new JButton(new ImageIcon("Client/image/friendlist/shoucang.png"));
        btn_l5.setBounds(120, 577, 30, 23);
        c.add(btn_l5);
        //按钮6
        JButton btn_l6 = new JButton(new ImageIcon("Client/image/friendlist/tubiao8.png"));
        btn_l6.setBounds(150, 577, 30, 23);
        c.add(btn_l6);
        //按钮7
        JButton btn_l7 = new JButton(new ImageIcon("Client/image/friendlist/tubiao9.png"));
        //btn_l7.addActionListener(this);
        btn_l7.setBounds(180, 577, 30, 23);
        c.add(btn_l7);
        //按钮8
        JButton btn_l8 = new JButton(new ImageIcon("Client/image/friendlist/apl.png"));
        //btn_l8.addActionListener(this);
        btn_l8.setBounds(210, 577, 64, 23);
        c.add(btn_l8);

        //显示好友列表
        initList(this, msg);

        //设置窗口在屏幕中间
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        this.setBounds(screenWidth*2/3, screenHeight/6, 274, 600);

        //去除其定义装饰框
        this.setUndecorated(true);
        //设置窗体可见
        this.setVisible(true);
        //添加鼠标监听事件
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
                //拖拽结束图标恢复
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                //限定范围内可拖拽
                if(e.getY()<30)
                {
                    //获取鼠标按下位置
                    tmp = new Point(e.getX(), e.getY());
                    isDragged = true;
                    //拖拽时更改鼠标图标
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragged) {
                    //设置鼠标与窗体相对位置不变
                    loc = new Point(getLocation().x + e.getX() - tmp.x, getLocation().y + e.getY() - tmp.y);
                    setLocation(loc);
                }
            }

        });
    }

    public void initList(JFrame f, String msg) {
        friendNodes = new ArrayList<>();
        Parser parser = new Parser();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultMutableTreeNode friendList = new DefaultMutableTreeNode("我的好友");
        //创建树
        contacts_tree = new JTree(root);
        // 设置为点击一次展开
        contacts_tree.setToggleClickCount(1);
        // 将节点添加到根节点
        root.add(friendList);

        Vector<Vector<Object>> friends = parser.getFriends(msg);
        for (Vector<Object> friend : friends) {

            FriendNode node = new FriendNode((long) friend.get(0), (String) friend.get(1), (Boolean) friend.get(2), (ArrayList<String>) friend.get(3));
            friendNodes.add(node);//存放的arraylist
            friendList.add(node);//界面的列表

        }


        // 隐藏根节点
        contacts_tree.setRootVisible(false);
        // 展开树(在根节点隐藏时,能看见子节点)
        contacts_tree.expandPath(new TreePath(root));
        // 设置透明
        contacts_tree.setOpaque(false);
        // 隐藏根柄
        contacts_tree.setShowsRootHandles(false);
        //隐藏连接线
        contacts_tree.putClientProperty("JTree.lineStyle","None");

        ImageIcon icon1 = new ImageIcon("Client/image/friendlist/sanjiao_right_s.png");
        ImageIcon icon2 = new ImageIcon("Client/image/friendlist/sanjiao_down_s.png");
        // 收起和展开图片设置为三角形
        icon1.setImage(icon1.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        icon2.setImage(icon2.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT));
        contacts_tree.setCellRenderer(new DefaultTreeCellRenderer() {
            // 重写该方法
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                this.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
                //节点为展开时显示的图片
                if (!expanded) {
                    setIcon(icon1);
                } else {
                    setIcon(icon2);
                }

                String str = value.toString();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                //DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if (!value.toString().equals("我的好友") && !value.toString().equals("")) {
                    //System.out.println("nodeValue" + value.toString());
                    FriendNode people = (FriendNode) node;
                    setIcon(new ImageIcon(people.getImageIcon().getImage().getScaledInstance(20, 20, JFrame.DO_NOTHING_ON_CLOSE)));
                    if (people.getMessages().size() != 0) {
                        setForeground(Color.RED);
                    }
                }


                // 设置未选中节点时背景色为白色且完全透明，0表示透明,255表示正常
                setBackgroundNonSelectionColor(new Color(255, 255, 255, 0));
                // 设置选中节点时背景色为白色，透明度改为100，来区分未选中状态
                setBackgroundSelectionColor(new Color(255, 255, 255, 100));
                //
                return this;
            }
        });

        // 使节点能响应相应操作
        contacts_tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object node = contacts_tree.getLastSelectedPathComponent();
                String str = node.toString();

                if (!str.equals("我的好友") && e.getClickCount() == 2) {
                    //点击两次好友，弹出对话框
                    FriendNode friendNode = (FriendNode) contacts_tree.getLastSelectedPathComponent();
                    String[] nameAndAccount = parser.getAccountAndName(str);
                    if (ManageChatFrame.getChatFrame(myAccount + nameAndAccount[1]) == null) {
                        //System.out.println("add "+ myAccount + nameAndAccount[1]);
                        ManageChatFrame.addChatFrame(myAccount + nameAndAccount[1], new ChatFrame(myAccount, myName, Long.parseLong(nameAndAccount[1]), nameAndAccount[0], friendNode.getMessages()));
                        friendNode.clearMessages();
                    }
                }
            }

        });

        //群聊列表初始化
        groupNodes = new ArrayList<>();
        DefaultMutableTreeNode groupRoot = new DefaultMutableTreeNode();
        groupList = new DefaultMutableTreeNode("我的群组");
        //创建树
        group_tree = new JTree(groupRoot);
        //设置为点击一次展开
        group_tree.setToggleClickCount(1);
        // 将节点添加到根节点
        groupRoot.add(groupList);

        //获取群聊数据
        groupController.getGroupsInfo(myAccount);


        // 隐藏根节点
        group_tree.setRootVisible(false);
        // 展开树(在根节点隐藏时,能看见子节点)
        group_tree.expandPath(new TreePath(groupRoot));
        // 设置透明
        group_tree.setOpaque(false);
        // 隐藏根柄
        group_tree.setShowsRootHandles(false);
        //隐藏连接线
        group_tree.putClientProperty("JTree.lineStyle","None");

        group_tree.setCellRenderer(new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                this.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
                //节点为展开时显示的图片
                if (!expanded) {
                    setIcon(icon1);
                } else {
                    setIcon(icon2);
                }
                //String str = value.toString();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                //DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if (!value.toString().equals("我的群组") && !value.toString().equals("")) {
                    //System.out.println("nodeValue" + value.toString());
                    FriendNode group = (FriendNode) node;
                    setIcon(new ImageIcon(new ImageIcon("Client/image/friendlist/group_head.png").getImage().getScaledInstance(20, 20, JFrame.DO_NOTHING_ON_CLOSE)));
                    if (group.isHasNoReadMessage() || group.getMessages().size() != 0) {
                        setForeground(Color.RED);
                    }
                }

                // 设置未选中节点时背景色为白色且完全透明，0表示透明,255表示正常
                setBackgroundNonSelectionColor(new Color(255, 255, 255, 0));
                // 设置选中节点时背景色为白色，透明度改为100，来区分未选中状态
                setBackgroundSelectionColor(new Color(255, 255, 255, 100));
                //
                return this;
            }
        });

        // 使节点能响应相应操作
        group_tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object node = group_tree.getLastSelectedPathComponent();
                String str = node.toString();

                if (!str.equals("我的群组") && e.getClickCount() == 2) {
                    //点击两次群组，弹出对话框
                    FriendNode groupNode = (FriendNode) group_tree.getLastSelectedPathComponent();
                    if (ManageGroupChatFrame.getGroupChatFrame(""+groupNode.getAccount()) == null) {
                        //System.out.println("add "+ myAccount + nameAndAccount[1]);
                        new GroupChatFrame(myAccount, myName, groupNode.getAccount(), groupNode.getName(), groupNode.isHasNoReadMessage(), groupNode.getMessages());
                        groupNode.setHasNoReadMessage(false);
                        groupNode.clearMessages();
                    }
                }
            }

        });

        // 设置选项卡透明（需放置在创建之前）
        UIManager.put("TabbedPane.contentOpaque", false);
        // 创建选项卡
        JTabbedPane tab = new JTabbedPane();

        // 将创建并进行个性化设置的树添加到滚动面板
        JScrollPane jsp = new JScrollPane(contacts_tree);
        JScrollPane jsp_group = new JScrollPane(group_tree);
        // 将滚动面板设置透明
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
        jsp.setName("联系人");

        jsp_group.getViewport().setOpaque(false);
        jsp_group.setOpaque(false);
        jsp_group.setName("群组");
        // 显示滚动条
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsp_group.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // 最后将滚动面板添加到选项卡中
        tab.add(jsp);
        tab.add(jsp_group);

        tab.setBounds(0, 157, 274, 422);
        c.add(tab);
    }

    public void receiveMsg(long fromAccount, String msg){
        for(FriendNode node : friendNodes){
            if(fromAccount == node.getAccount()){
                node.addMessage(msg);
                break;
            }
        }
        contacts_tree.updateUI();
    }

    public void receiveGroupMsg(long groupId, String msg){
        System.out.println("groupId:"+groupId);
        for(FriendNode node : groupNodes){
            if(groupId == node.getAccount()){
                System.out.println("添加了");
                node.addMessage(msg);
                break;
            }
        }
        group_tree.updateUI();
    }

    public void receiveNotify(long sendAccount, Boolean state){
        for(FriendNode node : friendNodes){
            if(sendAccount == node.getAccount()){
                node.setState(state);
                break;
            }
        }
        contacts_tree.updateUI();
    }

    public void receiveGroupsInfo(Vector<Vector<Object>> groups){
        for (Vector<Object> group : groups) {
            FriendNode node = new FriendNode((long) group.get(0), (String) group.get(1), (Boolean) group.get(2));
            groupNodes.add(node);//存放的arraylist
            groupList.add(node);//界面的列表
        }
    }
}
