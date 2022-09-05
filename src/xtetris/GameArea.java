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
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author darthleonard
 */
public class GameArea extends JPanel {
    private final Color[] wallColor = new Color[]{Color.lightGray, Color.gray, Color.darkGray};
    private final Font font = new Font("Courier New", 1, 12);
    
    private Graphics2D g;
    
    private int map[][];
    private Piece piece;
    private int style;
    private int penaltyX = -1;
    private int penaltyY = -1;
    
    private int dx;
    private int dy;
    
    public GameArea(int style) {
        this.style = style;
        initMap();
        super.setBackground(new Color(210,210,210));
    }

    private void initMap() {
        map = new int[Engine.ROWS][Engine.COLS];
        for (int i = 0; i < Engine.ROWS; i++) {
            for (int j = 0; j < Engine.COLS; j++) {
                if(i == 0 || i == Engine.ROWS-1 || j == 0 || j == Engine.COLS-1)
                    map[i][j] = Engine.V_WALL;
                else
                    map[i][j] = 0;
            }
        }
    }
    
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        g = (Graphics2D) grphcs;
        dx = getWidth() / Engine.COLS;
        dy = getHeight() / Engine.ROWS;
        
        drawMap();
        drawPiece();
        drawPenalty();
        g.setColor(Engine.COLORS[getStyle()][0]);
        g.setFont(font);
        g.drawString("Area " + getStyle(), getWidth()/2, getHeight()-2);
    }
    
    private void drawMap() {
        for (int row = 0; row < Engine.ROWS; row++) {
            for (int col = 0; col < Engine.COLS; col++) {
                Color [] color = (map[row][col] != -1) ? Engine.COLORS[map[row][col]] : wallColor;
                drawCell(col*dx, row*dy, color);
            }
        }
    }
    
    protected void drawPiece() {
        if(piece != null) {
            int x;
            int y;
            for (int row = 0; row < piece.getRows(); row++) {
                for (int col = 0; col < piece.getCols(); col++) {
                    x = piece.getPosX() + col;
                    y = piece.getPosY()+row;
                    if(piece.getValue(row, col) != Engine.V_EMPTY)
                        drawCell(x*dx, y*dy, Engine.COLORS[piece.getStyle()]);
                }
            }
        }
    }
    
    private void drawCell(int x, int y, Color[] color) {
        g.setPaint(new GradientPaint(11, 10, color[0], 25, 25, color[1], true));
        g.fillRect(x, y, dx, dy);
        
        g.setColor(color[0]);
        g.drawLine(x, y, x+dx-1, y);
        g.drawLine(x, y, x, y+dy-1);
        
        g.setColor(color[1]);
        g.drawLine(x+dx-1, y, x+dx-1, y+dy-1);
        g.drawLine(x, y+dy-1, x+dx-1, y+dy-1);
    }
    
    private void drawPenalty() {
        g.setColor(Color.cyan);
        int x = penaltyX*dx, y = penaltyY*dy;
        g.drawLine(x + dx/2, dy, x + dx/2, getHeight()-(dy*2));
        g.drawLine(dx, y + dy/2, getWidth()-dx, y + dy/2);
        for (int i = 0; i < 2; i++) {
            g.drawRect(x-i, y-i, dx+i, dy+i);
        }
    }

    public void Clear() {
        piece = null;
        initMap();
        penaltyX = -1;
        penaltyY = -1;
        repaint();
    }
    
    public void setPenalty(int penaltyX, int penaltyY) {
        this.penaltyX = penaltyX;
        this.penaltyY = penaltyY;
    }
    
    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}
