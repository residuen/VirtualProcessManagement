package de.virtualprocessmanagement.visu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.processing.ProcessMap;

public class VisuPanel extends JPanel {

	private ArrayList<RectShape> objectList = null;
	
	private ProcessMap processMap = null;
	
	private Shape boundary = null;

//	public VisuPanel(Shape boundary) {
//		this.boundary = boundary;
//	}
	
	public void setProcessMap(ProcessMap processMap) {
		this.processMap = processMap;
		
		objectList = processMap.getObjectList();
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
