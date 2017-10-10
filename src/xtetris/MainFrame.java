/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    public MainFrame() {
        setTitle(" _.:|  XTetris  |:._");
        setSize(600, 400);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        add(createGameAreas(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);
        
        engine = new Engine(this);
        addKeyListener(new Control(engine));
        new Thread(engine).start();
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
        Font font = new Font("Courier New", 1, 20);
        Color fontColor = Color.white;
        
        JLabel lblInstructions = new JLabel(" Instructions ", JLabel.CENTER);
        lblInstructions.setFont(font);
        
        lblInstructions.setBorder(BorderFactory.createRaisedBevelBorder());
        lblInstructions.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                ShowInstructions();
            }
        });
        
        JLabel lblNextFig = new JLabel("   next:");
        lblNextFig.setFont(font);
        
        nextArea = new NextFigureArea();
        nextArea.setPreferredSize(new Dimension(40, 40));
        
        lblScore = new JLabel("   socre:", JLabel.CENTER);
        lblScore.setFont(font);
        
        p.add(lblInstructions);
        p.add(lblNextFig);
        p.add(nextArea);
        p.add(lblScore);
        
        return p;
    }
    
    public void ShowInstructions() {
        
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
