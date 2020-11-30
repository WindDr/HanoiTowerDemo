package com.yemuc;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ****** control PANEL ********<BR>
 *        控制面板<BR>
 * @author yemuc
*/	
final class ControlPanel extends JPanel{
	/**
	 * 线程最大休眠：1s
	 */
	static final int MAX_DELAY=1000 ;              //最大延迟1000ms
	/**
	 * 内部面板<BR>
	 * 关于盘子数设置
	 */
	private final JPanel discsPanel ;

	private Button bDiscsMinus, bDiscsPlus, bReset, bStart ,bPause ,bgoOn;
	private Checkbox cbTimer ;
	private TextField tfDiscs, tfTimer ;
	private Scrollbar sbSpeed ;
	private int discs=Hanoi.MIN_DISCS, delay=200 ;         //初始200
	
	/**
	 * 构造函数<BR>
	 * 设置面板布局<BR>
	 * 初始化构造盘子面板<BR>
	 * 放置速度控制滑块，并添加事件监听<BR>
	 * 放置计时器<BR>
	 * 为几个按钮添加事件监听<BR>
	 * @param main  Hanoi类的对象
	 */
	ControlPanel(Hanoi main) {
 
	   this.setLayout(new GridLayout(0,1,0,3)) ;           //设置布局管理器,1列
	   this.setFont(Hanoi.TEXTFONT) ;                      //设置字体
	   // 盘子设置单元
	   discsPanel = new JPanel() ;                         //实例化
	   discsPanel.setLayout(new BorderLayout(2,4)) ;       //设置布局管理器
	   tfDiscs = new TextField(3) ;                        //实例化文本框
	   tfDiscs.setFont(Hanoi.MONOFONT) ;                   //文本框字体设置
	   tfDiscs.setForeground(Color.black) ;                //文本框前景色设置
	   tfDiscs.setBackground(Color.lightGray) ;            //文本框背景色设置
	   tfDiscs.setEditable(false) ;                        //设置组件不可编辑
	   this.setDiscs(discs) ;                              //设置圆盘数，默认3
	   discsPanel.add("West",bDiscsMinus=new Button("-")) ;//在布局左侧增加"-"按钮
	   bDiscsMinus.setFont(Hanoi.MONOFONT) ;               //设置字体
	   discsPanel.add("Center",tfDiscs) ;                  //在布局中央增加文本框
	   discsPanel.add("East",bDiscsPlus=new Button("+")) ; //在布局右侧增加"+"按钮
	   bDiscsPlus.setFont(Hanoi.MONOFONT) ;                //设置字体
	   discsPanel.validate() ;                             //确保组件都能工作
	   //速度滑块设置
	   sbSpeed = new Scrollbar(Scrollbar.HORIZONTAL,
	                           (MAX_DELAY-delay),0,0,(MAX_DELAY-9)) ;   
	   sbSpeed.setBackground(Color.lightGray) ;            //设置背景色
	   sbSpeed.setBlockIncrement((int)(MAX_DELAY/10)) ;    //设置滚动条的快增量
	   sbSpeed.setUnitIncrement((int)(MAX_DELAY/100)) ;    //设置滚动条的块减量
	   // 计时器控制
	   cbTimer = new Checkbox("   Timer") ;              //实例化复选框
	   cbTimer.setBackground(Color.gray) ;              //设置背景色
	   tfTimer = new TextField(10) ;                    //实例化文本框
	   tfTimer.setFont(Hanoi.MONOFONT) ;                //设置字体
	   tfTimer.setForeground(Color.white) ;             //设置前景色
	   tfTimer.setBackground(Color.darkGray) ;          //设置背景色
	   tfTimer.setEditable(false) ;                     //组件不可编辑
	   // 组件添加
	   this.add(new JLabel("Discs",JLabel.CENTER)) ;    //加入标签，居中对齐 
	   this.add(discsPanel) ;                           //加入圆盘设置单元面板
	   this.add(bReset=new Button("reset")) ;              //加入"重置"按钮
	   this.add(bStart=new Button("start")) ;              //加入"开始"按钮
	   this.add(bPause=new Button("pause"));               //加入"暂停"按钮
	   this.add(bgoOn=new Button("goOn"));                //加入"继续"按钮
	   this.add(new JLabel("speed",JLabel.CENTER)) ;     //加入"速度"标签，居中显示
	   this.add(sbSpeed) ;                               //增加速度设置滑块
	   this.add(cbTimer) ;                               //增加计时器复选框
	   this.add(tfTimer) ;                               //增加时间显示文本框
	   
	   bgoOn.setEnabled(false);                          //初始时"继续"按钮不可操作
	   bPause.setEnabled(false);                         //初始时"暂停"按钮不可操作
	  
	   bStart.addActionListener(new ActionListener(){
       //为"开始"按钮设置监听器
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setDemoEnable(false) ;   //按下"开始"按钮后，按钮变为不可操作状态，同时演示线程启动，不可再次启动
			setPauseEnable(true);    //"暂停"按钮可操作
		    main.startDemoThread() ; //调用主类函数以开启演示线程
		}
	   });
	   
