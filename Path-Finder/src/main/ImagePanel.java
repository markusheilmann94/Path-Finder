
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private static final long serialVersionUID = -192324L;
	private Dimension dim;
	private BufferedImage img;
	
	public ImagePanel(Dimension dim) {
		this.dim = dim;
	}
	
	/** Loads an image into this panel */
	public void loadImage(Image img) {
		if(img instanceof BufferedImage) {
			this.img = (BufferedImage) img;
		}else{
			BufferedImage bi = new BufferedImage(img.getWidth(null),
					img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			bi.getGraphics().drawImage(img, 0, 0, null);
			this.img = bi;
		}
		revalidate();
		repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return dim;
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	@Override 
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, dim.width, dim.height);
        if(img != null) {
	        int x = (dim.width - img.getWidth(null))/2;
	        int y = (dim.height - img.getHeight(null))/2;
	        g2d.drawImage(img, null, x, y);
        }
	}
	
	/**
	 * Used again later
	 * @return
	 */
	public BufferedImage getBufferedImage() {
		return img;
	}
	
	public void setBufferedImage(BufferedImage img) {
		this.img = img;
		revalidate();
		repaint();
	}
}
