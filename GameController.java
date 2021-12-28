package controller;


import model.ChessPiece;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static view.ChessBoardPanel.iCurrentStep;

public class GameController {


    private ChessBoardPanel gamePanel;
    private StatusPanel statusPanel;
    private ChessPiece currentPlayer;
    private int blackScore;
    private int whiteScore;
    private CheatWindow cheat;
    public int[] swapNum;

    public GameController(ChessBoardPanel gamePanel, StatusPanel statusPanel) {
        setGamePanel(gamePanel);
        this.statusPanel = statusPanel;
        this.currentPlayer = ChessPiece.BLACK;
        blackScore = 2;
        whiteScore = 2;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gamePanel.tip(i,j,currentPlayer);
            }
        }
    }

    public void swapPlayer() {
        if(!gamePanel.IfSOneHavePoint())
            return;

        if (havePointToSet(Reverse(currentPlayer))) {
            swapPlayerAnyway();
        } else {
            JOptionPane.showMessageDialog(null, "对方无子可下了，您继续。");
            return;
        }

        if (!CheatWindow.getCheatIdentification()){
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    gamePanel.getChessGrids()[i][j].setIfTips(false);
                    gamePanel.tip(i, j, currentPlayer);
                }
            }
        }
    }

    private boolean havePointToSet(ChessPiece currentPlayer){
        boolean result =gamePanel.havePointToSet(currentPlayer);
        return result;
    }

        public void swapPlayerAnyway(){
        countScore();
        currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
        statusPanel.setScoreText(blackScore, whiteScore);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    gamePanel.getChessGrids()[i][j].setIfTips(false);
                }
            }
    }

    private ChessPiece Reverse(ChessPiece currentPlayer) {
        if(currentPlayer == ChessPiece.WHITE){
            return ChessPiece.BLACK;
        }
        else{return ChessPiece.WHITE;}
    }

    public void countScore() {
        if (currentPlayer == ChessPiece.BLACK) {
            blackScore++;
        } else {
            whiteScore++;
        }
    }

    public void initialScoreandPlayer(){
            blackScore = 2;
            whiteScore = 2;
        this.currentPlayer = ChessPiece.BLACK;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gamePanel.getChessGrids()[i][j].setIfTips(false);
                gamePanel.tip(i,j,currentPlayer);
            }
        }
    }


    public void swapBetween(int rowOfCur, int colOfCur,int[] whichOne) {
        swapNum = new int[8];
        int[][] vector = {{1,0},{-1,0},{0,-1},{0,1},{-1,-1},{1,-1},{-1,1},{1,1}};
        for (int i = 0; i < 8; i++) {
            if(whichOne[i] == 0){continue;}
            int[] vectorOfCurrent = vector[i];
            int[] location = {rowOfCur,colOfCur};
            for(int m = 0; m < 8; m++){
                location[0] = location[0] + vectorOfCurrent[0];
                location[1] = location[1] + vectorOfCurrent[1];
                if (gamePanel.getChess(location[0], location[1]).getColor() != currentPlayer.getColor()) {
                    gamePanel.swapChess(location[0],location[1]);
                    swapNum[i] ++;
                    if(currentPlayer.getColor() == Color.BLACK){
                        blackScore++;
                        whiteScore--;
                    }
                    else if(currentPlayer.getColor() == Color.WHITE){
                        blackScore--;
                        whiteScore++;
                    }
                }
                else {
                    break;
                }
            }
        }
    }

    public void undoSwap(){
        for (int i = 0; i < 8; i++) {
            int m = gamePanel.step[iCurrentStep][0];
            int n = gamePanel.step[iCurrentStep][1];
            if (i == 0){//下
            for (int j = 0; j < swapNum[i]; j++) {
                m += 1;
                gamePanel.getChessGrids()[m][n].setChessPiece(currentPlayer);
                }
            }
            if (i == 1){//上
                for (int j = 0; j < swapNum[i]; j++) {
                    m -= 1;
                    gamePanel.getChessGrids()[m][n].setChessPiece(currentPlayer);
                }
            }
            if (i == 2){//左
                for (int j = 0; j < swapNum[i]; j++) {
                    n -= 1;
                    gamePanel.getChessGrids()[m][n].setChessPiece(currentPlayer);
                }
            }
            if (i == 3){//右
                for (int j = 0; j < swapNum[i]; j++) {
                    n += 1;
                    gamePanel.getChessGrids()[m][n].setChessPiece(currentPlayer);
                }
            }
            if (i == 4){//左上
                for (int j = 0; j < swapNum[i]; j++) {
                    m -= 1;
                    n -= 1;
                    gamePanel.getChessGrids()[m][n].setChessPiece(currentPlayer);
                }
            }
            if (i == 5){//左下
                for (int j = 0; j < swapNum[i]; j++) {
                    m += 1;
                    n -= 1;
                    gamePanel.getChessGrids()[m][n].setChessPiece(currentPlayer);
                }
            }
            if (i == 6){//右上
                for (int j = 0; j < swapNum[i]; j++) {
                    m -= 1;
                    n += 1;
                    gamePanel.getChessGrids()[m][n].setChessPiece(currentPlayer);
                }
            }
            if (i == 7){//右下
                for (int j = 0; j < swapNum[i]; j++) {
                    m += 1;
                    n += 1;
                    gamePanel.getChessGrids()[m][n].setChessPiece(currentPlayer);
                }
            }
        }
        gamePanel.getChessGrids()[gamePanel.step[iCurrentStep][0]][gamePanel.step[iCurrentStep][1]].setChessPiece(null);
    }

    public void unDo(){

        if (currentPlayer == ChessPiece.BLACK) {
            currentPlayer = ChessPiece.WHITE;
        }
        else currentPlayer = ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
        reNewScore();
        iCurrentStep--;//棋步数减一
    }

    public void reNewScore(){
        int a = 0;
        int b = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gamePanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.BLACK)
                    a++;
                if (gamePanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.WHITE)
                    b++;
            }
        }
        this.blackScore = a;
        this.whiteScore = b;

        statusPanel.setScoreText(a,b);
    }

    public ChessPiece getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessBoardPanel getGamePanel() {
        return gamePanel;
    }


    public void setGamePanel(ChessBoardPanel gamePanel) {
        this.gamePanel = gamePanel;
    }


        public void readFileData(File file) {//输入文件
            //todo: read data from file
            int datas[][] = new int[8][8];
            List<String> fileData = new ArrayList<>();
            String strErrorMessage = "";
            String filename = file.getName();

            try {
            FileReader fileReader = new FileReader(file);//打开文件
            BufferedReader bufferedReader = new BufferedReader(fileReader);//缓冲区 一行一行读
            String line;//一行字符串
            while ((line = bufferedReader.readLine()) != null) {
                fileData.add(line);
            }
            fileData.forEach(System.out::println);//一行一行打印内容
            //读取完毕

            if(!filename.contains(".txt")) {//文件空或格式错误
                strErrorMessage = strErrorMessage + "104:文件格式错误 ";
                JOptionPane.showMessageDialog(null, strErrorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {//确定行棋方
                String strCurrentPlayer = fileData.get(0);
                if (strCurrentPlayer.length() ==0)
                    strErrorMessage = strErrorMessage + "103:缺少行棋方 ";
                else if (strCurrentPlayer.equals("BLACK"))
                currentPlayer = ChessPiece.BLACK;
                else if (strCurrentPlayer.equals("WHITE"))
                currentPlayer = ChessPiece.WHITE;
                else strErrorMessage = strErrorMessage + "106:其他错误 ";
                statusPanel.setPlayerText(this.currentPlayer.name());
            }

            //棋盘
            if (fileData.size() < 9) {
                strErrorMessage = strErrorMessage + "101:棋盘错误 ";
            } else {
                boolean er = false;
                for (int i = 1; i < fileData.size(); i++) {
                    String[] str = fileData.get(i).split(" ");
                    if (str.length < 8 && i <= 8) {
                        strErrorMessage = strErrorMessage + "101:棋盘错误 ";
                        er = true;
                    } else {
                        if (i <= 8){
                            for (int j = 0; j < str.length; j++) {
                                int m ;
                                try {
                                    m = Integer.parseInt(str[j]);
                                } catch (Exception e) {
                                    strErrorMessage = strErrorMessage + "101:棋盘错误 ";
                                    er = true;
                                    break;
                                }
                                if (m == -1 || m == 0 || m == 1) {
                                    datas[i - 1][j] = m;
                                } else {
                                    strErrorMessage = strErrorMessage + "102:棋子错误 ";
                                    er = true;
                                    break;
                                }
                            }
                        }

                    }
                    if (er) {//有错误
                        break;
                    } else {//计算双方得分
                        blackScore = 0;
                        whiteScore = 0;
                        for (int x = 0; x < 8; x++) {
                            for (int y = 0; y < 8; y++) {
                                if (datas[x][y] == -1) {
                                    blackScore++;
                                }if (datas[x][y] == 1) {
                                    whiteScore++;
                                }
                            }
                        }
                    }
                }
            }
            if (strErrorMessage.length() == 0 ) {
                if (datas[3][3] == 0 || datas[3][4] == 0 || datas[4][3] == 0 || datas[4][4] == 0) {
                    strErrorMessage = strErrorMessage + "105:非法落子 ";

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            strErrorMessage = strErrorMessage + "106:其他错误 ";

        }

        if(strErrorMessage.length() > 0) {
            System.out.println(strErrorMessage);
            JOptionPane.showMessageDialog(null, strErrorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (strErrorMessage.contains(":") == false) {
            this.gamePanel.loadGame(datas);
            statusPanel.setScoreText(blackScore, whiteScore);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    gamePanel.getChessGrids()[i][j].setIfTips(false);
                    gamePanel.tip(i,j,currentPlayer);
                }
            }
        }
    }


    public void writeDataToFile(File file) {
        //todo: write data into file
        System.out.println(file.getPath() + " | " + file.getName());
        try {if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            String str = this.currentPlayer.name();
            bw.write(str);
            bw.write("\n");

            for (int i = 0; i < 8; i++) {
                 str = "";
                for (int j = 0; j < 8; j++) {
                    ChessPiece chessPiece = this.gamePanel.getChess(i, j);
                    if ( chessPiece !=null && chessPiece == ChessPiece.BLACK) {
                        str = str + "-1";
                    } else if (chessPiece !=null && chessPiece == ChessPiece.WHITE) {
                        str = str + "1";
                    } else {
                        str = str + "0";
                    }
                    if (j < 7) {
                        str = str + " ";
                    }
                }
                bw.write(str);
                if (i < 7) {
                    bw.write("\n");
                }
            }
            for (int i = 0; i < 64; i++) {
                 str = "";
                //int z = this.gamePanel.;
                if (this.gamePanel.step[i][0] != -2) {//有过程数据
                    bw.write("\n");
                    str =   String.valueOf(this.gamePanel.step[i][0]) + " " +
                            String.valueOf(this.gamePanel.step[i][1]) + " " +
                            String.valueOf(this.gamePanel.step[i][2]) + " " ;
                    bw.write(str);
                }
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean canClick(int row, int col) {
        return gamePanel.canClickGrid(row, col, currentPlayer);
    }


    public void setCheat() {
        this.cheat = new CheatWindow();
    }

    public CheatWindow getCheat(){
        return cheat;
    }

    public String getWinner(){
        if(blackScore == whiteScore){
            return "平局";
        }
        else if(blackScore > whiteScore){
            return "黑棋胜";
        }
        else {
            return "白棋胜";
        }
    }

}

