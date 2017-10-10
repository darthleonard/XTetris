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
 * @author darthleonard
 */
public class GameArea extends JPanel {

    private Graphics2D g;
    private Color[] wallColor = new Color[]{Color.lightGray, Color.gray, Color.darkGray};
    
    private int map[][];
    private Piece piece;
    private int style;
    
    private int dx;
    private int dy;
    
    public GameArea(int style) {
        this.style = style;
        
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
        g = (Graphics2D) grphcs;
        dx = getWidth() / Engine.COLS;
        dy = getHeight() / Engine.ROWS;
        
        drawMap();
        drawPiece();
    }
    
    private void drawMap() {
        for (int row = 0; row < Engine.ROWS; row++) {
            for (int col = 0; col < Engine.COLS; col++) {
                Color [] color = (map[row][col] != -1) ? Engine.COLORS[map[row][col]] : wallColor;
                drawCell(col*dx, row*dy, color);
            }
        }
    }
    
    private void drawPiece() {
        if(piece != null) {
            
            int x;
            int y;
            Color[] color;
            for (int row = 0; row < piece.getRows(); row++) {
                for (int col = 0; col < piece.getCols(); col++) {
                    x = piece.getPosX() + col;
                    y = piece.getPosY()+row;
                    if(piece.getValue(row, col) != Engine.V_EMPTY)
                        drawCell(x*dx, y*dy, Engine.COLORS[piece.getStyle()]);
                }
            }
            
            // dibuja el contorno de la pieza actual
            for (int row = 0; row < piece.getRows(); row++) {
                for (int col = 0; col < piece.getCols(); col++) {
                    g.setColor(Color.yellow);
                    g.drawRect((row+piece.getPosX())*dx, (col+piece.getPosY())*dy, dx, dy);
                }
            }
        }
    }
    
    private void drawCell(int x, int y, Color[] color) {
        g.setPaint(new GradientPaint(100, 100, color[0], 250, 250, color[1], true));
        g.fillRect(x, y, dx, dy);
        
        g.setColor(color[0]);
        g.drawLine(x, y, x+dx-1, y);
        g.drawLine(x, y, x, y+dy-1);
        
        g.setColor(color[1]);
        g.drawLine(x+dx-1, y, x+dx-1, y+dy-1);
        g.drawLine(x, y+dy-1, x+dx-1, y+dy-1);
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
