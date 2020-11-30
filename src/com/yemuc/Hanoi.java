
package com.yemuc;

import java.awt.* ;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

//import static sun.applet.AppletResourceLoader.getImage;


/**
 * ****** HANOI ********<BR>
 *主类<BR>
 *功能：初始化程序面板<BR>
 *    开始演示<BR>
 *    暂停演示<BR>
 *    继续演示<BR>
 *    停止演示<BR>
 *    启动演示线程，计时器线程<BR>
 *    汉诺塔递归解决函数<BR>
 * @author yemuc
*/	
public class Hanoi extends JFrame implements Runnable {
	/**
	 * 字体常量
	 */
	static final Font TITLEFONT=new Font("Helvetica", Font.BOLD, 12),
                     TEXTFONT=new Font("Helvetica", Font.PLAIN, 11),
                     MONOFONT=new Font("Courier", Font.BOLD, 12) ;
	static final int CANVAS_WIDTH=450, CANVAS_HEIGHT=250, TABLE_TOP=225,
                    PEG1=0, PEG2=1, PEG3=2, MIN_DISCS=3, MAX_DISCS=12 ;
   /**
    * 右侧面板
    */
	private JPanel cpBack ;                //面板
	/**
	 * 标题标签
	 */
	private JLabel titleLabel ;                        //标题标签
	/**
	 * 放置图片的面板
	 */
	private Board board ;                                //板
	/**
	 * 画板<BR>
	 * 画出图片以及盘子移动情况
	 */
	private BoardCanvas boardCanvas ;                          //画图
	/**
	 * 状态面板
	 */
	private StatusPanel statusPanel ;                          //状态面板
	/**
	 * 控制功能面板
	 */
	private ControlPanel controlPanel ;                         //控制面板
	/**
	 * 文本面板<BR>
	 * 显示每一步盘子的移动状况
	 */
	private TextPanel textPanel;
	/**
	 * 文本面板的文本区
	 */
	private TextArea textArea;
	/**
	 * 画板背景图片
	 */
	private Image boardImage ;                        //面板图片
	/**
	 * 图片的名字
	 */
	private final String boardImageFile = "board.gif";     //图片名
	/**
	 * 线程<BR>
	 * sloveThread:演示线程<BR>
	 * timer:计时器线程
	 */
	private Thread demoThread, timer ;                //线程：解决线程以及时间显示线程

