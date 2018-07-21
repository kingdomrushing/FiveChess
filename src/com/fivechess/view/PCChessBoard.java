package com.fivechess.view;

import com.fivechess.judge.*;
import com.fivechess.model.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 加载人机棋盘
 * 人机对战
 * @author：admin
 */

public class PCChessBoard extends ChessBoard{
    private int role;//人类角色
    private int result=1;//定义每次下棋后结果（是否五子成线，结束棋局）,默认棋局未结束
    private PCMainBoard mb;//定义主界面
    private Computer com;//定义电脑角色
    private int step[][]=new int[30*30][2];//定义储存步数数组
    private int stepCount=0;//初始化数组
    private Coord coord=new Coord(); //坐标
    //打印日志
    private Logger logger1 = Logger.getLogger("棋盘"); 
    private Logger logger2 = Logger.getLogger("接收通道");
    //设置结果
    public void setResult(int result){this.result=result;}

    public PCChessBoard(PCMainBoard mb)
    {
        this.mb=mb;
        role=Chess.WHITE; //定义玩家执白
        com=new Computer();//加载电脑算法
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
     * 悔棋，去掉刚下的黑白两棋
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
     * 胜利事件
     * @param winner 胜利方
     **/
    public void WinEvent(int winner)
    {
        //白棋赢
        if(winner == Chess.WHITE) {
            result=GAME_OVER;
            //终止计时线程
            try {
                mb.getTimer().interrupt();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            mb.getstart().setText("开始游戏");
            mb.getstart().setEnabled(true);
            mb.getSituation1().setText("    状态:");
            mb.getSituation2().setText("    状态:");
            JOptionPane.showMessageDialog(mb,"恭喜！白棋获胜");
            logger1.info("白棋获胜！初始化棋盘页面");
            setClickable(MainBoard.CAN_NOT_CLICK_INFO);
            initArray();  //初始化页面
            mb.getLabel().setText(null); //清空计时
        }
        //黑棋赢
        else if(winner==Chess.BLACK){
            result=GAME_OVER;
            try {
                mb.getTimer().interrupt();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            /*
             * 恢复初始页面状态
             */
            mb.getstart().setText("开始游戏");
            mb.getstart().setEnabled(true);
            mb.getSituation1().setText("    状态:");
            mb.getSituation2().setText("    状态:");
            setClickable(MainBoard.CAN_NOT_CLICK_INFO);
            JOptionPane.showMessageDialog(mb,"恭喜！黑棋获胜");
            logger1.info("黑棋获胜！初始化棋盘页面");
            initArray();
            mb.getLabel().setText(null);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {  
        int winner;
        if(clickable==MainBoard.CAN_CLICK_INFO) {
            chessX = e.getX();
            chessY = e.getY();
            //将点击限制在棋盘内
            if (chessX < 524 && chessX > 50 && chessY < 523 && chessY > 50) {
                float x = (chessX - 49) / 25;
                float y = (chessY - 50) / 25;
                int x1 = (int) x;
                int y1 = (int) y;
                //如果这个地方没有棋子
                if (chess[x1][y1] == Chess.BLANK){
                    chess[x1][y1] = role;
                    try {
                        mb.getTimer().interrupt();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    mb.getSituation1().setText("    状态:下棋...");
                    mb.getSituation2().setText("    状态:等待...");
                    logger2.info("等待对方消息");
                    saveStep(x1,y1);  //保存坐标，用于悔棋
                    logger1.info("白棋落子:"+x1+","+y1);
                    setClickable(MainBoard.CAN_NOT_CLICK_INFO);
                    winner= Judge.whowin(x1, y1, chess, role);//判断胜负
                    WinEvent(winner);
                    if(result!=GAME_OVER) {
                        coord = com.computePos(Chess.BLACK, chess,mb.getLevel());//加载电脑棋类算法
                        chess[coord.getX()][coord.getY()] = Chess.BLACK;//输出黑棋图片
                        //重新启动计时线程
                        mb.timer=new TimeThread(mb.getLabel()); 
                        mb.timer.start();
                        logger1.info("黑棋落子:"+coord.getX()+","+coord.getY());
                        mb.getSituation1().setText("    状态:等待...");
                        logger2.info("等待对方消息");
                        mb.getSituation2().setText("    状态:下棋...");
                        saveStep(coord.getX(),coord.getY());  //保存电脑坐标，用于悔棋
                        winner = Judge.whowin(coord.getX(), coord.getY(), chess, Chess.BLACK);
                        WinEvent(winner);
                        if(result!=GAME_OVER) {
                            setClickable(MainBoard.CAN_CLICK_INFO);
                        }
                    }
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
