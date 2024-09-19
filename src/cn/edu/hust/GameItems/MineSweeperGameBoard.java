package cn.edu.hust.GameItems;

import java.util.Random;

public class MineSweeperGameBoard {
    public char difficulty;        //游戏难度
    public int scale;      //棋盘大小
    public int bombN;      //炸弹数量
    public int[][] board;        //棋盘  数字代表其附近的炸弹数量 -1表示炸弹
    public MineSweeperGameBoard(char difficulty){
        if(difficulty == '1'){
            scale = 9;
            bombN = 10;
        } else if (difficulty == '2') {
            scale = 16;
            bombN = 40;
        } else if (difficulty == '3') {
            scale = 22;
            bombN = 60;
        }
        initBoard();
    }

    private void initBoard(){
        board = new int[scale][scale];
        for (int i = 0; i < scale; i++) {
            for (int j = 0; j < scale; j++) {
                board[i][j]=0;
            }
        }
//        showBoard();
        int n = bombN;
        while(n>0){
            Random r = new Random();
            double ry = r.nextDouble();
            double rx = r.nextDouble();
            int y = (int)(ry*(scale));
            int x = (int)(rx*(scale));
//            System.out.println("y:"+y+"  x:"+x);
            if(board[y][x] != -1) {
                board[y][x] = -1;
                n--;
                if(y>0){
                    if(board[y-1][x]!=-1){
                        board[y-1][x]=board[y-1][x]+1;
                    }
                    if(x<scale-1){
                        if(board[y-1][x+1]!=-1){
                            board[y-1][x+1]=board[y-1][x+1]+1;
                        }
                    }
                    if(x>0){
                        if(board[y-1][x-1]!=-1){
                            board[y-1][x-1]=board[y-1][x-1]+1;
                        }
                    }
                }
                if(y<scale-1){
                    if(board[y+1][x]!=-1){
                        board[y+1][x]=board[y+1][x]+1;
                    }
                    if(x<scale-1){
                        if(board[y+1][x+1]!=-1){
                            board[y+1][x+1]=board[y+1][x+1]+1;
                        }
                    }
                    if(x>0){
                        if(board[y+1][x-1]!=-1){
                            board[y+1][x-1]=board[y+1][x-1]+1;
                        }
                    }
                }
                if(x<scale-1){
                    if(board[y][x+1]!=-1){
                        board[y][x+1]=board[y][x+1]+1;
                    }
                }
                if(x>0){
                    if(board[y][x-1]!=-1){
                        board[y][x-1]=board[y][x-1]+1;
                    }
                }
            }
        }
        showBoard();
    }

    public void showBoard(){
        System.out.println("==扫雷游戏棋盘初始化：");
        for (int i = 0; i < scale; i++) {
            for (int j = 0; j < scale; j++) {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
