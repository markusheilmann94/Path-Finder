package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import main.filter.Threshold;

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
	}
	
	/** Applies a filter to the loaded source image and shows the generated image in the target panel */
	public void applyFilter(Threshold t, int thresold) {
		filtered = t.thImage(scaled, thresold);
		target.loadImage(filtered);
	}

	public void applyWalg(Walgorithmus w) {
		dImage = w.draw(filtered);
		target.setBufferedImage(dImage);
		
	}
	
	public BufferedImage getFiltered() {
		return filtered;
	}
	
	public ImagePanel getImageTarget() {
		return target;
	}
	
	/** Sets filtered image as source image in order to apply further filters *//*
	public void setTargetAsSource() {
		scaled = target.getBufferedImage();
		source.loadImage(scaled);
	}*/
	
}
