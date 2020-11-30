package com.yemuc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JPanel;
/**
 * ******  TextPanel*******<BR>
 * 文本面板<BR>
 * 显示移动过程信息
 * 
 */
public class TextPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数<BR>
	 * 初始化面板布局<BR>
	 * 放置文本域
	 * @param textArea2   文本域
	 */
	public TextPanel(TextArea textArea2){
		
		this.textArea = textArea2;
		
		this.setLayout(new GridLayout());
		this.setBackground(Color.gray) ;             //背景色设置:灰色
	    this.setFont(Hanoi.TITLEFONT) ;                     //字体设置为“titileFont”
	    //this.add(new JLabel("移动过程展示"),JLabel.CENTER) ;          //初始化标签并添加到面板，左对齐
	   // this.setForeground(Color.white) ;             //设置前景色：白色
	    this.setPreferredSize(new Dimension(230,500));   //设置面板大小
	    
		//JPanel text = new JPanel();
		///text.setPreferredSize(new Dimension(230,490));
		this.add(textArea);
		//this.add(text);
		
	}
	
	private TextArea textArea;
	
}
