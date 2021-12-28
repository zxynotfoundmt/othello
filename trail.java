import view.ChessBoardPanel;

import java.awt.*;

public class trail {
    public static void main(String[] args) {
        ChessBoardPanel a = new ChessBoardPanel(8,8);
        Color color = Color.white;
        System.out.println(ChessBoardPanel.reverseColor(color));
    }
}
