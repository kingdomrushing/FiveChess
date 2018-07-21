package com.fivechess.view;

import com.fivechess.model.*;

import javax.swing.*;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**人机对战页面（棋盘为人机和人人共享）
 * 面板：电脑信息，棋盘，玩家信息，棋子图片，计时器
 * @author admin
 */
public class PCMainBoard extends MainBoard{
    private PCChessBoard cb;//棋盘
    private JButton start;//开始游戏按钮
    private JButton back;//悔棋按钮
    private JButton exit;//返回按钮
    private JLabel people;//玩家标签
    private JLabel computer;//电脑标签
    private JLabel plv;//玩家等级标签
    private JLabel clv;//电脑等级标签
    private JLabel timecount;//计时器标签
    //双方状态
    private JLabel situation1;//玩家状态标签
    private JLabel situation2;//电脑状态标签
	private JLabel jLabel1;
	private Image   image1;//白棋图片
	private ImageIcon  imageIcon1;//
	private JLabel jLabel2;//
	private Image   image2;//黑棋图片
	private ImageIcon  imageIcon2;//
    private int level;
    private Logger logger = Logger.getLogger("游戏");
    public int getLevel(){return level;}//返回你要挑战的等级
    public JButton getstart(){return start;}//返回start
    public JButton getback(){return back;}//返回back
    public JLabel getSituation1(){return situation1;}//返回玩家状态
    public JLabel getSituation2(){return situation2;}//返回电脑状态

    //显示对应等级的人机对战界面
    public PCMainBoard(int level)
    {
        init();
        this.level=level;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void init()
    {
        cb=new PCChessBoard(this);
        cb.setClickable(CAN_NOT_CLICK_INFO);//设置棋盘是否能被点击
        cb.setBounds(210, 40, 570, 585);
        cb.setVisible(true);
        start=new JButton("开始游戏");//设置名称，下同
        start.setBounds(780,240, 200, 50);//设置起始位置，宽度和高度，下同
        start.setBackground(new Color(50,205,50));//设置颜色，下同
        start.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同
        start.addActionListener(this);
        back=new JButton("悔  棋");
        back.setBounds(780, 295, 200, 50);
        back.setBackground(new Color(85,107,47));
        back.setFont(new Font("宋体", Font.BOLD, 20));
        back.addActionListener(this);
        exit=new JButton("返  回");
        exit.setBackground(new Color(218,165,32));
        exit.setBounds(780,350,200,50);
        exit.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同
        exit.addActionListener(this);
        people=new JLabel("    玩 家:");
        people.setOpaque(true); 
        people.setBackground(new Color(82,109,165));
        people.setBounds(10,410,200,50);
        people.setFont(new Font("宋体", Font.BOLD, 20));
        computer=new JLabel("    电 脑:");
        computer.setOpaque(true); 
        computer.setBackground(new Color(82,109,165));
        computer.setBounds(10,75,200,50);
        computer.setFont(new Font("宋体", Font.BOLD, 20));
        timecount=new JLabel("    计时器:");
        timecount.setBounds(320,1,200,50);
        timecount.setFont(new Font("宋体", Font.BOLD, 30));
        plv=new JLabel("    等 级:LV.1");
        plv.setOpaque(true); 
        plv.setBackground(new Color(82,109,165));
        plv.setBounds(10,130,200,50);
        plv.setFont(new Font("宋体", Font.BOLD, 20));
        clv=new JLabel("    等 级:LV.1");
        clv.setOpaque(true); 
        clv.setBackground(new Color(82,109,165));
        clv.setBounds(10,465,200,50);
        clv.setFont(new Font("宋体", Font.BOLD, 20));
        situation1=new JLabel("    状态:");
        situation1.setOpaque(true); 
        situation1.setBackground(new Color(82,109,165));
        situation1.setBounds(10,185,200,50);
        situation1.setFont(new Font("宋体", Font.BOLD, 20));
        situation2=new JLabel("    状态:");
        situation2.setOpaque(true); 
        situation2.setBackground(new Color(82,109,165));
        situation2.setBounds(10,520,200,50);
        situation2.setFont(new Font("宋体", Font.BOLD, 20));
 	    image1 = Toolkit.getDefaultToolkit().getImage("images/black.png");//添加黑棋图片
 	    jLabel1 = new JLabel();
 	    add(jLabel1); 
		imageIcon1 = new ImageIcon(image1);
		jLabel1.setIcon(imageIcon1);
		
		setVisible(true);
		jLabel1.setBounds(130,75,200,50);
 	    image2= Toolkit.getDefaultToolkit().getImage("images/white.png");//添加白棋图片
 	    jLabel2 = new JLabel();
		imageIcon2 = new ImageIcon(image2);
		jLabel2.setIcon(imageIcon2);
		add(jLabel2); 
		setVisible(true);
		jLabel2.setBounds(130,410,200,50);
        add(cb);
        add(back);
        add(start);
        add(exit);
        add(people);
        add(computer);
        add(timecount);
        add(plv);
        add(clv);
        add(situation1);
        add(situation2);
    }
    
    /**
     * 点击事件
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start)
        {
            start.setEnabled(false);
            start.setText("正在游戏");
            logger.info("开始游戏");
            start.setFont(new Font("宋体", Font.BOLD, 20));
            //设置玩家状态
            situation1.setText("    状态:等待...");
            situation2.setText("    状态:下棋...");
            cb.setClickable(MainBoard.CAN_CLICK_INFO);
            //加载计时线程
            timer=new TimeThread(label_timeCount);
            timer.start();
            //设置结果为1，游戏继续
            cb.setResult(1);
        }
        //点击悔棋后的操作
        else if(e.getSource()==back)
        {
        	cb.backstep();
        	logger.info("玩家选择悔棋");
        }
        //点击返回后的操作，返回主菜单
        else if(e.getSource()==exit)
        {
            dispose();
            new SelectMenu();
            logger.info("玩家选择返回主菜单");
        }
    }

}