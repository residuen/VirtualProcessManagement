package de.virtualprocessmanagement.visu;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import de.virtualprocessmanagement.interfaces.SubjectShape;
import de.virtualprocessmanagement.objects.MainObject;
import de.virtualprocessmanagement.objects.RectShape;
import de.virtualprocessmanagement.processing.ProcessMap;

public class VisuPanel extends JPanel {

	private ArrayList<SubjectShape> objectList = null;
	
	private ProcessMap processMap = null;
	
	private boolean hasMap = true;
	
	private IndependenceObjectReader objectReader = null;
	
	private String host = null;
	
	private double zoomFactor = 1.5;
	
	private Image palette = null; // Toolkit.getDefaultToolkit().getImage( getClass().getResource("/de/virtualprocessmanagement/images/objects/palette.png") );

	public VisuPanel() {
		
		palette = new ImageIcon(getClass().getResource("/de/virtualprocessmanagement/images/objects/palette.png")).getImage();
	}
	
	public void paint(Graphics g) {
		
		super.paintComponents(g);
		
//		System.out.println(palette);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.scale(zoomFactor, zoomFactor);
		
		g2d.clearRect(0, 0, getWidth(), getHeight());
		
//		g2d.drawImage(palette, 0, 0, null);
		
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
			
			if(shape.getGroup()==MainObject.STORAGE_OBJECT)
				g2d.drawImage(palette, (int)shape.getX(), (int)shape.getY(), (int)MainObject.DEFAULT_WIDTH, (int)MainObject.DEFAULT_HEIGHT, null);
			else
			{
				g2d.setColor(shape.getFillColor());
				g2d.fill(shape);
				
				if(shape.getGroup() == MainObject.FORKLIFT || shape.getGroup() == MainObject.CHARGE_OBJECT)
					g2d.setColor(Color.BLACK);
				else
					g2d.setColor(Color.LIGHT_GRAY);
//				g2d.setColor(shape.getFrameColor());
				g2d.draw(shape);
			}
			
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
					  (float)(shape).getCenterX() - (float)fontMetrics.stringWidth(str)/2,
					  (float)(shape).getCenterY() + (float)fontMetrics.getHeight()/2);
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
