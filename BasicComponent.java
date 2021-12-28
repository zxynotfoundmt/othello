package components;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public abstract class BasicComponent extends JComponent {
    public BasicComponent() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                onMouseClicked();//点击之后发生的事情
            }
        });
    }

    public abstract void onMouseClicked();
}
