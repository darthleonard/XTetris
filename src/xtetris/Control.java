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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author darthleonard
 */
public class Control implements KeyListener {

    private Engine engine;
    
    public Control(Engine engine) {
        this.engine = engine;
    }
    
    private void gameAction(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                engine.FigureRotate();
                break;
            case KeyEvent.VK_DOWN:
                engine.MoveFigure(Engine.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                engine.MoveFigure(Engine.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                engine.MoveFigure(Engine.RIGHT);
                break;
            case KeyEvent.VK_1:
                engine.SwitchArea(Engine.STYLE1);
                break;
            case KeyEvent.VK_2:
                engine.SwitchArea(Engine.STYLE2);
                break;
            case KeyEvent.VK_3:
                engine.SwitchArea(Engine.STYLE3);
                break;
            case KeyEvent.VK_CONTROL:
                engine.ChangeFigure();
                break;
        }
    }
    
    private void penaltyAction(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                engine.SearchNextPenalty();
                break;
            case KeyEvent.VK_ENTER:
                engine.RemovePenalty();
                break;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_F1:
                engine.ShowInstructions();
                break;
            case KeyEvent.VK_F2:
                engine.NewGame();
                break;
            case KeyEvent.VK_SHIFT:
                if(!engine.isRemovingPenalty())
                    engine.setPaused(!engine.isPaused());
                break;
            default:
                if(engine.isRemovingPenalty())
                    penaltyAction(e);
                else
                    gameAction(e);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) { }
    @Override
    public void keyReleased(KeyEvent ke) { }
    
}
