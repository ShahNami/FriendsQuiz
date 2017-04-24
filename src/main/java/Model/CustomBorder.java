/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author namimac
 */
@SuppressWarnings("serial")
public class CustomBorder extends AbstractBorder {
    private int _stroke;
    private Color _color;
    
    public CustomBorder(int stroke, Color color){
        this._stroke = stroke;
        this._color = color;
    }
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y,
            int width, int height) {
        // TODO Auto-generated method stubs
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(_stroke));
        g2d.setColor(_color);
        g2d.drawRoundRect(x, y, width - 1, height - 1, 20, 20);
    }   
}