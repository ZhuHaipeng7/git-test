package view;

import net.Client;
import tools.Parser;
import tools.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JLabel jlb_north;//北部背景图片标签
    private JButton btn_exit,btn_min;//右上角最小化和关闭按钮
    private JLabel jlb_photo;//中部账号密码框左边企鹅图片标签
    private JTextField qqNum;//账号输入框
    private JPasswordField qqPwd;//密码输入框
    private JLabel jlb_register;//账号输入框后的"注册账号"
    private JLabel after_qqPwd;//密码输入框后的"忘记密码"
    private JCheckBox remPwd;//"记住密码"单选框
    private JCheckBox autoLog;//"自动登录"单选框
    private JButton btn_login;//南部登录按钮
    private boolean isDragged = false;//记录鼠标是否是拖拽移动
    private Point frame_temp;//鼠标当前相对窗体的位置坐标
    private Point frame_loc;//窗体的位置坐标

    //public Client client = new Client();
    private Protocol protocol = new Protocol();

    public LoginFrame(){
        //获取此窗口容器
        Container c = this.getContentPane();
        //设置布局
        c.setLayout(null);

        //处理北部背景图片标签
        jlb_north = new JLabel(new ImageIcon("Client/image/login/login.jpg"));
        jlb_north.setBounds(0,0,430,182);
        c.add(jlb_north);
        //处理右上角最小化和关闭按钮
        btn_min = new JButton(new ImageIcon("Client/image/login/min.jpg"));
        btn_min.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //注册监听器,点击实现窗口最小化
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        btn_min.setBounds(373, 0, 28, 29);
        c.add(btn_min);

        btn_exit = new JButton(new ImageIcon("Client/image/login/exit.png"));
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //注册监听器,点击实现窗口关闭
                System.exit(0);
            }
        });
        btn_exit.setBounds(401, 0, 28, 29);
        c.add(btn_exit);

        //处理中部账号密码框左边头像标签
        jlb_photo = new JLabel();
        ImageIcon image = new ImageIcon("Client/image/login/touxiang.jpg");
        image.setImage(image.getImage().getScaledInstance(82, 78,Image.SCALE_DEFAULT));
//        BufferedImage bi = new BufferedImage(image.getImage().getWidth(null), image.getImage().getHeight(null), BufferedImage.TYPE_INT_ARGB);
//        Graphics g = bi.createGraphics();
//        g.drawImage(image.getImage(), 0, 0, 82, 78, null);

        jlb_photo.setIcon(image);
        jlb_photo.setBounds(20, 192, 82, 78);
        c.add(jlb_photo);
        //处理账号输入框
        qqNum = new JTextField();
        qqNum.setBounds(120,195,194,30);
        c.add(qqNum);
        //处理密码输入框
        qqPwd = new JPasswordField();
        qqPwd.setBounds(120,240,194,30);
        c.add(qqPwd);

        //处理密码输入框后的"忘记密码"
        after_qqPwd = new JLabel("忘记密码");
        after_qqPwd.setFont(new java.awt.Font("宋体", 0, 10));
        //after_qqPwd.setForeground(Color.blue);
        after_qqPwd.setBounds(271,277,78,15);

        c.add(after_qqPwd);
        //处理"记住密码"单选框
        remPwd = new JCheckBox("记住密码");
        remPwd.setFont(new java.awt.Font("宋体", 0, 10));
        remPwd.setBounds(118,277,65,15);
        c.add(remPwd);
        //处理"自动登录"单选框
        autoLog = new JCheckBox("自动登录");
        autoLog.setFont(new java.awt.Font("宋体", 0, 10));
        autoLog.setBounds(191,277,65,15);
        c.add(autoLog);
        //处理南部登录按钮
        btn_login = new JButton(new ImageIcon("Client/image/login/loginbutton.png"));
        btn_login.setBounds(120,299,194,30);
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Parser parser = new Parser();
                long account = Long.parseLong(qqNum.getText().trim());//获取输入账号
                String pwd = new String(qqPwd.getPassword());//获取密码
                //接收检验结果
                Client client = new Client();
                client.init();
                String reply = client.login(protocol.loginMeg(account, pwd));
                if(reply != null){
                    new FriendListFrame(account, parser.loginGetName(reply), reply);
                    dispose();
                }

            }
        });
        c.add(btn_login);

        //处理账号输入框后的"注册账号"
        jlb_register = new JLabel("注册账号");
        jlb_register.setForeground(Color.gray);
        jlb_register.setBounds(5,320,78,30);
        jlb_register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jlb_register.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e){
                jlb_register.setForeground(Color.gray);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterFrame();
                dispose();
            }
        });
        c.add(jlb_register);

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
                frame_temp = new Point(e.getX(),e.getY());
                isDragged = true;
                //光标改变为移动形式
                if(e.getY() < 182)
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        //注册鼠标事件监听器
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //指定范围内点击鼠标可拖拽
                if(e.getY() < 182){
                    //如果是鼠标拖拽移动
                    if(isDragged) {
                        frame_loc = new Point(getLocation().x+e.getX()-frame_temp.x,
                                getLocation().y+e.getY()-frame_temp.y);
                        //保证鼠标相对窗体位置不变,实现拖动
                        setLocation(frame_loc);
                    }
                }
            }
        });

        //设置窗口在屏幕中间
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        this.setBounds(screenWidth/3, screenHeight/3, screenWidth/2, screenHeight/2);

        this.setIconImage(new ImageIcon("Client/image/login/Q.png").getImage());//修改窗体默认图标
        this.setSize(430,345);//设置窗体大小
        this.setUndecorated(true);//去掉自带装饰框
        this.setVisible(true);//设置窗体可见
    }
}
