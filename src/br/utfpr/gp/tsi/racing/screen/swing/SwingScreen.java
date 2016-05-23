package br.utfpr.gp.tsi.racing.screen.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.utfpr.gp.tsi.racing.car.ICar;
import br.utfpr.gp.tsi.racing.screen.IScreen;
import br.utfpr.gp.tsi.racing.track.Track;

/**
 * This class was used to test the game.
 * It creates a 2D screen.
 */
public class SwingScreen extends JFrame implements IScreen {
	private static final long serialVersionUID = 1L;
	private List<? extends ICar> carList;
	private int[][] matrix;
	private Color[] colorArray = {
			Color.BLUE, Color.RED, Color.PINK,
			Color.BLACK, new Color(100, 30, 0), new Color(10, 50, 100),
			new Color(50, 100, 0), new Color(150, 0, 50), new Color(100, 100, 150),
			Color.WHITE
			};
	
	public void drawCar(ICar car, Color color, Graphics g) {
		final int size = 20;
		Point point;
		g.setColor(color);
		point = car.getLocation();
		for (int i = -2; i < 3; i++) {
			for (int j = -2; j < 3; j++) {
				g.drawLine(
						(int) (i + point.x + size*Math.cos(car.getAngleRadians()) ), 
						(int) (j + point.y + size*Math.sin(car.getAngleRadians()) ), 
						i + point.x, 
						j + point.y);
			}
		}
		g.drawString(car.getName(), point.x-5, point.y+15);
	}

	public SwingScreen() {
		super("TSI Racing");
		setSize(800, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel container = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.GREEN);
				g.fillRect(0, 0, 1000, 1000);
				for (int i = 0; i < 1000; i++) {
					for (int j = 0; j < 1000; j++) {
						if (matrix[i][j] == Track.PIXEL_ROAD) {
							g.setColor(Color.BLACK);
							g.drawLine(i, j, i, j);
						}
					}
				}
				for (int carIndex = 0; carIndex < carList.size(); carIndex++) {
					drawCar(carList.get(carIndex), colorArray[carIndex], g);
				}
			}
		};
		container.setSize(1000, 1000);
		container.setPreferredSize(new Dimension(1000, 1000));
		JScrollPane jsp = new JScrollPane(container,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setSize(800, 800);
		add(jsp);

		setVisible(true);
	}

	@Override
	public void setTrack(Track track) {
		matrix = track.getMatrix();
	}

	@Override
	public void setCarList(List<? extends ICar> carList) {
		this.carList = carList;
	}

	@Override
	public void update() {
		repaint();
	}

}
