package de.virtualprocessmanagement.visu;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.processing.ProcessMap;

public class VisuPanel extends JPanel {

	private ArrayList<SubjectShape> objectList = null;
	
	private ProcessMap processMap = null;
	
	private Shape boundary = null;

//	public VisuPanel(Shape boundary) {
//		this.boundary = boundary;
//	}
	
	public void setProcessMap(ProcessMap processMap) {
		this.processMap = processMap;
	}

	public void paint(Graphics g) {
		
		super.paintComponents(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.clearRect(0, 0, getWidth(), getHeight());
		
		
		for(String key : RectShape.staticShapeKeys)
		{
			objectList = processMap.getObjectList(key);
			
			if(objectList != null)
				paintShapes(g2d);
		}

		for(String key : RectShape.moveableShapeKeys)
		{
			objectList = processMap.getObjectList(key);
			
			if(objectList != null)
				paintShapes(g2d);
		}
	}
	
	private void paintShapes(Graphics2D g2d) {
		
		for(SubjectShape shape : objectList) {
			
			g2d.setColor(shape.getFillColor());
			g2d.fill(shape);

			g2d.setColor(shape.getFrameColor());
			g2d.draw(shape);
			
			// Zeichne die Object-Id, wenn showId  = true ist
			if(shape.isShowId())
				paintId(g2d, shape);
			
			g2d.setColor(Color.BLACK);
			g2d.draw(processMap.getBoundary());
		}
	}
	/**
	 * Zeichne die Object-Id, wenn showId  = true ist
	 * @param g2d
	 * @param shape
	 */
	private void paintId(Graphics2D g2d, SubjectShape shape) {
		
		String str = Integer.toString(shape.getId());
		FontMetrics fontMetrics = g2d.getFontMetrics();
		
		g2d.setColor(Color.BLACK);
		g2d.drawString(str,
					  (int)((RectShape)shape).getCenterX() - fontMetrics.stringWidth(str)/2,
					  (int)((RectShape) shape).getCenterY() + fontMetrics.getHeight()/2);
	}
	

}
