package de.virtualprocessmanagement.visu;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.processing.ProcessMap;

public class VisuPanel extends JPanel {

	private ArrayList<SubjectShape> objectList = null;
	
	private ProcessMap processMap = null;
	
	private boolean hasMap = true;
	
	private IndependenceObjectReader objectReader = null;
	
	private String host = null;
	
	private double zoomFactor = 1.5;

	public void paint(Graphics g) {
		
		super.paintComponents(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.scale(zoomFactor, zoomFactor);
		
		g2d.clearRect(0, 0, getWidth(), getHeight());
		
		if(hasMap) {
			// Zuerst die statischen Objekte zeichnen
			for(String key : RectShape.staticShapeKeys)
			{
				objectList = processMap.getObjectList(key);
				
				if(objectList != null)
					paintShapes(g2d);
			}
	
			// ... anschliessend werden die beweglichen Objekte gezeichnet
			for(String key : RectShape.moveableShapeKeys)
			{
				objectList = processMap.getObjectList(key);
				
				if(objectList != null)
					paintShapes(g2d);
			}
		}
		else
			paintShapes(g2d);
			
	}
	
	/**
	 * Zeichnen der Shapes und der IDs
	 * @param g2d
	 */
	private void paintShapes(Graphics2D g2d) {
		
		for(SubjectShape shape : objectList) {
			
			g2d.setColor(shape.getFillColor());
			g2d.fill(shape);

			g2d.setColor(shape.getFrameColor());
			g2d.draw(shape);
			
			// Zeichne die Object-Id, wenn showId  = true ist
			if(shape.isShowId())
				paintId(g2d, shape);
			
			if(hasMap) {
				g2d.setColor(Color.BLACK);
				g2d.draw(processMap.getBoundary());
			}
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
					  (int)(shape).getCenterX() - fontMetrics.stringWidth(str)/2,
					  (int)(shape).getCenterY() + fontMetrics.getHeight()/2);
	}
	
	public double getZoomFactor() {
		return zoomFactor;
	}

	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public void setProcessMap(ProcessMap processMap, String host) {
		
		this.processMap = processMap;
		this.host = host;
		
		if(processMap == null) {
			hasMap = false;
			
			objectList = new ArrayList<SubjectShape>();
			
			objectReader = new IndependenceObjectReader(host, objectList, (Component)this);
			objectReader.start();
		}
	}
}
