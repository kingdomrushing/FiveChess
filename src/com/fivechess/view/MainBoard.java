package com.fivechess.view;

import com.fivechess.model.TimeThread;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * 人机对战和人人对战父类
 * 初始化二者相同页面、计时器
 * @author admin
 */
public class MainBoard extends JFrame implements MouseListener,ActionListener{
    public static final int CAN_CLICK_INFO=1; //棋盘可以点击
    public static final int CAN_NOT_CLICK_INFO=0; //棋盘不可以点击
    public JLabel label_timeCount;//计时
    public JLabel timecount;
    public TimeThread timer;
    public int result=1; 
    public TimeThread getTimer() {return timer;}
    public JLabel getLabel() {return label_timeCount;}
    public MainBoard()
    {
        setLayout(null); //取消原来布局
        setBounds(100,30,1000,680);
        init1();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("欢乐五子棋");
    }
    
    /**
     * 绘图
     * @param g
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        repaint();
    }

    /**
     * 初始化计时标签
     */
    public void init1()
    {
        label_timeCount=new JLabel();
        label_timeCount.setFont(new Font("宋体",Font.BOLD,30));
        label_timeCount.setBounds(502, 1, 500, 50);
        add(label_timeCount);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