	   bReset.addActionListener(new ActionListener(){
	   //为"重置"按钮设置监听器
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			main.restartPlay();  //调用主类函数以重置演示线程
		}
	   });
	   
	   bPause.addActionListener(new ActionListener(){
	   //为"暂停"按钮设置监听器
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				main.pause();    //调用主类函数以暂停线程
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	   });
	   
	   bgoOn.addActionListener(new ActionListener(){
	   //为"继续"按钮设置监听器
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			main.goOn();    //调用主类函数以继续线程
		}
	   });
	   
	   sbSpeed.addAdjustmentListener(new AdjustmentListener(){
		 //为"速度"设置滑块设置监听器
		@Override
		public void adjustmentValueChanged(AdjustmentEvent arg0) {
			// TODO Auto-generated method stub
			delay = MAX_DELAY - sbSpeed.getValue() ;  //计算出线程休眠时间
		}
	   });
	   validate() ;                   //确保组件可工作
	   setPlusMinusEnable() ;         //设置调节圆盘数量控制按钮
	   cbTimer.setState(true) ;       //设置复选框初始状态为"on"状态
	   
	   bDiscsMinus.addActionListener(new ActionListener(){
	   //为圆盘数量设置按钮"-"设置滑块设置监听器
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setDiscs(--discs) ;    //圆盘数减1
				setPlusMinusEnable() ; //设置状态，判断按钮是否还可以操作
				main.restartPlay();    //盘子数更新要重置
			}
		});
		   
	    bDiscsPlus.addActionListener(new ActionListener(){
	    //为圆盘数量设置按钮"+"设置滑块设置监听器
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setDiscs(++discs) ;    //圆盘数加1
				setPlusMinusEnable() ;  //设置状态，判断按钮是否还可以操作
				main.restartPlay();     //盘子数更新要重置
			}
		 });
	}
	/**
	 * 设置计时器可用状态
	 * @param b   状态
	 */
	void setTimerEnable(boolean b) {
		cbTimer.setEnabled(b) ; 
	}
	/**
	 * 设置开始按钮是否可用
	 * @param b   状态
	 */
	void setDemoEnable(boolean b) { 
		bStart.setEnabled(b) ; 
	}
	/**
	 * 设置盘子数数量控制按钮<BR>
	 * 不能超过12或者低于1
	 */
	void setPlusMinusEnable() { 
	   bDiscsPlus.setEnabled(discs<Hanoi.MAX_DISCS) ;
	   bDiscsMinus.setEnabled(discs>Hanoi.MIN_DISCS) ;
	}
	/**
	 * 设置继续按钮是否可用
	 * @param b  状态
	 */
   void setgoOnEnable(boolean b) {
			// TODO Auto-generated method stub
		 bgoOn.setEnabled(b);
	}
   /**
    * 设置暂停按钮是否可用
    * @param b  状态
    */
	void setPauseEnable(boolean b) {
			// TODO Auto-generated method stub
		bPause.setEnabled(b);
	}
	/**
	 * 设置盘子数<BR>
	 * @param i  数量
	 */
	void setDiscs(int i) { 
	   String s = Integer.toString(i) ;
	   if (s.length()==1) s = "  " + s ;
	   else if (s.length()==2) s = " " + s ;
	   tfDiscs.setText(s) ;
	}
	/**
	 * 设置计时器<BR>
	 * @param s  时间，格式为min:sec:msec
	 */
	void setTimer(String s) {
		tfTimer.setText(s) ; 
	}
	/**
	 * 得到盘子数<BR>
	 * @return  盘子数
	 */
	int getDiscs() {
		return discs ; 
	} 
	/**
	 * 得到线程延迟
	 * @return  延迟时间
	 */
	public int getDelay() { 
		return delay ; 
	} 
	/**
	 * 得到计时器是否可用
	 * @return   计时器
	 */
	boolean isTimerOn() { 
		return cbTimer.getState() ;
	}
}