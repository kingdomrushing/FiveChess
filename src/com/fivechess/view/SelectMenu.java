package com.fivechess.view;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.naming.InitialContext;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

/**
 * 登录页面
 * 选择人机对战还是人人对战
 * @author admin
 *
 */
public class SelectMenu extends JFrame implements MouseListener{
	public static void main(String[] args) {
		new SelectMenu();
	}
	public SelectMenu()
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
	 * 添加背景图片，设置位置
	 */
	private void paintBg() {
		// TODO Auto-generated method stub
		ImageIcon image = new ImageIcon("images/main.jpg");
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
		//日志
		Logger logger = Logger.getLogger("菜单");
		//获取点击坐标
		int x=e.getX();
		int y=e.getY();
		//System.out.println(x+" "+y);
		if(x>=70 && x<=255 && y>=336&& y<=383)
		{
			// 加载人机对战页
			dispose();
			logger.info("用户选择人机对战页面");
			new ChooseLevel();
		}
		else if(x>=70 && x<=255 && y>=125 && y<=172)
		{
			//加载人人对战页面
			dispose();
			logger.info("用户选择人人对战页面");
			new PPMainBoard();
		}
		else if(x>=7 && x<=40 && y>=83&& y<=107)
		{
		//退出
			dispose();
			logger.info("用户点击退出游戏");
			System.exit(0);
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
