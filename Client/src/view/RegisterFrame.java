package view;

import net.Client;
import tools.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterFrame extends JFrame {
    private JLabel jlb_north;//北部背景图片标签
    private JButton btn_exit,btn_min;//右上角最小化和关闭按钮
    private JLabel jlb_account; //账号输入提示
    private JTextField qqAccount;//账号输入框
    private JLabel jlb_name;//昵称输入提示
    private JTextField qqName;//昵称输入框
    private JLabel jlb_pwd;//密码输入提示
    private JPasswordField qqPwd;//密码输入框
    private JButton btn_register;//南部注册按钮
    private JButton btn_return;//南部返回按钮
    private boolean isDragged = false;//记录鼠标是否是拖拽移动
    private Point frame_temp;//鼠标当前相对窗体的位置坐标
    private Point frame_loc;//窗体的位置坐标

    public RegisterFrame(){
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
        //处理账号输入提示
        jlb_account = new JLabel("账号：",JLabel.CENTER);
        jlb_account.setFont(new Font("宋体", 1, 16));
        jlb_account.setBounds(55,195, 90, 30);
        c.add(jlb_account);
        //处理账号输入框
        qqAccount = new JTextField();
        qqAccount.setBounds(120,195,194,30);
        c.add(qqAccount);
        c.add(btn_exit);
        //处理昵称输入提示
        jlb_name = new JLabel("昵称：",JLabel.CENTER);
        jlb_name.setFont(new Font("宋体", 1, 16));
        jlb_name.setBounds(55,240, 90, 30);
        c.add(jlb_name);
        //处理昵称输入框
        qqName = new JTextField();
        qqName.setBounds(120,240,194,30);
        c.add(qqName);
        //处理密码输入提示
        jlb_pwd = new JLabel("密码：",JLabel.CENTER);
        jlb_pwd.setFont(new Font("宋体", 1, 16));
        jlb_pwd.setBounds(55,285, 90, 30);
        c.add(jlb_pwd);
        //处理密码输入框
        qqPwd = new JPasswordField();
        qqPwd.setBounds(120,285,194,30);
        c.add(qqPwd);

        //处理南部注册按钮
        btn_register = new JButton("注册");
        btn_register.setBounds(120,330,120,30);
        btn_register.setBackground(new Color(9,163,220));
        btn_register.setForeground(Color.WHITE);
        btn_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long account = Long.parseLong(qqAccount.getText());
                String name = qqName.getText();
                String password = new String(qqPwd.getPassword());
                Client client = new Client();
                client.init();
                Boolean isRegisterSucceed = client.register(new Protocol().registerMsg(account, name, password));
                if(isRegisterSucceed){
                    System.out.println("注册成功");
                    new LoginFrame();
                    dispose();
                }else {
                    System.out.println("注册失败");
                }

            }
        });
        c.add(btn_register);

        //处理南部返回按钮
        btn_return = new JButton("返回");
        btn_return.setBounds(254,330,60,30);
        btn_return.setBackground(new Color(179,179,179));
        btn_return.setForeground(Color.WHITE);
        btn_return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame();
                dispose();
            }
        });
        c.add(btn_return);

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
        this.setSize(430,375);//设置窗体大小
        this.setUndecorated(true);//去掉自带装饰框
        this.setVisible(true);//设置窗体可见
    }
}
