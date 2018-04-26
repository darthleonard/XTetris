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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author darthleonard
 */
public class Engine implements Runnable {
    public static final int ROWS = 22;
    public static final int COLS = 12;
    
    public static final int V_WALL = -1;
    public static final int V_EMPTY = 0;
    public static final int STYLE1 = 1;
    public static final int STYLE2 = 2;
    public static final int STYLE3 = 3;
    
    public static final int DOWN = 0;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    
    
    public static final Color[][] COLORS = {
        { new Color(16, 16, 16), new Color(0, 0, 0),},
        { new Color(22, 127, 57), new Color(4, 76, 41)},         // green
        { new Color(255, 255, 255), new Color(205, 205, 209)},   // white
        { new Color(212, 13, 18),   new Color(148, 9, 13)}       // red
    };
    
    private Piece piece;
    private GameArea area, area1, area2, area3;
    private Penalty penalty;
    private MainFrame mainFrame;
    
    private int map[][];
    
    private boolean playing = true;
    private boolean paused = false;
    private boolean removingPenalty = false;

    private int score = 0;
    private int level = 1;

    public Engine(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        initMap();
        
        penalty = new Penalty();
        
        area1 = this.mainFrame.getArea1();
        area2 = this.mainFrame.getArea2();
        area3 = this.mainFrame.getArea3();
        
        area = area2;
        
        piece = new Piece();
        piece.setPosX((COLS /2) - (piece.getRows() / 2));
        piece.CreateNext();
        
        this.mainFrame.ShowNext(piece.getNextPiece());
        
        area.setPiece(piece);
        
        new Thread(this).start();
    }
    
    public void NewGame() {
        if(!playing) {
            area1.Clear();
            area2.Clear();
            area3.Clear();

            playing = true;
            paused = false;
            removingPenalty = false;
            
            score = 0;
            level = 1;

            initMap();

            penalty = new Penalty();

            area = area2;

            piece = new Piece();
            piece.setPosX((COLS /2) - (piece.getRows() / 2));
            piece.CreateNext();

            mainFrame.ShowNext(piece.getNextPiece());

            area.setPiece(piece);

            new Thread(this).start();
        }
    }
    
