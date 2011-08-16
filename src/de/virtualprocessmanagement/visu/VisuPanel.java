package de.virtualprocessmanagement.visu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.virtualprocessmanagement.objects.RectShape;

public class VisuPanel extends JPanel {

	private ArrayList<RectShape> objectList = null;
	
	public void setObjectList(ArrayList<RectShape> objectList) {
		this.objectList = objectList;
	}
	
	public void paint(Graphics g) {
		
		super.paintComponents(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		for(RectShape shape : objectList) {
			
			g2d.setColor(shape.getFillColor());
			g2d.fill(shape);

			g2d.setColor(shape.getFrameColor());
			g2d.draw(shape);
		}
		
	}
}
