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
