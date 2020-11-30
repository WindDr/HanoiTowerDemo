package com.yemuc;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * ****** BOARD CANVAS ********<BR>
 * 画出面包board<BR>
 * 画出圆盘<BR>
 * 重载paint方法<BR>
 * @author yemuc
*/	
final class BoardCanvas extends Canvas {

	/**
	 * PEG_SPACE：柱子空间<BR>
	 * DISC_HEIGHT：盘子高度
	 */
	static final int PEG_SPACE=75, DISC_HEIGHT=15 ;
	/**
	 * 常量，颜色
	 */
	static final Color COLOR_1=new Color(102,51,0), 
			COLOR_2=new Color(153,102,0),
					COLOR_3=new Color(204,153,51),
							COLOR_4=new Color(255,204,0),
									COLOR_5=new Color(255,255,204) ;
	/**
	 * 图片
	 */
	private Image bufferImage ;
	/**
	 * 图片缓存
	 */
	private Graphics buffer ;
	public static int x,y,width1;
	/**
	 * 画出背景图以及圆盘新状态
	 * @param b     面板
	 * @param boardImage     背景图
	 * 
	 */
	void drawBoard(Board b,Image boardImage) {
		   int width=0, disc=0 ;
		   //buffer.drawImage(boardImage,0,0,this);
		   if (buffer==null) {
			   bufferImage = createImage(Hanoi.CANVAS_WIDTH,Hanoi.CANVAS_HEIGHT) ;
			   buffer = bufferImage.getGraphics() ;
		   }
		   //System.out.println(buffer);
	      // 画出 board
	      buffer.drawImage(boardImage,0,0,this) ;
	      // 画出盘子
	      for (int p=Hanoi.PEG1; p<=Hanoi.PEG3; p++) {
	         for (int d=0; d<=b.getPegTop(p); d++) {
	            disc = b.getDisc(d,p) ;
	            if (disc!=0) {
	               width = b.getDiscWidth(disc) ;
					//super.paint(this.bufferImage.getGraphics());//调用父类方
					drawDisc( (((2*p)+1)*PEG_SPACE)-((int)(width/2)),
	                  Hanoi.TABLE_TOP-((d+1)*DISC_HEIGHT),width ) ;
					x = (((2*p)+1)*PEG_SPACE)-((int)(width/2));
					y = Hanoi.TABLE_TOP-((d+1)*DISC_HEIGHT);
					width1 = width;
					//repaint();

	            }
	         }
	      }
	     
	      repaint() ;

	   }
	/**
	 * 画出盘子<BR>
	 * @param x     x坐标
	 * @param y     y坐标
	 * @param width  盘子宽度
	 */
	void drawDisc(int x,int y,int width) {

	  buffer.setColor(COLOR_3) ;
	  buffer.drawLine(x+4,y,x+width-4,y) ;         // 1
	  buffer.drawLine(x+2,y+1,x+width-2,y+1) ;     // 2
	  buffer.drawRect(x,y+7,width,1) ;             // 8,9
	  buffer.setColor(COLOR_4) ;
	  buffer.drawLine(x+1,y+2,x+width-1,y+2) ;     // 3
	  buffer.drawRect(x,y+5,width,1) ;             // 6,7
	  buffer.setColor(COLOR_5) ;
	  buffer.drawLine(x+1,y+3,x+width-1,y+3) ;     // 4
	  buffer.drawLine(x,y+4,x+width,y+4) ;         // 5
	  buffer.setColor(COLOR_2) ;
	  buffer.drawRect(x,y+9,width,1) ;             // 10,11
	  buffer.drawLine(x+1,y+11,x+width-1,y+11) ;   // 12
	  buffer.setColor(COLOR_1) ;
	  buffer.drawLine(x+1,y+12,x+width-1,y+12) ;   // 13
	  buffer.drawLine(x+2,y+13,x+width-2,y+13) ;   // 14
	  buffer.drawLine(x+4,y+14,x+width-4,y+14) ;   // 15
		//this.repaint();
	}
	/**
	 * 重置paint()
	 */
	    @Override
	public void paint(Graphics g) {
	    	//super.paint(g);//调用父类方法
			update(g) ;

	    }
	    /**
	     * 更新背景图
	     */
	    @Override
	public void update(Graphics g) {
	    	g.drawImage(bufferImage,0,0,this) ;
	    //repaint();
	    }

}