	/**
	 * 构造函数函数<BR>
	 * 功能：完成初始化工作<BR>
	 * 	   加载画板图片并实现监听<BR>
	 *     画布设置<BR>
	 *     初始化主面板布局<BR>
	 *     初始化标题面板<BR>
	 *     初始化文本面板<BR>
	 *     初始化控制面包男<BR>
	 *     初始化状态面板<BR>
	 *     构造主面板<BR>
	 *     开启演示
	 */
	public Hanoi() throws IOException {
		super("Hanoi demo by 1020041201");
		File file = new File("");
		String filePath = file.getCanonicalPath();
		System.out.println(filePath);
		String usrpath = System.getProperty("user.dir");     //导出jar包有问题 这是得到工作目录
		System.out.println("usrpath:"+usrpath);
		String path = usrpath + "\\src\\com\\yemuc\\" +boardImageFile;    //待改进，读取路径有些问题，有空改进
		//System.out.println("path  "+path);
//		String path1 = this.getClass().getResource("").getPath();   // 此法不行，文件夹拒绝访问
//		System.out.println(path1);
		String path2 = filePath +"\\" +boardImageFile;
		//boardImage = ImageIO.read(new FileInputStream(path));
		boardImage = ImageIO.read(new FileInputStream(path2));
		//初始化界面
		setBackground(Color.black) ;                       //设置背景色：黑色
		JPanel mainPanel = new JPanel() ;                    //定义一个主面板
		mainPanel.setLayout(new BorderLayout(5,5)) ;       //布局设置为BoderLayout，部件间水平间隔，垂直间隔 5

		// 	BoardCanvas                                     //Board画布 设置
		boardCanvas = new BoardCanvas() ;                       //初始化
		boardCanvas.setSize(CANVAS_WIDTH,CANVAS_HEIGHT) ;            //设置宽，高  正好是图片的高度

		//标题面板
		TitlePanel titlePanel = new TitlePanel(titleLabel);

		//文本面板
		textArea = new TextArea();
		textPanel = new TextPanel(textArea);

		// 控制面板
		cpBack = new JPanel() ;                              //初始化面板cpback
		cpBack.setBackground(Color.gray) ;                  //设置背景色：灰色
		controlPanel = new ControlPanel(this) ;                        //初始化控制面板
		cpBack.add(controlPanel) ;                                    //控制面板放入cpback

		// 状态面板
		statusPanel = new StatusPanel() ;                        //初始化状态面板
		statusPanel.setBackground(Color.gray) ;                      //设置背景色

		// 构造主面板
		mainPanel.add(boardCanvas,BorderLayout.CENTER);              //Boder画布boardCanvas放入中
		mainPanel.add(titlePanel,BorderLayout.NORTH) ;
		mainPanel.add(cpBack,BorderLayout.EAST) ;
		mainPanel.add(statusPanel,BorderLayout.SOUTH) ;
		mainPanel.add(textPanel,BorderLayout.WEST);
		this.add(mainPanel) ;                                    //主面板放入容器

		validate() ;                                        //确保组件具有有效布局

		//下面设置界面初始的显示位置  否则初始在左上角，显示不美观
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = this.getSize().height; // 取窗口的高
		int width = this.getSize().width; // 窗口的宽

		int top = (scrSize.height - height) / 3 ; // 设置 界面x y
		int left = (scrSize.width - width) / 3;

		this.setFocusable(true);
		this.setLocation(left,top);        //计算数值 自主设置以居中
		//this.setLocationRelativeTo(null);     //自动居中显示
		this.setSize(Hanoi.CANVAS_WIDTH+350,Hanoi.CANVAS_HEIGHT+300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.newPlay();
	}

 /**
   	 * 开启新演示<BR>
   	 * 更新各组件状态，确保为初始状态
   	 */
   	void newPlay() {
   		int discs = controlPanel.getDiscs() ;                         //获取控制面板的盘子数

   		System.gc() ;                                       //通知垃圾回收器
   		
   		board = new Board(discs);
   		boardCanvas.drawBoard(board,boardImage) ;                 //画布画图
   		statusPanel.setMovecount(0) ;   
   		statusPanel.setSpeed(controlPanel.getDelay());
   		textArea.setText(null);
   		
   		if (controlPanel.isTimerOn())                       //如果控制面板的时间起动
   		{
   			timer = new Timer(controlPanel) ;               //构造计时器对象
   		}
   		else 
   			controlPanel.setTimer(" ") ;
   		if (demoThread == null)                            //如果自动解决的线程为空，没有启动
   			statusPanel.setStatus("准备移动这 " + discs + "个盘子 到正确位置.") ;    //则在状态面板显示这句话
   		boardCanvas.requestFocus() ;                        //输入焦点放到画布上
   	}		
   /**
    * 重新开始<BR>
    * 调用stop函数<BR>
    * 开始新演示
    */
   	void restartPlay() { 
   		stop() ;                                            //先停下当前线程
   		controlPanel.setDemoEnable(true) ;             //设置控制面板对象的能够自动解决状态为true
   		controlPanel.setPauseEnable(false);
   		controlPanel.setgoOnEnable(false);
   		Timer.nTime = (long)0;
   		Timer.nRange = (long)0;
   		newPlay() ;                                         //开始新演示
   		
   	}
   /**
    * 暂停函数<BR>
    * 处理暂停组件传来的事件<BR>
    * 暂停演示线程，计时器线程<BR>
    * 更新Timer中当前时间nTime<BR>
    * 更新状态面板信息<BR>
    * 更新相关组件信息：设置继续按钮可操作，继续按钮不可操作
    * @throws InterruptedException
    */
   	@SuppressWarnings("deprecation")
	public void pause()throws InterruptedException {
   		demoThread.suspend();
   		Timer.nTime = System.currentTimeMillis() ;
   		timer.suspend();
   		
   		statusPanel.setStatus("暂停......");
   		
   		controlPanel.setgoOnEnable(true);
   		controlPanel.setPauseEnable(false);
   	}
   	/**
   	 * 继续函数<BR>
   	 * 处理继续组件事件<BR>
   	 * 继续演示线程，计时器线程<BR>
   	 * 更新Timer中暂停时间长度nrem<BR>
   	 * 更新状态面板信息<BR>
   	 * 更新相关组件信息：设置暂停按钮可操作，继续按钮不可操作
   	 */
   	@SuppressWarnings("deprecation")
	public void goOn(){
   		demoThread.resume();
   		//Timer.nTime = time ;
   		Timer.nRange += System.currentTimeMillis() - Timer.nTime ;
   		timer.resume();
   		
   		statusPanel.setStatus("演示......");
   		
   		controlPanel.setgoOnEnable(false);
   		controlPanel.setPauseEnable(true);
   	}
   	/**
   	 * 停止函数<BR>
   	 * 终止两个线程
   	 */
   	@SuppressWarnings("deprecation")
   	public void stop() {                                      //终止线程，主要就是自动解决线程以及计时器线程
   		if (demoThread!=null) {
   			demoThread.stop() ;
   			demoThread = null ;
   		}
   		if (timer!=null) {
   			timer.stop() ;
   			timer = null ;
   		}
   	}
   	/**
   	 * 开始演示线程<BR>
   	 * 同时开启计时器线程
   	 */
   	public void startDemoThread() {                        //开始解决线程
   		stop() ;
   		demoThread = new Thread(this) ;                     //为线程申请资源
   		
   		if ((timer!=null)&&(!timer.isAlive())) 
   			timer.start() ;      //如果计时器线程存在，并且存在，那么就启动它
   		demoThread.start() ;                                //开始线程
   	}
   	/**
   	 * 线程启动<BR>
   	 * 更新状态面板信息<BR>
   	 * 更新设置相关组件状态
   	 */
   	@SuppressWarnings("deprecation")
   	@Override
    public void run() {                                     //自动解决线程的run方法
        newPlay() ;                                          //开始新状态
        statusPanel.setStatus("演示中 ...") ;                  //设置状态信息
      
        timer.start();                                      //计时器线程启动
        solve(controlPanel.getDiscs(),PEG1,PEG2,PEG3) ;      //调用解决函数
        timer.stop();                                       //计时器线程停止
        
        statusPanel.setStatus("移动完成!") ;                 //更新状态信息

        controlPanel.setDemoEnable(true) ;           //可以开启新的线程
        controlPanel.setgoOnEnable(false);                //”继续“按钮不可操作
        controlPanel.setPauseEnable(false);               //”暂停“按钮不可操作
    }
    /**
     * 汉诺塔解决函数，递归实现<BR>
     * 更新状态面板相关信息：移动次数、当前速度<BR>
     * 更新文本面板的信息，显示移动信息<BR>
     * 画出移动状态<BR>
     * @param discs   盘子数
     * @param source  起始柱
     * @param aux     中间柱
     * @param target  目标柱
     */
    //汉诺塔解题函数
    void solve(int discs,int source,int aux,int target) {
    	if (discs==0) 
    		return ;                                     // 如果盘子为0，不用再进行了
    	solve(discs-1,source,target,aux) ;               // 递归解决，借由aux柱，将盘子从source柱移至target柱
    	textArea.append("第"+(board.getMovecount()+1)+"步，塔"+source+"盘子"+discs+"从"+source+"塔移到"+target+"塔\n");
    	if((board.getMovecount()+1) % 10 == 0)           //10行一换行
    		textArea.append("\n");
    	board.moveDisc(source,target) ;                  // 移动盘子
    	statusPanel.setMovecount(board.getMovecount()) ; // 更新状态面板中的移动步数，这个变量记录在board类（解题过程）中
    	statusPanel.setSpeed(controlPanel.getDelay());
    	boardCanvas.drawBoard(board,boardImage) ;        //将移动盘子后的board状态画出来
    	try {
    		Thread.sleep(controlPanel.getDelay()) ; 
    	 }    										     //线程休眠
    	catch (InterruptedException e)
    	{}
    	solve(discs-1,aux,source,target) ;               // 再次递归，此次将target柱当成空闲柱aux
    }
	public static void main(String[] args) throws IOException {
		// write your code here
		//System.out.println("hello world");
		Hanoi hanoi = new Hanoi();
	}
}

	
		
