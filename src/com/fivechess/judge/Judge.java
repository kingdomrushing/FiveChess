package com.fivechess.judge;

/**
 * 下过棋之后判断是否五子成线
 * 以棋盘左上角为坐标原点
 * 横向x坐标变化，纵向y坐标变化，斜向x，y变化
 * 在每次横向或纵向或斜向变化后均判断一次是否连成5子
 * @author admin
 * 
 **/

import javax.swing.*;

import java.awt.*;
import com.fivechess.model.Chess;

public class Judge {
	/**
	 * 判断是否五子成线
	 * @param xIndex 该点横坐标
	 * @param yIndex 该点纵坐标
	 * @param chess 数组
	 * @param role 黑棋或白棋
	 * @return 黑棋或白棋或空
	 */
	public static int whowin(int xIndex, int yIndex,int chess[][],int role){
		int continueCount = 1;//连续棋子个数
		//横向向左
		for(int x=xIndex-1;x>=0;x--){
			if(chess[x][yIndex] == role){  
                continueCount++;
			}
			else{
				break;
			}
		}
		//横向向右
		for(int x=xIndex+1;x<19;x++){
			if(chess[x][yIndex] == role){  
                continueCount++;
			}
			else{
				break;
			}
		}
		//判断是否大于5
		if(continueCount>=5){
			return role;
		}
		else{
			continueCount = 1;
		}
		//纵向向上
		for(int y=yIndex-1;y>=0;y--){
			if(chess[xIndex][y] == role){  
                continueCount++;
			}
			else{
				break;
			}
		}
		//纵向向下
		for(int y=yIndex+1;y<19;y++){
			if(chess[xIndex][y] == role){  
                continueCount++;
			}
			else{
				break;
			}
		}
		//判断是否大于5
		if(continueCount>=5){
			return role;
		}
		else{
			continueCount = 1;
		}
		//左上
		for(int x=xIndex-1,y=yIndex-1;x>=0 && y>=0;x--,y--){
			if(chess[x][y] == role){  
                continueCount++;
			}
			else{
				break;
			}
		}
		//右下
		for(int x=xIndex+1,y=yIndex+1;x<19 && y<19;x++,y++){
			if(chess[x][y] == role){  
                continueCount++;
			}
			else{
				break;
			}
		}
		//判断是否大于5
		if(continueCount>=5){
			return role;
		}
		else{
			continueCount = 1;
		}
		//右上
		for(int x=xIndex+1,y=yIndex-1;x<19 && y>=0;x++,y--){
			if(chess[x][y] == role){  
                continueCount++;
			}
			else{
				break;
			}
		}
		//左下
		for(int x=xIndex-1,y=yIndex+1;x>=0 && y<19;x--,y++){
			if(chess[x][y] == role){  
                continueCount++;
			}
			else{
				break;
			}
		}
		//判断是否大于5
		if(continueCount>=5){
			return role;
		}
		else{
			continueCount = 1;
		}
		return 0;
	}

}
