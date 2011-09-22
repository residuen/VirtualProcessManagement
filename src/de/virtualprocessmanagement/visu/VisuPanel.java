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
	
	private ProcessMap processMap = null;	// beinhaltet die Map
	
	private boolean hasMap = true;			// Flag zur Kontrolle existenz der Map
	
	// liest die Objektinfos vom Server, falls Clientmodus aktiv ist
	private IndependenceObjectReader objectReader = null;	
	
//	private String host = null;
	
	private double zoomFactor = 1.5;	// Variable fuer Zoom-Faktor  
	
	private Image palette = null; // Image mit Palettenbild

	public VisuPanel() {
		
		// Palette initialisieren
		palette = new ImageIcon(getClass().getResource("/de/virtualprocessmanagement/images/objects/palette.png")).getImage();
	}
	
	public void paint(Graphics g) {
		
		super.paintComponents(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.scale(zoomFactor, zoomFactor);	// Zoom in Visualisierung
		
		g2d.clearRect(0, 0, getWidth(), getHeight());	// Loeschen des Zeichenbereichs
		
		// Zeichen wenn Server Map uebergeben hat ...
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
		else // ... ansonsten die Objekte aus dem IndependenceObjectReader zeichnen
			paintShapes(g2d);
			
	}
	
	/**
	 * Zeichnen der Shapes und der IDs
	 * @param g2d
	 */
	private void paintShapes(Graphics2D g2d) {
		
		for(SubjectShape shape : objectList) {
			
			// Shape eine STellflaeche ist, dann die Palette zeichnen
			if(shape.getGroup()==MainObject.STORAGE_OBJECT)
			{
				g2d.drawImage(palette, (int)shape.getX(), (int)shape.getY(), (int)MainObject.DEFAULT_WIDTH, (int)MainObject.DEFAULT_HEIGHT, null);
			}
			else
			{
				// Zeichne ie Objektflaechen
				g2d.setColor(shape.getFillColor());
				g2d.fill(shape);	// Objektflaeche zeichnen
				
				// Wenn Objekt ein Stapler oder Ladung ist, dann Rahmenfarbe schwarz ...
				if(shape.getGroup() == MainObject.FORKLIFT || shape.getGroup() == MainObject.CHARGE_OBJECT)
					g2d.setColor(Color.BLACK);
				else	// ... ansonsten ist die Rahmenfarbe grau
					g2d.setColor(Color.LIGHT_GRAY);

				g2d.draw(shape);	// Objektrahmen zeichnen
			}
			
			// Zeichne die Object-Id, wenn showId  = true ist
			if(shape.isShowId())
				paintId(g2d, shape);
			
			// Aeusseren Rahmen zeichnen, wenn Servermodus
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
		
		String str = Integer.toString(shape.getId()); // ID in Zeichenkette umwandeln
		
		// Fontmetric-Objekt um grafikeigenschaften des Textes zu ermittelt
		FontMetrics fontMetrics = g2d.getFontMetrics();
		
		g2d.setColor(Color.BLACK);
		
		// Text in die Objektmitte schieben
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
//		this.host = host;
		
		if(processMap == null) {
			hasMap = false;
			
			objectList = new ArrayList<SubjectShape>();
			
			objectReader = new IndependenceObjectReader(host, objectList, (Component)this);
			objectReader.start();
		}
	}
}
