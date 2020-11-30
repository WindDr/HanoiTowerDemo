package com.yemuc;


/**
 * ****** TIMER ********<BR>
 * 计时器线程<BR>
*/	
final class Timer extends Thread {               //计时器线程
	/**
	 * 常量：1s=1000ms
	 */
	static final int ONE_SECND=1000 ;
	/**
	 * startTime:开始时间<BR>
	 */
	private long  startTime , rem ;
	/**
	 * 时间，格式：min:sec:msec<BR>
	 * min:分<BR>
	 * sec:秒<BR>
	 * msec:毫秒
	 */
	private int min , sec, msec;
	/**
	 * 时间数字对应的字符串
	 */
	private String sSec, sMsec, sTime ;
	/**
	 * 控制面板对象<BR>
	 * 便于调用控制面板的函数方法
	 */
	private final ControlPanel cp ;
	/**
	 * 当前时间，初始为0
	 */
	static  long nTime = (long)0;
	/**
	 * 时间范围，初始为0
	 */
	static long nRange = (long)0;
	/**
	 * 构造函数<BR>
	 * 初始化计数器显示：0<BR>
	 * 传递当前的控制面板到计时器类
	 * @param cp   当前的控制面板
	 */
	Timer(ControlPanel cp) {
		this.cp = cp ;
		cp.setTimer(setTime(0)) ;                  //在控制面板放置计时器
	}
	/**
	 * 计时器线程运行
	 */
	   @Override
	public void run() {
	    startTime = System.currentTimeMillis() ;  //获取机器当前时间
	    while (true) {
	    	try { 
	    		Thread.sleep(ONE_SECND) ;         //线程休眠一秒
		     } 
		    catch (InterruptedException e) {}
	    
		    cp.setTimer(setTime(System.currentTimeMillis() - startTime - nRange)) ;      //更新时间
	    	//cp.setTimer(setTime(nTime - Hanoi.time));
		}
	}
	/**
	 * 设置时间格式<BR>
	 * 传入long型时间戳数据<BR>
	 * 返回字符串型时间格式：min:sec:msec<BR>
	 * @param millisec   要转换的时间戳
	 * @return         返回格式化后的字符串
	 */
	private String setTime(long millisec) { 
		
		min = (int)(millisec/60000) ;          //1min=60sec=60000msec
		rem = millisec - (min*60000) ;
		sec = (int)(rem/1000) ;
		rem = rem - (sec*1000) ;
		msec = (int) rem ;      
		
		sSec = Integer.toString(sec) ;
	
		if (sSec.length()==1) 
			sSec = "0" + sSec ;                    //转换成字符
		sMsec = Integer.toString(msec) ;
		if (sMsec.length()==1) 
			sMsec = "0" + sMsec ;
		
		sTime = "  " + Integer.toString(min) + ":" + sSec + ":" + sMsec ;
		return sTime ;
	}
}		