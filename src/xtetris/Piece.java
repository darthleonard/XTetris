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

/**
 *
 * @author darthleonard
 */
public class Piece {
    public static final int O = 0;
    public static final int I = 1;
    public static final int S = 2;
    public static final int Z = 3;
    public static final int L = 4;
    public static final int J = 5;
    public static final int T = 6;
    
    private int posX;
    private int posY;
    private int style;
    
    private int[][] figure;
    private int[][] figureResp;
    
    private Piece nextPiece = null;

    public Piece() {
        setStyle((int) (Math.random() * 3) + 1);
        setFigure(chooseFigure((int) (Math.random() * 7)));
        setPosX((Engine.COLS / 2) - (figure[0].length / 2));
        setPosY(0);
        figureResp = figure;
    }
    
    private int[][] chooseFigure(int op) {
        int o = Engine.V_EMPTY;
        int x = getStyle();
        switch(op) {
            case O:
                return new int[][] {
                    {x,x},
                    {x,x}
                };
            case I:
                return new int[][] {
                    {o,x,o,o},
                    {o,x,o,o},
                    {o,x,o,o},
                    {o,x,o,o}
                };
            case S:
                return new int[][] {
                    {o,o,o},
                    {o,x,x},
                    {x,x,o}
                };
            case Z:
                return new int[][] {
                    {o,o,o},
                    {x,x,o},
                    {o,x,x}
                };
            case L:
                return new int[][] {
                    {o,x,o},
                    {o,x,o},
                    {o,x,x}
                };
            case J:
                return new int[][] {
                    {o,x,o},
                    {o,x,o},
                    {x,x,o}
                };
            case T:
            default:
                return new int[][] {
                    {o,o,o},
                    {o,x,o},
                    {x,x,x}
                };
        }
    }
    
    public void Rotate() {
        int[][] aux = new int [getRows()][getCols()];
        
        for (int i = 0; i < getRows(); i++) {
            int k = getRows() - 1;
            for (int j = 0; j < getCols(); j++) {
                aux[i][j] = figure[k][i];
                k--;
            }
        }
        
        figureResp = figure;
        figure = aux;
        stepDown();
    }
    
    public void Restore() {
        figure = figureResp;
    }
    
    private void stepDown() {
        int aux[][] = new int[getRows()][getCols()];
        for (int i = 0; i < getCols(); i++) {
            if(figure[getRows()-1][i] != 0) {
                return;
            }
        }
        
        for(int i = getRows()-1; i > 0; i--) {
            int[] a = figure[i-1];
            aux[i] = a;
        }
        figure = aux;
    }
    
    public void CreateNext() {
        nextPiece = new Piece();
    }
    
    public int getValue(int row, int col) {
        return figure[row][col];
    }
    
    public int getRows() {
        return figure.length;
    }
    
    public int getCols() {
        return figure[0].length;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int[][] getFigure() {
        return figure;
    }

    public void setFigure(int[][] figure) {
        this.figure = figure;
    }

    public Piece getNextPiece() {
        return nextPiece;
    }

    public void setNextPiece(Piece nextPiece) {
        this.nextPiece = nextPiece;
    }
}
