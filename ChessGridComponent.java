package components;

import model.*;
import view.CheatWindow;
import view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChessGridComponent extends BasicComponent {
    public static int chessSize;//棋盘尺寸
    public static int gridSize;//格子尺寸
    public boolean ifTips;

    public static Color gridColor = new Color(218, 218, 153, 255);
    public static Color tipsColor = new Color(162, 106, 62, 255);

    private ChessPiece chessPiece;
    private int row;
    private int col;


    public ChessGridComponent(int row, int col) {
        super();
        this.setSize(gridSize, gridSize);

        this.row = row;
        this.col = col;

    }

    @Override
    public void onMouseClicked() {
        System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
        //todo: complete mouse click method
        //todo:调整到这个的末尾，每次结束时检测
        //todo:canclick 是要输入当前玩家的，不是无条件检测，注意你使用是否正确
        if (CheatWindow.getCheatIdentification()) {//处于双方都作弊模式
            if(GameFrame.controller.getCheat().getCheatSide() == ChessPiece.GRAY){//作弊1
                if(this.chessPiece == null){
                    this.chessPiece = GameFrame.controller.getCurrentPlayer();
                    if (GameFrame.controller.canClick(row, col)) {
                        GameFrame.controller.swapBetween(row, col
                                , GameFrame.controller.getGamePanel().whichOne(row, col, GameFrame.controller.getCurrentPlayer()));
                    }
                    GameFrame.controller.swapPlayerAnyway();
                    int iStep = GameFrame.controller.getGamePanel().increaseStep();
                    GameFrame.controller.getGamePanel().recordStep(iStep, row, col);
                }
            } else {
                //作弊方
                if (GameFrame.controller.getCurrentPlayer() == GameFrame.controller.getCheat().getCheatSide()) {
                    if (this.chessPiece == null) {
                        this.chessPiece = GameFrame.controller.getCurrentPlayer();
                        if (GameFrame.controller.canClick(row, col)) {
                            GameFrame.controller.swapBetween(row, col
                                    , GameFrame.controller.getGamePanel().whichOne(row, col, GameFrame.controller.getCurrentPlayer()));
                        }
                        GameFrame.controller.swapPlayer();
                        int iStep = GameFrame.controller.getGamePanel().increaseStep();
                        GameFrame.controller.getGamePanel().recordStep(iStep, row, col);
                    }
                } else {//非作弊方
                    if (GameFrame.controller.canClick(row, col)) {
                        if (this.chessPiece == null) {
                            this.chessPiece = GameFrame.controller.getCurrentPlayer();
                            GameFrame.controller.swapBetween(row, col
                                    , GameFrame.controller.getGamePanel().whichOne(row, col, GameFrame.controller.getCurrentPlayer()));
                            GameFrame.controller.swapPlayerAnyway();
                            int iStep = GameFrame.controller.getGamePanel().increaseStep();
                            GameFrame.controller.getGamePanel().recordStep(iStep, row, col);
                        }
                    }
                }
            }
        }//非作弊模式
        else {if (GameFrame.controller.canClick(row, col)) {
            if (this.chessPiece == null) {
                this.chessPiece = GameFrame.controller.getCurrentPlayer();
                GameFrame.controller.swapBetween(row, col
                        , GameFrame.controller.getGamePanel().whichOne(row, col, GameFrame.controller.getCurrentPlayer()));
                GameFrame.controller.swapPlayer();
                int iStep = GameFrame.controller.getGamePanel().increaseStep();
                GameFrame.controller.getGamePanel().recordStep(iStep, row, col);
            }
        }
        }
        repaint();
        if(!GameFrame.controller.getGamePanel().IfSOneHavePoint()){
            GameFrame.controller.reNewScore();
            String s = GameFrame.controller.getWinner();
            JOptionPane.showMessageDialog(null,s);
        }
    }

    public void swapChessPiece(){
        if(chessPiece == ChessPiece.BLACK){
            chessPiece = ChessPiece.WHITE;
        }
        else if(chessPiece == ChessPiece.WHITE){
            chessPiece = ChessPiece.BLACK;
        }
        this.repaint();
    }//new added




    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void drawPiece(Graphics g) {
        g.setColor(gridColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
        if (this.chessPiece != null) {
            g.setColor(chessPiece.getColor());
            g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2,chessSize , chessSize);
        }
    }

    public void drawTips(Graphics g){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ifTips) {
                    g.setColor(tipsColor);
                    g.fillOval(70 * i + 23, 70 * j + 23, 25, 25);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        drawPiece(g);
        drawTips(g);
        repaint();
    }

    public void setIfTips(boolean ifTips) {
        this.ifTips = ifTips;
    }


}
