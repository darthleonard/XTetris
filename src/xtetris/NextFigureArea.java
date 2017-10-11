/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtetris;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Leonardo Gonzalez Caracosa
 */
public class NextFigureArea extends JPanel {
    
    private Piece piece;
    
    public NextFigureArea() {
        super.setBackground(Color.black);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        int dx = getWidth() / piece.getCols();
        int dy = getHeight() / piece.getRows();
        
        Graphics2D g = (Graphics2D) grphcs;
        
        for (int i = 0; i < piece.getRows(); i++) {
            for (int j = 0; j < piece.getCols(); j++) {
                if(piece.getValue(i, j) == Engine.V_EMPTY)
                    g.setPaint(new GradientPaint(10, 10, Engine.COLORS[0][0], 25, 25, Engine.COLORS[0][1], true));
                else 
                    g.setPaint(new GradientPaint(10, 10, Engine.COLORS[piece.getStyle()][0], 25, 25, Engine.COLORS[piece.getStyle()][1], true));
                
                g.fillRect(j*dx+1, i*dy+1, dx-1, dy-1);
            }
        }
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
