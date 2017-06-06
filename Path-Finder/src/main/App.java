package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileFilter;

import main.filter.ObstacelFinder;

public class App extends JFrame{

	private static final long serialVersionUID = 192323L;
	public static final Dimension PANEL_SIZES = new Dimension(600,600);
	private ImagePanel sourceImage;
	private ImagePanel targetImage;
	private ImageFilterCtrl ctrl;
	private BufferedImage source;
	private Walgorithmus walg;
	private StartAndEndPoint p = new StartAndEndPoint();
	
	public App() {
		super("Path Finder");
		initUI();
		ctrl = new ImageFilterCtrl(sourceImage, targetImage);
	}
	
	private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());

		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		
		
		JToggleButton toggle = new JToggleButton("Terrain", false);
		
		
		
		JMenuItem load = new JMenuItem("Load");
		final JFrame tmpframe = this;
		
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileFilter() {
					@Override
					public String getDescription() {
						return "JPEG/PNG Images";
					}
					@Override
					public boolean accept(File f) {
						String n = f.getName().toLowerCase();
						return n.endsWith(".jpg") || n.endsWith(".png");
					}
				});
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if(fc.showOpenDialog(tmpframe) == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					source = loadImage(f);
					ctrl.loadImage(source);
				}
			}
		});
		menu.add(load);
		
		JMenuItem FindStartAndEndPoint = new JMenuItem("Find Startpoint and Endpoint");
		FindStartAndEndPoint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ObstacelFinder t = new ObstacelFinder();
				p = ctrl.applyStartandEndPointFinding( t );
			}
		});
		menu.add( FindStartAndEndPoint );
	
		final JFrame tmp = this;
		JMenuItem thresold = new JMenuItem("Find Obstacels");
		thresold.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ObstacelFinder t = new ObstacelFinder();
				String msg = "Please enter a value for the Thresold (0-255)";
				
				String userInput = JOptionPane.showInputDialog(tmp, msg, "" + 127);
				int value = Integer.parseInt(userInput);
				
				if(value < 0 || value > 255) {
					value = 127;
				}
				ctrl.applyFilter(t, value);
			}
		});
		menu.add(thresold);
	
	
		
		JMenuItem completFinding = new JMenuItem("complet Obstacel Finding");
		completFinding.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ObstacelFinder t = new ObstacelFinder();
				ctrl.applyCompletFinding( t );
			}
		});
		menu.add( completFinding );
	
		
		
	
		JMenuItem item = new JMenuItem("Start");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				walg = new Walgorithmus(ctrl.getFiltered(), p , 3, toggle.isSelected());
		      
				while(!walg.step(10)) {
					ctrl.applyWalg(walg);
				}
			}
		});
		
		menu.add(item);
		
		menu.addSeparator();
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEnabled(false);
				dispose();
			}
			
		});
		menu.add(exit);
		bar.add(menu);
		bar.add(toggle);
		
		JPanel imagingPanel = new JPanel();
		imagingPanel.setLayout(new BoxLayout(imagingPanel, BoxLayout.Y_AXIS));
		sourceImage = new ImagePanel(PANEL_SIZES);
		targetImage = new ImagePanel(PANEL_SIZES);
		
		setJMenuBar(bar);
		
		Box b = Box.createHorizontalBox();
		b.add(sourceImage);
		b.add(Box.createHorizontalStrut(5));
		b.add(targetImage);
		imagingPanel.add(b);
		
		getContentPane().add(imagingPanel, BorderLayout.CENTER);
		
		JPanel legende = new JPanel();
		JLabel label = new JLabel(new ImageIcon("Legende (1).png"));
		legende.setLayout(new BoxLayout(legende, BoxLayout.X_AXIS));
		
		Box l = Box.createVerticalBox();
		l.add(label);
		
		legende.add(l);
		getContentPane().add(legende, BorderLayout.SOUTH);
	}
	
	public BufferedImage loadImage(InputStream stream) {
		try {
			return ImageIO.read(stream);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		return null;
	}
	
	public BufferedImage loadImage(File f) {
		try {
			return loadImage(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		return null;
	}
	
	public BufferedImage loadImage(String fileName) {
		return loadImage(new File(fileName));
	}
	
	private void launch() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		App window = new App();
		window.launch();
	}
}