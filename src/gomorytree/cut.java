package gomorytree;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class cut {
    //实现Ford-Fulkerson算法，求出给定图中从源点s到汇点t的最大流，并输出最小割。
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int[][] c=new int[][]{
                {0,1,1,0},
                {0,0,1,1},
                {0,0,0,1},
                {0,0,0,0}
        };
        MGraph1 m1=new MGraph1(c);
        m1.fordFulkerson(0, 3);

//        int[][] c1=new int[][]{
//                {0,3,3,4,0,0,0},
//                {0,0,0,0,2,0,0},
//                {0,1,0,0,1,0,0},
//                {0,0,0,0,0,5,0},
//                {0,0,0,1,0,1,2},
//                {0,0,0,0,0,0,5},
//                {0,0,0,0,0,0,0}
//        };
        int[][] c1 = new int[][]{
                {0,10,0,0,0,8},
                {10  ,0 ,  4,   0,   2,   3},
        {0   ,4   ,0  , 5   ,4  , 2},
            {0  , 0  , 5 ,  0 ,  7   ,2},
                {0  , 2  , 4  , 7   ,0  , 3},
                    {8 ,  3  , 2 ,  2   ,3  , 0}
        };
        MGraph1 m2=new MGraph1(c1);
        m2.fordFulkerson(0, 3);
    }
}

class MGraph1{
    private int[][] c;        //容量矩阵
    private int[][] e;        //残量矩阵
    private int[][] f;        //当前流矩阵
    private int vexNum;        //顶点数量

    public MGraph1(int[][] c){
        this.c=c;
        this.vexNum=c.length;
        this.e=new int[vexNum][vexNum];
        this.f=new int[vexNum][vexNum];

        //刚开始时残量矩阵等于容量矩阵
        for(int i=0;i<vexNum;i++){
            System.arraycopy(c[i], 0, e[i], 0, c[i].length);
        }

    }
    //fordFulkerson算法
    public void fordFulkerson(int s,int t){
        int[] route=new int[vexNum];    //s到t的路径数组,route[i]表示i的前一个顶点
        System.out.println("route is "+route[0]);
        while(bfs(s,t,route)){             //若还能找到一条路径

            //寻找路径中流最小的边的大小(在残量矩阵中)
            int min=Integer.MAX_VALUE;
            int tail=t;
            int head=route[t];

            while(head!=-1){
                if(e[head][tail]<min){
                    min=e[head][tail];
                }
                tail=head;
                head=route[head];
            }

            //更新当前流矩阵和残量矩阵
            int tail1=t;
            int head1=route[tail1];
            while(head1!=-1){
                //更新当前流矩阵
                if(c[head1][tail1]!=0){
                    f[head1][tail1]+=min;        //容量矩阵中存在边,增加head1到tail1的流的大小为min
                }else{
                    f[head1][tail1]-=min;        //容量矩阵中不存在边，撤销head1到tail1的流的大小为min
                }
                //更新残量矩阵
                e[head1][tail1]-=min;            //head1到tail1的流量减少min
                e[tail1][head1]+=min;            //tail1到head1的流量增加min

                tail1=head1;
                head1=route[head1];
            }//while
            //route=new int[vexNum];
            Arrays.fill(route, 0);                 //初始化路径数组
        }//while 还能找到一条s到t的路径

        int maxFlow=0;
        for(int i=0;i<vexNum;i++)                //最大流为  当前流矩阵中  从s流出的量
            maxFlow+=f[s][i];
        System.out.println("最大流为:"+maxFlow);

        for(int i=0;i<e.length;i++){
            for(int j=0;j<e[i].length;j++)
                System.out.print(e[i][j]+" ");
            System.out.println();
        }
        System.out.print("最小割为(集合S)：");
        ArrayList<Integer> cut=cut(s);
        for(int i=0;i<cut.size();i++)
            System.out.print(cut.get(i)+" ");
        System.out.println();

    }

    //广度优先搜索在残量图e中寻找s到t的路径
    public boolean bfs(int s,int t,int[] route){
        boolean[] visited=new boolean[vexNum];        //访问数组
        visited[s]=true;

        ArrayDeque<Integer> queue=new ArrayDeque<>();
        route[s]=-1;                                //设s的前一个顶点为-1

        for(int i=0;i<vexNum;i++){
            if(e[s][i]!=0 && !visited[i]){            //在残量矩阵中s到i存在一条路径
                queue.add(i);
                route[i]=s;
                visited[i]=true;
            }
        }
        System.out.println("queue is"+queue);
        while(!queue.isEmpty()){
            //System.out.println(queue);
            int middleVex=queue.poll();
            //System.out.println(queue);
            if(middleVex==t){
                return true;
            }else{
                for(int i=0;i<vexNum;i++){
                    if(e[middleVex][i]!=0 && !visited[i]){
                        queue.add(i);
                        route[i]=middleVex;
                        visited[i]=true;
                    }
                }
            }
        }//while
        return false;
    }

    //求最小割
    //在残量矩阵中,从s开始做一次搜索,从s能达到的所有的顶点都属于集合S
    public ArrayList<Integer> cut(int s){
        boolean[] visited=new boolean[vexNum];
        ArrayList<Integer> cut=new ArrayList<>();    //保存最小割,集合S
        dfs(visited,cut,s);
        return cut;
    }
    //深度优先搜索
    private void dfs(boolean[] visited,ArrayList<Integer> cut,int v){
        cut.add(v);
        visited[v]=true;
        for(int i=0;i<vexNum;i++){
            if(e[v][i]!=0 && !visited[i]){
                dfs(visited,cut,i);
            }
        }
    }
}