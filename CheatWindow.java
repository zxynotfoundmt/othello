package view;

import model.ChessPiece;

import javax.swing.*;
import java.awt.*;


public class CheatWindow extends JFrame {
    private JButton cheatWhite,cheatBlack,cheatGray;
    private ChessPiece cheatSide;
    public static boolean cheatIdentification = false;

    public CheatWindow() {
        cheatIdentification = true;
        this.setTitle("CHEAT");
        this.setLayout(null);

        this.setLocation(800, 300);
        this.setSize(400, 300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        setLayout(new GridLayout(1,2,3,3));
        cheatWhite = new JButton("WHITE");
        //cheatWhite.setSize(120, 50);
        //cheatWhite.setLocation(this.getWidth()/2 + 20,this.getHeight()/2);
        add(cheatWhite);
        cheatWhite.addActionListener(e -> {
            cheatSide = ChessPiece.WHITE;
            this.dispose();
        });
        cheatBlack = new JButton("BLACK");
        //cheatBlack.setSize(120,50);
        //cheatBlack.setLocation(this.getWidth() - 100,this.getHeight()/2);
        add(cheatBlack);
        cheatBlack.addActionListener(e -> {
            cheatSide = ChessPiece.BLACK;
            this.dispose();
        });
        cheatGray = new JButton("BLACK and WHITE");
        add(cheatGray);
        cheatGray.addActionListener(e -> {
            cheatIdentification = true;
            cheatSide = ChessPiece.GRAY;
            this.dispose();
        });

    }

    public ChessPiece getCheatSide() {
        return cheatSide;
    }

    public static boolean getCheatIdentification(){
        return cheatIdentification;
    }

    public static void escapeFromCheat(){
        cheatIdentification = false;
    }

}
