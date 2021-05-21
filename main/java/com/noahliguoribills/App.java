package com.noahliguoribills;
import com.noahliguoribills.Cells.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Scanner;

public class App {
	
	public App() throws IOException{
		
		//cells
		Cells a = new Cells();
		//height
		int h = a.getHeight();
		//width
		int w = a.getWidth();
		//matrix
		int[][] m = a.getMat();
		//image
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		
		//create the image from mat
		Graphics2D g2d = image.createGraphics();
		//color 1s and 0s
		for (int y = 0; y < m.length; y ++) {
			for (int x = 0; x < m[0].length; x++) {
				if (m[y][x] == 0) {
					g2d.setColor(Color.white);
				} else {
					g2d.setColor(Color.black);
				}
				g2d.fillRect(x, y, 1, 1);
			}
		}
		
		String dir = "image.png";
		File f = new File(dir);
		ImageIO.write(image, "png", f);
		
	}
	
	public static void main(String[] args) throws IOException{
		App i = new App();
		System.out.println("done.");
	}

}
