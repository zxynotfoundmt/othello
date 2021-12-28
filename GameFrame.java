package view;


import controller.GameController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class GameFrame extends JFrame {
    public static GameController controller;
    private ChessBoardPanel chessBoardPanel;
    private StatusPanel statusPanel;

    public GameFrame(int frameSize) {

        this.setTitle("2021F CS102A Project Reversi");
        this.setLayout(null);

        //获取窗口边框的长度，将这些值加到主窗口大小上，这能使窗口大小和预期相符
        Insets inset = this.getInsets();
        this.setSize(frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);

        this.setLocationRelativeTo(null);


        chessBoardPanel = new ChessBoardPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.7));
        chessBoardPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() - chessBoardPanel.getHeight()) / 3);

        statusPanel = new StatusPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.1));
        statusPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, 0);
        controller = new GameController(chessBoardPanel, statusPanel);


        this.add(chessBoardPanel);
        this.add(statusPanel);


        JButton restartBtn = new JButton("Restart");
        restartBtn.setSize(120, 50);
        restartBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() + chessBoardPanel.getHeight()) / 2);
        add(restartBtn);

        restartBtn.addActionListener(e -> {
            System.out.println("click restart Btn");

            chessBoardPanel.initialGame();
            statusPanel.setPlayerText("BLACK");
            statusPanel.setScoreText(2,2);
            controller.initialScoreandPlayer();

            repaint();

        });

        JButton loadGameBtn = new JButton("Load");
        //设置外观
        loadGameBtn.setSize(120, 50);
        loadGameBtn.setLocation(restartBtn.getX()+restartBtn.getWidth()+30, restartBtn.getY());
        add(loadGameBtn);

        //设置指令
        loadGameBtn.addActionListener(e -> {
            System.out.println("clicked Load Btn");
            //使用文件选择对话框打开存盘文件，限定txt文件类型
            JFileChooser fileChooser = new JFileChooser("..");
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("txt文件(*.txt)", "txt", "jpg");
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setDialogTitle("打开文件");
            int i = fileChooser.showOpenDialog(this);
            if (i == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                controller.readFileData(file);
            }
        });


        JButton saveGameBtn = new JButton("Save");
        saveGameBtn.setSize(120, 50);
        saveGameBtn.setLocation(loadGameBtn.getX()+restartBtn.getWidth()+30, restartBtn.getY());
        add(saveGameBtn);
        saveGameBtn.addActionListener(e -> {
            System.out.println("clicked Save Btn");
            JFileChooser fileChooser = new JFileChooser("..") {
                public void approveSelection() {
                    File savedFile = getSelectedFile();

                    if (savedFile.exists()) {
                        int overwriteSelect = JOptionPane.showConfirmDialog(this,
                                "文件" + savedFile.getName() + "已存在，是否覆盖?",
                                "是否覆盖?",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);
                        if (overwriteSelect != JOptionPane.YES_OPTION) {
                            return;
                        }
                    }
                    super.approveSelection();
                }
            };
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("txt文本文件(*.txt)", "txt");
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setDialogTitle("保存当前游戏");
            int i = fileChooser.showSaveDialog(this);
            if (i == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String fileName = file.getName();
                if (fileName.indexOf(".txt") == -1) {
                    file = new File(fileChooser.getCurrentDirectory(), fileName+".txt");
                }
                controller.writeDataToFile(file);
            }
        });


        JButton cheatBtn = new JButton("Cheat");
        cheatBtn.setSize(120,50);
        cheatBtn.setLocation(saveGameBtn.getX() + restartBtn.getWidth() + 30,restartBtn.getY());
        add(cheatBtn);
        cheatBtn.addActionListener(e -> {
            System.out.println("clicked Cheat Btn");
            if(CheatWindow.getCheatIdentification()){
                CheatWindow.escapeFromCheat();
                JOptionPane.showMessageDialog(null, "你成功退出了作弊模式");
            }
            else {
                controller.setCheat();}
        });

        JButton undoBtn = new JButton ("Undo");
        undoBtn.setSize(100,50);
        undoBtn.setLocation(saveGameBtn.getX() + restartBtn.getWidth() + 50,20);
        add(undoBtn);
        undoBtn.addActionListener(e->{
            System.out.println("clicked Undo Btn");
            //todo: complete the method
            controller.undoSwap();
            controller.unDo();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessBoardPanel.getChessGrids()[i][j].setIfTips(false);
                    chessBoardPanel.tip(i,j,controller.getCurrentPlayer());
                }
            }
            repaint();
                });

        JButton SndBtn = new JButton ("Surrender");
        SndBtn.setSize(100,50);
        SndBtn.setLocation(10,20);
        add(SndBtn);
        SndBtn.addActionListener(e->{
            System.out.println("clicked Surrender Btn");
            JOptionPane.showMessageDialog(null,"你确定要投降吗？");
            
        });

        JLabel backGround = new JLabel();
        Icon icon = new ImageIcon("image/img.jpg");
        backGround.setIcon(icon);
        backGround.setHorizontalAlignment(SwingConstants.CENTER);
        backGround.setBounds(0,0,this.getWidth(),this.getHeight());
        backGround.setOpaque(true);
        this.add(backGround);

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }
}
