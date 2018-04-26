/*
 * Copyright (C) 2017 Leonardo Gonzalez Caracosa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package xtetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author darthleonard
 */
public class MainFrame extends JFrame {

    private GameArea area1, area2, area3;
    private NextFigureArea nextArea;
    private JLabel lblScore;
    private Engine engine;
    
    String strInstructions =
            "" +
            "Game play:\n\n" + 
            "1.- Move the figure with the KEYBOARD ARROWS.\n" +
            "2.- Change figure's position with ARROW UP key.\n" +
            "3.- Switch between game areas pressing\n     1(Green), 2(white) or 3(red) key.\n" +
            "\nPenalties:\n\n" +
            "1.- Penalty to remove must be in the area that\n     you scored.\n" +
            "2.- Change the selected penalty, use SPACE key.\n" +
            "3.- To remove the selected penalty, hit ENTER.\n"+
            "\n\n* Press F1 or clic on \"Instructions\" button\n   to open this dialog again." +
            "\n* Press left shift key to switch the pasue mode"+
            "\n* Press F2 to start a new game once frist one has\n   finished.";
    
    public MainFrame() {
        setTitle(" _.:|  XTetris  |:._");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        add(createGameAreas(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);
        
        engine = new Engine(this);
        addKeyListener(new Control(engine));
        
        setVisible(true);
    }

    private JPanel createGameAreas() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 3));
        
        area1 = new GameArea(Engine.STYLE1);
        area2 = new GameArea(Engine.STYLE2);
        area3 = new GameArea(Engine.STYLE3);
        
        p.add(area1);
        p.add(area2);
        p.add(area3);
        
        return p;
    }
    
    private JPanel createControlPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.gray);
        
        Font font = new Font("Courier New", 1, 20);
        Color fontColor = Color.white;
        
        JLabel lblInstructions = new JLabel(" Instructions ", JLabel.CENTER);
        lblInstructions.setFont(font);
        lblInstructions.setForeground(fontColor);
        
        lblInstructions.setBorder(BorderFactory.createRaisedBevelBorder());
        lblInstructions.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                lblInstructions.setBorder(BorderFactory.createLoweredBevelBorder());
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                lblInstructions.setBorder(BorderFactory.createRaisedBevelBorder());
                ShowInstructions();
            }
        });
        
        JLabel lblNextFig = new JLabel("   next:");
        lblNextFig.setFont(font);
        lblNextFig.setForeground(fontColor);
        
        nextArea = new NextFigureArea();
        nextArea.setPreferredSize(new Dimension(40, 40));
        
        lblScore = new JLabel("   socre: 0", JLabel.CENTER);
        lblScore.setFont(font);
        lblScore.setForeground(fontColor);
        
        p.add(lblInstructions);
        p.add(lblNextFig);
        p.add(nextArea);
        p.add(lblScore);
        
        return p;
    }
    
    public void ShowInstructions() {
        Object[] options = {"Got it!"};
        engine.setPaused(true);
        JOptionPane.showOptionDialog(
            null,
            strInstructions, 
            "How to...",
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );
        if(true)
            engine.setPaused(false);
    }
    
    public void UpdateGameAreas() {
        area1.repaint();
        area2.repaint();
        area3.repaint();
    }
    
    public void ShowNext(Piece piece) {
        nextArea.setPiece(piece);
        nextArea.repaint();
    }
    
    public void UpdateScore(int score) {
        lblScore.setText("   score: " + score);
    }
    
    public GameArea getArea1() {
        return area1;
    }

    public void setArea1(GameArea area1) {
        this.area1 = area1;
    }

    public GameArea getArea2() {
        return area2;
    }

    public void setArea2(GameArea area2) {
        this.area2 = area2;
    }

    public GameArea getArea3() {
        return area3;
    }

    public void setArea3(GameArea area3) {
        this.area3 = area3;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
