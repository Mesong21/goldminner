package cn.edu.hust.ui;

import cn.edu.hust.FileUtils.RWCache;
import cn.edu.hust.GameItems.MineSweeperGameBoard;
import cn.edu.hust.GameItems.MineSweeperJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MineSweeperJFrame extends JFrame implements ActionListener {

    JMenu changeDifficulty= new JMenu("难度");
    JMenuItem exitGameItem = new JMenuItem("回到大厅");
    JMenuItem easyItem = new JMenuItem("简单");
    JMenuItem mediumItem = new JMenuItem("普通");
    JMenuItem hardItem = new JMenuItem("困难");
    int scale;      //棋盘大小
    ArrayList<ArrayList<MineSweeperJButton>> buttonList;        //按钮集合
    int[][] board;      //棋盘
    int[][] boardStatus;        //棋盘按钮是否可按
    int clearButtonN=0;       //记录累计点开的按钮数
    int bombN;      //记录本棋盘的炸弹数
    int buttonN;        //按钮总数
    public MineSweeperJFrame() throws IOException {
        initJFrame();
        initJMenuBar();
        initGameBoard();

        this.setVisible(true);
    }

    private void initJFrame(){       //初始化JFrame
        this.setSize(1920,1080);
        this.setTitle("扫雷");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initJMenuBar() {
        //菜单初始化
        JMenuBar jMenuBar = new JMenuBar();

        JMenu menu = new JMenu("菜单");


        exitGameItem.addActionListener(this);
        easyItem.addActionListener(this);
        mediumItem.addActionListener(this);
        hardItem.addActionListener(this);

        menu.add(changeDifficulty);
        menu.add(exitGameItem);
        changeDifficulty.add(easyItem);
        changeDifficulty.add(mediumItem);
        changeDifficulty.add(hardItem);

        jMenuBar.add(menu);

        this.setJMenuBar(jMenuBar);
    }

    private void initGameBoard() throws IOException {
        char dif = RWCache.readFromCache("cache_difficulty");
        MineSweeperGameBoard gameBoard = new MineSweeperGameBoard(dif);
        scale = gameBoard.scale;        //棋盘大小
        board = gameBoard.board;
        bombN = gameBoard.bombN;
        boardStatus = new int[scale][scale];
        buttonN = scale*scale;      //按钮数量
        for (int i = 0; i < scale; i++) {
            for (int j = 0; j < scale; j++) {
                boardStatus[i][j]=0;        //代表可点击
            }
        }

        buttonList = new ArrayList<>(scale);
        for (int i = 0; i <scale; i++) {
            ArrayList<MineSweeperJButton> buttonRow = new ArrayList<>(scale);
            for (int j = 0; j < scale; j++) {
                MineSweeperJButton button = new MineSweeperJButton(i,j);
                if(dif == '1'){
                    button.setBounds(600+80*j,180+80*i,80,80);
                }else if(dif == '2'){
                    button.setBounds(480+60*j,60+60*i,60,60);
                }else if(dif == '3'){
                    button.setBounds(460+45*j,33+45*i,45,45);
                }
                button.setBackground(Color.white);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.addActionListener(this);
                button.setBorder(BorderFactory.createRaisedBevelBorder());

                this.getLayeredPane().add(button,JLayeredPane.PALETTE_LAYER);
                buttonRow.add(button);
            }
            buttonList.add(buttonRow);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == easyItem){
            System.out.println("easy");
            try {
                RWCache.writeIntoCache('1',"cache_difficulty");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            try {
                MineSweeperJFrame mineSweeperJFrame = new MineSweeperJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }else if(source == mediumItem){
            System.out.println("medium");
            try {
                RWCache.writeIntoCache('2',"cache_difficulty");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            try {
                MineSweeperJFrame mineSweeperJFrame = new MineSweeperJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }else if(source == hardItem){
            System.out.println("hard");
            try {
                RWCache.writeIntoCache('3',"cache_difficulty");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            try {
                MineSweeperJFrame mineSweeperJFrame = new MineSweeperJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }else if(source == exitGameItem){
            System.out.println("退出扫雷游戏");
            this.dispose();
            try {
                LobbyJFrame lobbyJFrame = new LobbyJFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        for (int i = 0; i < scale; i++) {
            for (int j = 0; j < scale; j++) {
                if(source == buttonList.get(i).get(j)){
                    System.out.println(i+" "+j);

                    onClickAButton(i,j);

                }
            }
        }
    }

    private void onClickAButton(int y,int x){       //当点击棋盘某按钮后的事件
        if(y<0||y>scale-1||x<0||x>scale-1||boardStatus[y][x]!=0){
            return;
        }
        boardStatus[y][x]=1;
        if(board[y][x]==-1){
            System.out.println("游戏失败");
            MSGameOverJFrame MSGameOverJFrame = new MSGameOverJFrame(this);
        }else if(board[y][x]==0){
            buttonList.get(y).get(x).setBackground(Color.lightGray);
            buttonList.get(y).get(x).setText(String.valueOf(board[y][x]));
            onClickAButton(y-1,x-1);
            onClickAButton(y-1,x);
            onClickAButton(y-1,x+1);
            onClickAButton(y,x-1);
            onClickAButton(y,x);
            onClickAButton(y+1,x+1);
            onClickAButton(y+1,x-1);
            onClickAButton(y+1,x);
            onClickAButton(y+1,x+1);
        }else if(board[y][x]>0){
            buttonList.get(y).get(x).setBackground(Color.lightGray);
            buttonList.get(y).get(x).setText(String.valueOf(board[y][x]));
        }
        clearButtonN++;
        if(clearButtonN == buttonN - bombN){
            this.dispose();
            VictoryJFrame victoryJFrame = new VictoryJFrame();
        }
    }


}
