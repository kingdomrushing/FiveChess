package com.fivechess.view;

import com.fivechess.judge.*;
import com.fivechess.model.*;
import com.fivechess.net.*;
import javax.swing.*;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 人人对战棋盘
 * @author admin
 */
public class PPChessBoard extends ChessBoard{
    private int role; //角色
    private JTextArea talkArea; //交流信息
    private PPMainBoard mb;
    private int step[][]=new int[30*30][2];//定义储存步数数组
    private int stepCount=0;//初始化数组
    //加载黑白棋子，用于判定玩家所执棋
    private ImageIcon imageIcon1= new ImageIcon(blackChess);
    private ImageIcon imageIcon2= new ImageIcon(whiteChess);
    private Logger logger = Logger.getLogger("棋盘");
    /**
     * 构造函数，初始化棋盘的图片，初始化数组
     * @param mb 人人对战页面
     */
    public PPChessBoard(PPMainBoard mb) {
        super();
        this.mb=mb;
        //设置先开始游戏的玩家执白
        setRole(Chess.WHITE);
    }
    /**
     * 设置聊天窗口
     * @param area 聊天窗口
     */
    public void setInfoBoard(JTextArea area)
    {
        talkArea=area;
    }
    /**
     * 保存黑白棋子的坐标于二维数组中
     * @param posX
     * @param posY
     */
    private void saveStep(int posX,int posY)
    {
        stepCount++;
        step[stepCount][0]=posX;
        step[stepCount][1]=posY;
    }
    /**
     * 悔棋，去掉黑白棋子各一个
     */
    public void backstep() {
		// TODO Auto-generated method stub
    	if(stepCount>=2)
        {
            chess[step[stepCount][0]][step[stepCount][1]]=0;
            chess[step[stepCount-1][0]][step[stepCount-1][1]]=0;
            stepCount=stepCount-2;
        }
	}
    /**
     * 设置棋子横坐标
     * @param x,y,r 横坐标,纵坐标,对方的角色黑/白
     */
    public void setCoord(int x,int y,int r)
    {
    	//对方执白，自己执黑
        if(r==Chess.WHITE)
        {
            role=Chess.BLACK;
            mb.getLabel1().setIcon(imageIcon2);
            mb.getLabel2().setIcon(imageIcon1);
        }
        //对方执黑，自己执白
        else
        {
            role = Chess.WHITE;
            mb.getLabel1().setIcon(imageIcon1);
            mb.getLabel2().setIcon(imageIcon2);
        }
        chess[x][y]=r;
        saveStep(x, y); //保存坐标
        int winner=Judge.whowin(x,y,chess,r);
        WinEvent(winner);
        setClickable(MainBoard.CAN_CLICK_INFO);
        repaint();
    }
    /**
     * 设置角色
     * @param role 角色
     */
    public void setRole(int role)
    {
        this.role=role;
    }
    /**
     * 获得角色
     * @return 角色
     */
    public int getRole()
    {
        return role;
    }
    /**
     * 从父类继承的方法，自动调用，绘画图形
     * @param g 该参数是绘制图形的句柄
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    /**
     * 获得结果
     * @return 结果
     */
    public int getResult()
    {
        return result;
    }
    /**
     * 胜利事件
     * @param winner 胜方
     */
    public void WinEvent(int winner)
    {
        //白棋赢
        if(winner == Chess.WHITE) {
        	//中断线程
            try {
                mb.getTimer().interrupt();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            mb.getstart().setText("开始游戏");
            mb.getstart().setEnabled(true);
            result=Chess.WHITE;
            JOptionPane.showMessageDialog(mb,"恭喜！白棋获胜");
            logger.info("白棋获胜！初始化页面");
            setClickable(PPMainBoard.CAN_NOT_CLICK_INFO);
            //初始化页面
            initArray();
            mb.getLabel().setText(null);

        }
        //黑棋赢
        else if(winner==Chess.BLACK){
        	//中断线程
            try {
                mb.getTimer().interrupt();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            mb.getstart().setText("开始游戏");
            mb.getstart().setEnabled(true);
            result=Chess.BLACK;
            setClickable(MainBoard.CAN_NOT_CLICK_INFO);
            JOptionPane.showMessageDialog(mb,"恭喜！黑棋获胜");
            logger.info("黑棋获胜！初始化页面");
            //初始化页面
            initArray();
            mb.getLabel().setText(null);
        }
    }
    /**
     * 按下鼠标时，记录鼠标的位置，并改写数组的数值，重新绘制图形
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(clickable==PPMainBoard.CAN_CLICK_INFO) {
            chessX = e.getX();
            chessY = e.getY();
            //限定点击区域为棋盘区域
            if (chessX < 524 && chessX > 50 && chessY < 523 && chessY > 50) {
                float x = (chessX - 49) / 25;
                float y = (chessY - 50) / 25;
                int x1 = (int) x;
                int y1 = (int) y;
                //如果这个地方没有棋子
                if (chess[x1][y1] == Chess.BLANK) {
                    
                    if(role== Chess.WHITE) {
                        logger.info("白棋落子:"+x1+","+y1);
                        mb.getSituation1().setText("    状态:下棋...");
                        mb.getSituation2().setText("    状态:等待...");
                    }
                    else if(role==Chess.BLANK){
                        logger.info("黑棋落子:"+x1+","+y1);
                        mb.getSituation1().setText("    状态:下棋...");
                        mb.getSituation2().setText("    状态:等待...");
                    }
                    //计时线程中断
                    try {
                        mb.getTimer().interrupt();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    chess[x1][y1] = role;
                    saveStep(x1, y1);
                    //发送棋坐标
                    NetTool.sendUDPBroadCast(mb.getIp(),"POS"+","+x1 + "," + y1 + "," + role);
                    //判断输赢
                    int winner=Judge.whowin(x1,y1,chess,role);
                    WinEvent(winner);
                    setClickable(MainBoard.CAN_NOT_CLICK_INFO);
                }
            }
        }
    }
    
    /**
     *鼠标点击事件
     *@param e
     **/
     @Override
     public void mouseMoved(MouseEvent e) {
     	if(clickable==MainBoard.CAN_CLICK_INFO) {
     		 mousex=e.getX();
     		 mousey=e.getY();
     		 repaint();
     	}
     }

}
