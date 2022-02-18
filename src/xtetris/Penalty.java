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
public class Penalty {
    
    private int x;
    private int y;
    
    public boolean CheckPenalty(GameArea area, Piece piece) {
        return (area.getStyle() != piece.getStyle());
    }
    
    public void Penalize(Piece piece, GameArea area, GameArea[] areas) {
        int aux[][] = piece.getFigure();
        int cont = 0;
        for (int i = 0; i < aux.length; i++) {
            for (int j = 0; j < aux[0].length; j++) {
                if(aux[i][j] == piece.getStyle() && cont < 2) {
                    aux[i][j] = Engine.V_EMPTY;
                    cont++;
                }
            }
        }
        piece.setFigure(aux);
        
        int col;
        for (GameArea tmp : areas) {
            if(tmp != area) {
                col = (int) (Math.random() * (Engine.COLS - 2)) + 1;
                for (int i = 1; i < Engine.ROWS; i++) {
                    if(tmp.getMap()[i][col] != Engine.V_EMPTY) {
                        tmp.getMap()[i-1][col] = area.getStyle();
                        tmp.repaint();
                        break;
                    }
                }
            }
        }
    }
    
    public boolean FindPenalty(int[][] map, GameArea area) {
        for (int i = 0; i < Engine.ROWS; i++) {
            for (int j = 0; j < Engine.COLS; j++) {
                if(map[i][j] != Engine.V_EMPTY && map[i][j] != -1 && map[i][j] != area.getStyle()) {
                    y = i;
                    x = j;
                    return true;
                }
            }
        }
        return false;
    }
    
    public void SearchNextPenalty(int map[][], GameArea area) {
        int y = this.y;
        int x = this.x;
        
        // validate position
        if((y+1) >= Engine.ROWS || map[y+1][x] == -1) {
            y = 1;
            x = x + 1;
        }
        
        boolean flag = false; // flag to know if a penalty was found after the current penalty located
        
        for (int j = x; j < Engine.COLS; j++) {
            for (int i = 0; i < Engine.ROWS; i++) {
                if(i == y)
                    continue;
                if((map[i][j] != Engine.V_EMPTY) && (map[i][j] != -1) && (map[i][j] != area.getStyle())) {
                    y = i;
                    x = j;
                    flag = true;
                    break;
                }
            }
            if(flag)
                break;
        }
        
        if(!flag) { // no penalty founded after the current penalty located
            System.out.println("no flag, try from (0,0)");
            this.x = 0;
            this.y = 0;
            SearchNextPenalty(map, area);
        } else { // found new penalty
            System.out.println("new penality found!");
            this.x = x;
            this.y = y;
        }
        
    }
    
    /**
     * Remove the penalty selected
     * Assign the TYPE_Z value to the wrong value at map
     */
    public void RemovePenalty(int[][] map, GameArea area) {
        map[y][x] = Engine.V_EMPTY;
        
        for (int i = y; i > 0; i--) {
            if(i-1 > 0)
                map[i][x] = map[i-1][x];
            else
                map[i][x] = Engine.V_EMPTY;
        }
        
        x = -1;
        y = -1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}