    private void initMap() {
        map = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if(i == 0 || i == ROWS-1 || j == 0 || j == COLS-1)
                    map[i][j] = V_WALL;
                else
                    map[i][j] = 0;
            }
        }
    }
    
    /**
     * @param dir [-1 : left] [0 : down] [1 : right]
     */
    private boolean checkCollition(int dir) {
        int y = piece.getPosY();
        int x = piece.getPosX();
        int aux[][] = new int[piece.getRows()][piece.getCols()];
        
        switch(dir) {
            case DOWN:
                for (int row = 0; row < aux.length; row++)
                    for (int col = 0; col < aux[0].length; col++)
                        if(y+row+1 < ROWS && x+col < COLS)
                            aux[row][col] = map[y+row+1][x+col];
                break;
            case LEFT:
                x = x - 1;
                for(int row = 0; row < aux.length; row++)
                    for(int col = 0; col < aux[0].length; col++)
                            if(x+col >= 0)
                                aux[row][col] = map[y+row][x+col];
                break;
            case RIGHT:
                for (int row = 0; row < aux.length; row++)
                    for (int col = 0; col < aux[0].length; col++)
                        if(x+col+1 < COLS)
                            aux[row][col] = map[y+row][x+col+1];
                break;
        }
        
        //printMove(aux);
        
        for (int row = 0; row < aux.length; row++) {
            for (int col = 0; col < aux[0].length; col++) {
                if(aux[row][col] != V_EMPTY && piece.getValue(row, col) != V_EMPTY) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean checkOverlap() {
        return checkOverlap(map);
    }
    
    private boolean checkOverlap(int[][] map) {
        int y = piece.getPosY();
        int x = piece.getPosX();
        int aux[][] = new int[piece.getRows()][piece.getCols()];
        
        for (int row = 0; row < aux.length; row++)
            for (int col = 0; col < aux[0].length; col++)
                if(col+x < COLS)
                    aux[row][col] = map[row+y][col+x];
        
        for (int row = 0; row < aux.length; row++)
            for (int col = 0; col < aux[0].length; col++) {
                if(piece.getValue(row, col) != V_EMPTY && aux[row][col] != V_EMPTY)
                    return true;
            }
        
        return false;
    }
    
    public void SwitchArea(int op) {    
        switch(op) {
            case STYLE1: 
                if(checkOverlap(area1.getMap()))
                    return;
                area = area1; 
                break;
            case STYLE2: 
                if(checkOverlap(area2.getMap()))
                    return;
                area = area2; 
                break;
            case STYLE3: 
                if(checkOverlap(area3.getMap()))
                    return;
                area = area3; 
                break;
        }
        
        area1.setPiece(null);
        area2.setPiece(null);
        area3.setPiece(null);
        area.setPiece(piece);
        map = area.getMap();
        mainFrame.UpdateGameAreas();
    }
    
    public void FigureRotate() {
        piece.Rotate();
        if(checkOverlap())
            piece.Restore();
        area.repaint();
    }
    
    public void MoveFigure(int dir) {
        switch(dir) {
            case DOWN:
                if(!checkCollition(DOWN))
                    piece.setPosY(piece.getPosY() + 1);
                break;
            case RIGHT:
                if(!checkCollition(RIGHT))
                    piece.setPosX(piece.getPosX() + 1);
                break;
            case LEFT:
                if(!checkCollition(LEFT))
                    piece.setPosX(piece.getPosX() - 1);
                break;
        }
        area.repaint();
    }
    
    public void ChangeFigure() {
        piece = piece.getNextPiece();
        piece.CreateNext();
        area.setPiece(piece);
        area.repaint();
        if(checkCollition(DOWN)) {
            JOptionPane.showMessageDialog(mainFrame, "Game Over!", "You lost :(", JOptionPane.ERROR_MESSAGE);
            playing = false;
        }
    }
    
    private void updateMap() {
        int x = piece.getPosX();
        int y = piece.getPosY();
        
        for(int i = y; i < y+piece.getRows(); i++) {
            for (int j = x; j < x+piece.getCols(); j++) {
                if( j < COLS) {
                    if(map[i][j] != piece.getStyle()) {
                        if(piece.getFigure()[i-y][j-x] != V_EMPTY) {
                            map[i][j] = piece.getFigure()[i-y][j-x];
                        }
                    }
                }
            }
        }
    }
    
    public void checkPoints() {
        boolean flagLineCorrect;
        for(int row = ROWS-2; row >= 1; row--) {
            flagLineCorrect = true;
            for(int col = 1; col < COLS-1; col++) {
                if(map[row][col] != area.getStyle()) {
                    flagLineCorrect = false;
                }
            }
            
            if(flagLineCorrect) {
                score++;
                if(score % 4 == 0)
                    level++;
                //findPenalty();
                if(penalty.FindPenalty(map, area)) {
                    removingPenalty = true;
                    area.setPenalty(penalty.getX(), penalty.getY());
                    area.repaint();
                }
                removeLine(row);
                area.repaint();
                mainFrame.UpdateScore(score);
                row++;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void removeLine(int row) {
        for(int i = row; i > 1; i-- ) {
            for(int j = 1; j < COLS-1; j++) {
                if(map[i-1][j] == -1)
                    map[i][j] = V_EMPTY;
                else
                    map[i][j] = map[i-1][j];
            }
        }
    }
    
    public void ShowInstructions() {
        mainFrame.ShowInstructions();
    }
    
    private void printMove(int[][] arr) {
        for (int i = 0; i < piece.getRows(); i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print("[" + arr[i][j] + "]");
            }
            System.out.print("\t");
            for (int j = 0; j < piece.getCols(); j++) {
                System.out.print("[" + piece.getValue(i, j) + "]");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    private void printMap() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print("[" + map[i][j] + "]");
            }
            System.out.println("");
        }
         System.out.println("");
    }
    
    /**
     * change the penalty selected
     */
    public void SearchNextPenalty() {
        penalty.SearchNextPenalty(map, area);
        area.setPenalty(penalty.getX(), penalty.getY());
        area.repaint();
    }
    
    /**
     * Remove the penalty selected
     * Assign the TYPE_Z value to the wrong value at map
     */
    public void RemovePenalty() {
        setRemovingPenalty(false);
        
        penalty.RemovePenalty(map, area);
        area.setPenalty(-2, -2);
        area.repaint();
        mainFrame.UpdateGameAreas();
        checkPoints(); // check for points made cause the depanaitation
    }
     
    @Override
    public void run() {
        while(playing) {
            if(!removingPenalty && !paused) {
                if(!checkCollition(DOWN)) {
                    piece.setPosY(piece.getPosY() + 1);
                }else {
                    if(penalty.CheckPenalty(area, piece)) {
                        penalty.Penalize(piece, area, new GameArea[]{area1, area2, area3});
                    }
                    updateMap();
                    area.setMap(map);
                    checkPoints();
                    ChangeFigure();
                    mainFrame.ShowNext(piece.getNextPiece());
                }
                area.repaint();
            }
            try {
                Thread.sleep(500/level);
            } catch (InterruptedException ex) { Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        String title = (paused) ? " _.:|  XTetris  |:._          (_.:= paused =:._) " : " _.:|  XTetris  |:._ ";
        mainFrame.setTitle(title);
    }

    public boolean isRemovingPenalty() {
        return removingPenalty;
    }

    public void setRemovingPenalty(boolean removingPenalty) {
        this.removingPenalty = removingPenalty;
        String title = (removingPenalty) ? " _.:|  XTetris  |:._          (_.:= select penalty to remove =:._) " : " _.:|  XTetris  |:._ ";
        mainFrame.setTitle(title);
    }
}