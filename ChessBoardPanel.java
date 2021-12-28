package view;

import components.ChessGridComponent;
import model.ChessPiece;
import view.CheatWindow;

import javax.swing.*;
import java.awt.*;

public class ChessBoardPanel extends JPanel {
    private final int CHESS_COUNT = 8;
    private ChessGridComponent[][] chessGrids;
    public static int[][] step;
    public static int iCurrentStep;

    public ChessBoardPanel(int width, int height) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.white);
        int length = Math.min(width, height);
        this.setSize(length, length);
        ChessGridComponent.gridSize = length / CHESS_COUNT;
        ChessGridComponent.chessSize = (int) (ChessGridComponent.gridSize * 0.8);
        System.out.printf("width = %d height = %d gridSize = %d chessSize = %d\n",
                width, height, ChessGridComponent.gridSize, ChessGridComponent.chessSize);

        this.step = new int[80][3];
        this.iCurrentStep = -1;
        initialChessGrids();//return empty chessboard
        initialGame();//add initial four chess
        repaint();
    }

    /**
     * set an empty chessboard
     * 这里不用改
     */
    public void initialChessGrids() {
        //创建棋盘数组
        chessGrids = new ChessGridComponent[CHESS_COUNT][CHESS_COUNT];

        //draw all chess grids
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                //创建一个小格子
                ChessGridComponent gridComponent = new ChessGridComponent(i, j);
                //设置小格子在棋盘的位置
                gridComponent.setLocation(j * ChessGridComponent.gridSize, i * ChessGridComponent.gridSize);
                //将小格子添加到数组里
                chessGrids[i][j] = gridComponent;
                //将小格子的组件添加到棋盘组件里
                this.add(chessGrids[i][j]);

            }
        }
    }

    /**
     * initial origin four chess
     * 这里需要改
     */
    public void initialGame() {
        clearChessPieces();

        chessGrids[3][3].setChessPiece(ChessPiece.BLACK);
        chessGrids[3][4].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][3].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][4].setChessPiece(ChessPiece.BLACK);

        repaint();
    }

    public void clearChessPieces(){
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j <CHESS_COUNT ; j++) {
                chessGrids[i][j] .setChessPiece(null);
            }
        }
        repaint();
    }
    public boolean IfSOneHavePoint(){
        boolean result = false;
        if(havePointToSet(ChessPiece.BLACK)){
            result = true;
        }
        else if(havePointToSet(ChessPiece.WHITE)){
            result = true;
        }
        return result;
    }

    public boolean havePointToSet(ChessPiece currentPlayer){
        boolean result = false;
        for (ChessGridComponent[] ChessRow : chessGrids) {
            if (result == true){break;}
            for (ChessGridComponent OneChess : ChessRow) {
                if(result == true){break;}
                if(OneChess.getChessPiece() == null){
                    if(canClickGrid(OneChess.getRow(),OneChess.getCol(),currentPlayer)){
                        result = true;
                    }
                }
                else continue;
            }
        }
        return result;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public static Color reverseColor(Color myColor){
        if (myColor == Color.BLACK) {
            myColor = Color.white;
            return myColor;
        }else if(myColor == Color.white) {
            myColor = Color.BLACK;
            return myColor ;}
        return null;
    }

    public boolean find (int row,int col,ChessPiece currentPlayer,int rowmove,int colmove) {
        boolean flag = false;

            do {row += rowmove;
                col += colmove;
            } while (row >= 0 && row < 8 && col >= 0 && col < 8 && chessGrids[row][col].getChessPiece()!= null &&chessGrids[row][col].getChessPiece().getColor() ==reverseColor(currentPlayer.getColor()));

        if (row >= 0 && row < 8 && col >= 0 && col < 8 &&chessGrids[row][col].getChessPiece() != null&&
                chessGrids[row-rowmove][col - colmove].getChessPiece() != null&&
                chessGrids[row][col].getChessPiece().getColor() == currentPlayer.getColor()
                && chessGrids[row - rowmove][col - colmove].getChessPiece().getColor() == reverseColor(currentPlayer.getColor())) {
            flag = true;
        }
        return flag;
    }

    public  boolean canClickGrid(int row, int col, ChessPiece currentPlayer) {
        if(find(row,col,currentPlayer,1,0))
            return true;
        if(find(row,col,currentPlayer,-1,0))
            return true;
        if(find(row,col,currentPlayer,0,-1))
            return true;
        if(find(row,col,currentPlayer,0,1))
            return true;
        if(find(row,col,currentPlayer,-1,-1))
            return true;
        if(find(row,col,currentPlayer,1,-1))
            return true;
        if(find(row,col,currentPlayer,-1,1))
            return true;
        if(find(row,col,currentPlayer,1,1))
            return true;
        return false;
    }

    public int[] whichOne(int row, int col, ChessPiece currentPlayer) {
       int[] whichone = new int[8];
        if(find(row,col,currentPlayer,1,0))
            whichone[0] = 1;
        if(find(row,col,currentPlayer,-1,0))
            whichone[1] = 1;
        if(find(row,col,currentPlayer,0,-1))
            whichone[2] = 1;
        if(find(row,col,currentPlayer,0,1))
            whichone[3] = 1;
        if(find(row,col,currentPlayer,-1,-1))
            whichone[4] = 1;
        if(find(row,col,currentPlayer,1,-1))
            whichone[5] = 1;
        if(find(row,col,currentPlayer,-1,1))
            whichone[6] = 1;
        if(find(row,col,currentPlayer,1,1))
            whichone[7] = 1;
        return whichone;
    }

    public void swapChess(int row, int col){
        chessGrids[row][col].swapChessPiece();
    }// new added

    public ChessPiece getChess(int row, int col){
        return chessGrids[row][col].getChessPiece();
    }//new added


    public ChessGridComponent[][] getChessGrids() {return chessGrids;}


    public void loadGame (int[][] data){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(data[i][j] == -1){
                    chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                }
                if(data[i][j] == 1){
                    chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                }
                if (data[i][j] == 0){
                    chessGrids[i][j].setChessPiece(null);
                }
            }
        }
        repaint();
    }
    public boolean tip(int i,int j,ChessPiece currentPlayer){
        if(chessGrids[i][j].getChessPiece() == null)
            if (canClickGrid(i, j, currentPlayer))
                chessGrids[i][j].setIfTips(true);
        return chessGrids[i][j].ifTips;
    }

    public void recordStep(int iStep,int x,int y){
        this.step[iStep][0] = x;
        this.step[iStep][1] = y;
        if(CheatWindow.getCheatIdentification()) {
            this.step[iStep][2] = 1;
        } else {
            this.step[iStep][2] = 0;
        }
    }

    public int increaseStep(){
        this.iCurrentStep++;
        return iCurrentStep;
    }


}


