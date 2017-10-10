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
    
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_UP:
                engine.FigureRotate();
                break;
            case KeyEvent.VK_DOWN:
                engine.FigureDown();
                break;
            case KeyEvent.VK_LEFT:
                engine.FigureLeft();
                break;
            case KeyEvent.VK_RIGHT:
                engine.FigureRight();
                break;
            case KeyEvent.VK_CONTROL:
                engine.ChangeFigure();
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) { }
    @Override
    public void keyReleased(KeyEvent ke) { }
    
}
