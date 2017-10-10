/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtetris;

import javax.swing.JFrame;

/**
 *
 * @author desarrollo
 */
public class MainFrame extends JFrame {

    private GameArea area;
    private Engine engine;
    
    public MainFrame() {
        setTitle("XTetris");
        setSize(400, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        area = new GameArea();
        engine = new Engine(this);
        
        addKeyListener(new Control(engine));
        add(area);
        
        new Thread(engine).start();
    }

    public GameArea getArea() {
        return area;
    }

    public void setArea(GameArea area) {
        this.area = area;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
