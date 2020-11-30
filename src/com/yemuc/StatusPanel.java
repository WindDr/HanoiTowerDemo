package com.yemuc;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ****** STATUS PANEL ********<BR>
 * 状态面板<BR>
 * 显示当前线程运行状态<BR>
 * 移动次数<BR>
 * 当前运行速度
*/   
final class StatusPanel extends JPanel {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文本框<BR>
	 * spStatus:状态<BR>
	 * spMoveCount:移动次数<BR>
	 * spSpeed:移动速度 
	 */
	private TextField spStatus, spMoveCount , spSpeed ;
	/**
	 * 构造函数<BR>
	 * 初始化状态面板布局<BR>
	 * 设置文本背景以及前景色<BR>
	 * 设置文本框状态<BR>
	 */
	StatusPanel( ) {
		setFont(Hanoi.TEXTFONT) ;
		spStatus = new TextField(50) ;
		spStatus.setForeground(Color.white) ;
		spStatus.setBackground(Color.darkGray) ;
		spStatus.setEditable(false) ;
	
		spMoveCount = new TextField(6) ;
		spMoveCount.setFont(Hanoi.MONOFONT) ;
		spMoveCount.setForeground(Color.white) ;
		spMoveCount.setBackground(Color.darkGray) ;
		spMoveCount.setEditable(false) ;
	
		spSpeed = new TextField(6) ;
		spSpeed.setFont(Hanoi.MONOFONT) ;
		spSpeed.setForeground(Color.white) ;
		spSpeed.setBackground(Color.DARK_GRAY) ;
		spSpeed.setEditable(false) ;
		
		this.setLayout(new GridLayout(3,1));
		this.add(new JLabel("状态栏",JLabel.CENTER));
		this.add(spStatus) ;
		this.add(new JLabel("移动次数",JLabel.CENTER)) ;
		this.add(spMoveCount) ;
		this.add(new JLabel("当前速度(MaxDelay-delay)",JLabel.CENTER)) ;
		this.add(spSpeed) ;
		
		validate() ;           //确保组件可运行
	} 
	/**
	 * 设置移动文本框
	 * @param i    移动次数
	 */
	void setMovecount(int i) { 
	   String s=Integer.toString(i) ;     //将数字i转换为字符串
	
	   switch (s.length()) {              //确保字符串长度恒为5，右对齐效果
	      case 1: s = "    " + s ; break ;
	      case 2: s = "   " + s ; break ;
	      case 3: s = "  " + s ; break ;
	      case 4: s = " " + s ; break ;
	   }
	   spMoveCount.setText(s) ;           //将字符串显示待文本框中
	}
	/**
	 * 设置状态文本框
	 * @param s   状态信息
	 */
	void setStatus(String s){
		spStatus.setText(s) ; 
	} 
	/**
	 * 设置速度文本框信息
	 * @param speed  速度信息
	 */
	void setSpeed(int speed){
		String s=Integer.toString(ControlPanel.MAX_DELAY-speed) ;     //将数字i转换为字符串
		spSpeed.setText("  "+s);
	}
}
