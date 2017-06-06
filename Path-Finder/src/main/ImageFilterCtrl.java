package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import main.filter.ObstacelFinder;

/**
 * Controls the Image Filter app
 * 
 * @author Tom
 */
public class ImageFilterCtrl {

	private ImagePanel source, target;
	
	private BufferedImage scaled, filtered , dImage;
	
	/** Creates new controller */
	public ImageFilterCtrl(ImagePanel source, ImagePanel target) {
		this.source = source;
		this.target = target;
	}
	
	/** Loads a new Image into this controller and displays it in the source panel */
	public void loadImage(BufferedImage img) {
		int type = BufferedImage.TYPE_INT_ARGB;

		float s = 1.0f;
		float wFactor = (float) App.PANEL_SIZES.width / (float) img.getWidth();
		float hFactor = (float) App.PANEL_SIZES.height / (float) img.getHeight();
		if (wFactor < hFactor) {
			s = wFactor;
		} else {
			s = hFactor;
		}

		int w = (int) (img.getWidth() * s), h = (int) (img.getHeight() * s);

		Image tmp = img.getScaledInstance(w, h, 90);
		scaled = new BufferedImage(w, h, type);
		Graphics2D g = scaled.createGraphics();
		g.drawImage(tmp, 0, 0, null);
		g.dispose();
		//scaled = Tools.scale(img, App.PANEL_SIZES.width, App.PANEL_SIZES.height, 90);
		source.loadImage(scaled);
		target.loadImage(scaled);
		filtered = new BufferedImage(w, h, type);
		g = filtered.createGraphics();
		g.drawImage(tmp, 0, 0, null);
		g.dispose();
	}
	
	/** Applies a filter to the loaded source image and shows the generated image in the target panel */
	
	
	public StartAndEndPoint applyStartandEndPointFinding(ObstacelFinder t ) {
		
		StartAndEndPoint p = new StartAndEndPoint();
		boolean foundPoints;
		
		foundPoints = t.findStartAndEndPoint( scaled , p );
		
		
		System.out.println( foundPoints + " " + p.getstartx() + " " + p.getstarty() + " " + p.getendx() + " " + p.getendy() );
		
		return p;
	}
	
	public void applyFilter(ObstacelFinder t, int thresold) {
		filtered = t.ObstacelFinding(scaled, thresold);
		target.loadImage(filtered);
	}

	
	public void applyCompletFinding(ObstacelFinder t) {
		filtered = t.completObstacelFinding(filtered, scaled);
		target.loadImage(filtered);
	}

	
	
	public void applyWalg(Walgorithmus w) {
		while(!w.step(10)) {
			
		}
		BufferedImage test = new BufferedImage(filtered.getWidth(), filtered.getHeight(), filtered.getType());
		
		for(int i = 0; i < filtered.getWidth(); i++) {
			for(int j = 0; j < filtered.getHeight(); j++) {
				test.setRGB(i, j, filtered.getRGB(i, j));
			}
		}
		
		dImage = w.draw(test);
		dImage = w.drawPath(dImage);
		target.loadImage(dImage);
	}
	
	public BufferedImage getFiltered() {
		return filtered;
	}
	
	/** Sets filtered image as source image in order to apply further filters *//*
	public void setTargetAsSource() {
		scaled = target.getBufferedImage();
		source.loadImage(scaled);
	}*/
	
}
