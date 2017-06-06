package main;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	private int startX, startY, endX, endY;
	private int i;
	
	/** Creates new controller */
	public ImageFilterCtrl(ImagePanel source, ImagePanel target) {
		this.source = source;
		this.target = target;
		this.i = 1;
		
		target.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// @TODO Auto-generated method stub
				Dimension dim = target.getPreferredSize();
				
				if(i == 1) {
					startX = (dim.width-target.getBufferedImage().getWidth()) / 2;
					startY = (dim.height-target.getBufferedImage().getHeight()) / 2;
					
					if(arg0.getX() >= startX && arg0.getX() <= (startX + target.getBufferedImage().getWidth())) {
						startX -= arg0.getX();
						startX *= -1;
					}
					if(arg0.getY() >= startY && arg0.getY() <= (startY + target.getBufferedImage().getHeight())) {
						startY -= arg0.getY();
						startY *= -1;
					}
					System.out.println(startX + " " + startY);
				} else {
					if(i == 2) {
						endX = (dim.width-target.getBufferedImage().getWidth()) / 2;
						endY = (dim.height-target.getBufferedImage().getHeight()) / 2;
						
						if(arg0.getX() >= endX && arg0.getX() <= (endX + target.getBufferedImage().getWidth())) {
							endX -= arg0.getX();
							endX *= -1;
						}
						if(arg0.getY() >= endY && arg0.getY() <= (endY + target.getBufferedImage().getHeight())) {
							endY -= arg0.getY();
							endY *= -1;
						}
						System.out.println(endX + " " + endY);
					}
				} 
				i++;
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// @TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// @TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// @TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// @TODO Auto-generated method stub
				
			}
		});
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

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setI(int i) {
		this.i = i;
	}
	/** Sets filtered image as source image in order to apply further filters *//*
	public void setTargetAsSource() {
		scaled = target.getBufferedImage();
		source.loadImage(scaled);
	}*/
	
}
