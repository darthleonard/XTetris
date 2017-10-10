/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtetris;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desarrollo
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
        { new Color(22, 127, 57), new Color(4, 76, 41)},    // green
        { new Color(255, 255, 255), new Color(205, 205, 209)},   // white
        { new Color(212, 13, 18),   new Color(148, 9, 13)}        // red
    };
    
    private Piece piece;
    private GameArea area;
    private MainFrame mainFrame;
    
    private int map[][];

    public Engine(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        initMap();
        
        area = this.mainFrame.getArea();
        
        piece = new Piece();
        piece.setPosX((COLS /2) - (piece.getRows() / 2));
        piece.setPosY(ROWS / 2);
        piece.CreateNext();
        
        area.setPiece(piece);
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
     * 
     * @param dir
     *  -1 = left
     *  0 = down
     *  1 = right
     */
    private boolean checkCollition(int dir) {
        int y = piece.getPosY();
        int x = piece.getPosX();
        int aux[][] = new int[piece.getRows()][piece.getCols()];;
        
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
    
    public void FigureRotate() {
        piece.Rotate();
        area.repaint();
    }
    
    public void FigureDown() {
        if(!checkCollition(DOWN)) {
            piece.setPosY(piece.getPosY() + 1);
            area.repaint();
        }
    }
    
    public void FigureRight() {
        if(!checkCollition(RIGHT)) {
            piece.setPosX(piece.getPosX() + 1);
            area.repaint();
        }
    }
    
    public void FigureLeft() {
        if(!checkCollition(LEFT)) {
            piece.setPosX(piece.getPosX() - 1);
            area.repaint();
        }
    }
    
    public void ChangeFigure() {
        piece = piece.getNextPiece();
        piece.CreateNext();
        area.setPiece(piece);
        area.repaint();
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
        System.out.println("checkpoints");
        for(int row = ROWS-2; row >= 1; row--) {
            flagLineCorrect = true;
            for(int col = 1; col < COLS-1; col++) {
                if(map[row][col] != area.getStyle()) {
                    flagLineCorrect = false;
                }
            }
            
            if(flagLineCorrect) {
                System.out.println("punto en linea " + row);
                removeLine(row);
                area.repaint();
                row++;
                try {
                    Thread.sleep(100);
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

    @Override
    public void run() {
        while(true) {
            if(!checkCollition(DOWN)) {
                piece.setPosY(piece.getPosY() + 1);
            }else {
                updateMap();
                area.setMap(map);
                checkPoints();
                
                ChangeFigure();
            }
            
            area.repaint();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) { Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
}
