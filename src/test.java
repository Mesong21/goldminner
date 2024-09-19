package mysrc;

import cn.edu.hust.GameItems.GameBoard2048;

public class test {
    public static void main(String[] args) {
        GameBoard2048 gameBoard2048 = new GameBoard2048();
        gameBoard2048.spawnBlock();
        gameBoard2048.spawnBlock();
        gameBoard2048.spawnBlock();
        gameBoard2048.spawnBlock();
        gameBoard2048.spawnBlock();
        gameBoard2048.showBoard();
        gameBoard2048.downMove();
        gameBoard2048.showBoard();
    }
}
