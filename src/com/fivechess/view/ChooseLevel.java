package com.fivechess.view;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

/**
 * 人机对战界面
 * 初级：电脑水平低级（随机算法）
 * 中级：电脑水平中级（简单算法）
 * 高级：电脑水平高级（复杂算法）
 * 大师：电脑水平大师级（机器学习或深度学习）
 * @author admin
 *
 */
public class ChooseLevel extends JFrame implements MouseListener{
	public static final int PRIMARY=1; //初级 
    public static final int MEDIUM=2;  //中级
    public static final int SENIOR=3;  //高级
    public static final int SUPER=4;   //大师
	public ChooseLevel()
	{
		setVisible(true);
		setLayout(null); //取消原来布局
		setBounds(580,185,290,420);
		setResizable(false);
		paintBg(); //页面
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(this);
	}
	
	/**
	 * 添加背景图片
	 */
	private void paintBg() {
		// TODO Auto-generated method stub
		ImageIcon image = new ImageIcon("images/level.jpg");
		image.setImage(image.getImage().getScaledInstance(290, 420, Image.SCALE_DEFAULT));
        JLabel la = new JLabel(image);
        la.setBounds(0, 0, this.getWidth(), this.getHeight());//添加图片，设置图片大小为窗口的大小。
        this.getLayeredPane().add(la, new Integer(Integer.MAX_VALUE)); //将JLabel加入到面板容器的最上层
	}
	
	/**
	 * 点击页面触发事件
	 * @param e 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Logger logger = Logger.getLogger("人机对战菜单");
		//获取点击坐标
		int x=e.getX();
		int y=e.getY();
		System.out.println(x+" "+y);
		if(x>=68 && x<=227 && y>=130 && y<=160)
		{
			// 加载初级难度界面
			dispose();
			logger.info("用户选择电脑水平为初级");
			new PCMainBoard(PRIMARY);
		}
		else if(x>=68 && x<=227 && y>=185 && y<=226)
		{
			//加载中级难度页面
			dispose();
			logger.info("用户选择电脑水平为中级");
			new PCMainBoard(MEDIUM);
		}
		else if(x>=68 && x<=227 && y>=250 && y<=293)
		{
			//加载高级难度界面
			dispose();
			logger.info("用户选择电脑水平为高级");
			new PCMainBoard(SENIOR);
		}
		else if(x>=68 && x<=227 && y>=411 && y<=430)
		{
			//加载更高难度界面
			dispose();
			logger.info("用户选择电脑水平为大师级");
			new PCMainBoard(SUPER);
		}
		else if(x>=7 && x<=40 && y>=83&& y<=107)
		{
			//返回
			dispose();
			logger.info("用户选择返回主菜单");
			new SelectMenu();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
