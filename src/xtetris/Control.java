/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
