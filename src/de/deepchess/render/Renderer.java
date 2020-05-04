package de.deepchess.render;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.deepchess.main.Main;

public class Renderer extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void paintComponent(Graphics g) {
		try {
			Main.drawFrame((Graphics2D) g);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
