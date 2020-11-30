package com.yemuc;

/**
 * ******** BOARD ********<BR>
 * 控制盘子状态，移动规则<BR>
 * @author yemuc
*/   
final class Board {
	/**
	 * 柱数3<BR>
	 */
	static final int PEGS=3;
	/**
	 * 盘子的尺寸
	 */
    static final int DISC_SIZES[][]={ {68,18},{76,16},{84,14},{92,13},{100,12},
	                                  {108,12},{112,11},{116,10},{120,9},{124,9} } ;    //光盘尺寸
    /**
     * peg[][]:每个柱子上的盘子<BR>
     * pegTop[]:当前柱子最上面的盘子<BR>
     * discWith[]:盘子宽度
     */
	private final int peg[][], pegTop[]=new int[PEGS], discWidth[] ;
	/**
	 * 移动次数
	 */
	private int  moveCount;
	/**
	 * 构造函数<BR>
	 * 初始态，所有盘子在第一个柱子上<BR>
	 * 计算出盘子的宽度
	 * 移动次数设为0
	 * @param discs  盘子数
	 */
	Board(int discs) {
		
		peg = new int[discs][PEGS] ;            ///构建盘子图形
		
		// 初始状态所有盘子在第一个柱子上
		for (int i=0; i<discs; i++) 
			peg[i][Hanoi.PEG1] = discs-i ;
		pegTop[Hanoi.PEG1] = discs-1 ;
		for (int i=1; i<PEGS; i++) 
			pegTop[i] = -1 ;
	
		// 计算盘子的宽度
		discWidth = new int[discs] ;
		for (int i=discs-1; i>=0; i--) 
			/* discWidth[i] = DISC_SIZES[discs-Hanoi11.MIN_DISCS][0] - 
	                     	(DISC_SIZES[discs-Hanoi11.MIN_DISCS][1] * 
	                     	(discs-1-i)) ;*/
		{
			discWidth[i] = DISC_SIZES[discs-Hanoi.MIN_DISCS][0] - 
	                     (DISC_SIZES[discs-Hanoi.MIN_DISCS][1] * 
	                     (discs-1-i)) ;
		}
		
		moveCount = 0 ;   //移动次数初始为0
	}
	/**
	 * 放置盘子 
	 * @param d  要放置的盘子
	 * @param p  要放置的柱子
	 */
	void setDisc(int d,int p){ 
		peg[++pegTop[p]][p] = d ; 
	}
	/**
	 * 获取盘子
	 * @param d  该柱上盘子
	 * @param p  该柱子
	 * @return
	 */
	int getDisc(int d,int p) { 
		return peg[d][p] ; 
	}
	/**
	 * 获取某柱最高的那个盘子
	 * @param p   某柱
	 * @return
	 */
	int getTopDisc(int p) { 
		return peg[pegTop[p]--][p] ; 
	}
	/**
	 * 获取当前柱最高的盘子
	 * @param p  柱子
	 * @return
	 */
	int getPegTop(int p) {
		return pegTop[p] ; 
	}
	/**
	 * 获取移动次数
	 * @return
	 */
	int getMovecount() {
		return moveCount ; 
	}
	/**
	 * 获取盘子宽度
	 * @param d
	 * @return
	 */
	int getDiscWidth(int d) { 
		return discWidth[d-1] ; 
	}
	/**
	 * 是否可以是开始柱
	 * @param i  柱号
	 * @return
	 */
	boolean isStartPeg(int i) { 
	    return (i >= 0)&&(pegTop[i]>=0);
	}
	/**
	 * 自动移动函数
	 * @param p1   盘子
	 * @param p2  目标柱
	 */
	void moveDisc(int p1,int p2) {
	   setDisc(getTopDisc(p1),p2) ;
	   moveCount++ ;
	}            
}