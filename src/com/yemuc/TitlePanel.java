package com.yemuc;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * ******* TitlePanel********<BR>
 *   标题面板<BR>
 *  @author yemuc
 */
public class TitlePanel extends JPanel{

	//private static final long serialVersionUID = 1L;
	/**
	 * 构造函数<BR>
	 * 初始化标题面板布局
	 * @param titleLabel   当前程序的标题面板
	 */
	public TitlePanel(JLabel titleLabel){
		
		 // Title panel                                     //标题面板
		this.setLayout(new GridLayout(1,1)) ;        //布局设置，GridLayout布局，1行1列
	   	this.setBackground(Color.gray) ;             //背景色设置:灰色
	   	this.setFont(Hanoi.TITLEFONT) ;                     //字体设置为“titileFont”
	   	this.add(titleLabel=new JLabel("汉诺塔演示程序 ",JLabel.CENTER)) ;          //初始化标签并添加到面板，居中对齐
	   	this.setForeground(Color.white) ;             //设置前景色：白色
		//this.setSize(getWidth(),300);
	}
}
