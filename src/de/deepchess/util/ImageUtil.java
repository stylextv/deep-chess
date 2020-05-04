package de.deepchess.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.deepchess.main.Main;

public class ImageUtil {
	
	public static ArrayList<BufferedImage> ICONS=new ArrayList<BufferedImage>();
	
	public static BufferedImage BOARD_SHADOW;
	public static BufferedImage BOARD_PATTERN;
	
	public static BufferedImage BLACK_BISHOP;
	public static BufferedImage BLACK_KING;
	public static BufferedImage BLACK_KNIGHT;
	public static BufferedImage BLACK_PAWN;
	public static BufferedImage BLACK_QUEEN;
	public static BufferedImage BLACK_ROOK;
	
	public static BufferedImage WHITE_BISHOP;
	public static BufferedImage WHITE_KING;
	public static BufferedImage WHITE_KNIGHT;
	public static BufferedImage WHITE_PAWN;
	public static BufferedImage WHITE_QUEEN;
	public static BufferedImage WHITE_ROOK;
	
	public static BufferedImage[] POPUP_ERROR=new BufferedImage[31];
	
	public static BufferedImage[] BANNER_DEFEAT=new BufferedImage[61];
	public static BufferedImage[] BANNER_VICTORY=new BufferedImage[61];
	public static BufferedImage[] BANNER_DRAW=new BufferedImage[61];
	
	public static void load() {
		try {
			
			ICONS.add(loadImage("icons/icon16.png"));
			ICONS.add(loadImage("icons/icon24.png"));
			ICONS.add(loadImage("icons/icon32.png"));
			ICONS.add(loadImage("icons/icon64.png"));
			ICONS.add(loadImage("icons/icon128.png"));
			ICONS.add(loadImage("icons/icon256.png"));
			
			BOARD_SHADOW=loadImage("board/shadow.png");
			BOARD_PATTERN=loadImage("board/pattern.png");
			
			BLACK_BISHOP=loadImage("pieces/black_bishop.png");
			BLACK_KING=loadImage("pieces/black_king.png");
			BLACK_KNIGHT=loadImage("pieces/black_knight.png");
			BLACK_PAWN=loadImage("pieces/black_pawn.png");
			BLACK_QUEEN=loadImage("pieces/black_queen.png");
			BLACK_ROOK=loadImage("pieces/black_rook.png");
			
			WHITE_BISHOP=loadImage("pieces/white_bishop.png");
			WHITE_KING=loadImage("pieces/white_king.png");
			WHITE_KNIGHT=loadImage("pieces/white_knight.png");
			WHITE_PAWN=loadImage("pieces/white_pawn.png");
			WHITE_QUEEN=loadImage("pieces/white_queen.png");
			WHITE_ROOK=loadImage("pieces/white_rook.png");
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for(int i=0; i<61; i++) {
							BANNER_DEFEAT[i]=loadImage("overlay/"+Main.LANGUAGE+"/defeat/"+i+".png");
							BANNER_VICTORY[i]=loadImage("overlay/"+Main.LANGUAGE+"/victory/"+i+".png");
							BANNER_DRAW[i]=loadImage("overlay/"+Main.LANGUAGE+"/draw/"+i+".png");
						}
					} catch (Exception ex) {}
				}
			}).start();
			
//			int border=32;
//			BufferedImage image=new BufferedImage(512+border*2, 512+border*2, BufferedImage.TYPE_INT_ARGB);
//			Graphics2D graphics=(Graphics2D) image.getGraphics();
//			RenderUtil.setRenderingHints(graphics);
//			graphics.setColor(Vars.PRIMARY_COLOR2);
//			graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
//			graphics.setColor(Vars.PRIMARY_COLOR1);
//			for(int x=0; x<8; x++) {
//				for(int y=0; y<8; y++) {
//					if(x%2!=y%2) graphics.fillRect(border+x*64, border+y*64, 64, 64);
//				}
//			}
//			ImageIO.write(image, "PNG", new File("src/assets/textures/board/pattern.png"));
//			BufferedImage image=new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
//			Graphics2D graphics=(Graphics2D) image.getGraphics();
//			RenderUtil.setRenderingHints(graphics);
//			graphics.setColor(new Color(25,25,33,90));
//			graphics.fillRect(800/2-288, 800/2-288, 512+64, 512+64);
//			image=blurImage(image, 34);
//			ImageIO.write(image, "PNG", new File("src/assets/textures/board/shadow.png"));
			
//			BufferedImage error=loadImage("icons/error.png");
//			for(int i=0; i<31; i++) {
//				savePopup("Stockfish not found!", "english/error/"+i+".png", error, (i+1)/31.0);
//			}
//			for(int i=0; i<30; i++) {
//				saveBanner("DEFEAT", "english/defeat/"+i+".png", (i+1)/31.0, -1);
//				saveBanner("DEFEAT", "english/defeat/"+(i+31)+".png", 1-((i+1)/31.0), 1);
//				saveBanner("VICTORY", "english/victory/"+i+".png", (i+1)/31.0, -1);
//				saveBanner("VICTORY", "english/victory/"+(i+31)+".png", 1-((i+1)/31.0), 1);
//				saveBanner("DRAW", "english/draw/"+i+".png", (i+1)/31.0, -1);
//				saveBanner("DRAW", "english/draw/"+(i+31)+".png", 1-((i+1)/31.0), 1);
//			}
//			saveBanner("DEFEAT", "english/defeat/30.png", 1, -1);
//			saveBanner("VICTORY", "english/victory/30.png", 1, -1);
//			saveBanner("DRAW", "english/draw/30.png", 1, -1);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	public static void loadErrorPopup() {
		try {
			for(int i=0; i<31; i++) {
				POPUP_ERROR[i]=loadImage("overlay/"+Main.LANGUAGE+"/error/"+i+".png");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
//	private static void saveBanner(String title, String path, double d, int dir) throws IOException {
//		d=sigmoid(d);
//		
//		BufferedImage image=new BufferedImage(680, 680, BufferedImage.TYPE_INT_ARGB);
//		int width=image.getWidth();
//		int height=image.getHeight();
//		BufferedImage bannerShadow=new BufferedImage(680, 680, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D graphics2=(Graphics2D) bannerShadow.getGraphics();
//		RenderUtil.setRenderingHints(graphics2);
//		int bannerHeight=240;
//		graphics2.setColor(new Color(25,25,33,74));
//		graphics2.fillRect(width/2-680/2, height/2-bannerHeight/2, 680, bannerHeight);
//		bannerShadow=blurImage(bannerShadow, 28);
//		
//		Graphics2D graphics=(Graphics2D) image.getGraphics();
//		RenderUtil.setRenderingHints(graphics);
//		graphics.setColor(new Color(13,13,17));
//		int yOff=42;
//		graphics.fillPolygon(new int[]{width/2-680/2,width/2-680/2+52,width/2-680/2+52}, new int[]{height/2-bannerHeight/2,height/2-bannerHeight/2,height/2-bannerHeight/2-yOff}, 3);
//		graphics.fillPolygon(new int[]{width/2+680/2,width/2+680/2-52,width/2+680/2-52}, new int[]{height/2+bannerHeight/2,height/2+bannerHeight/2,height/2+bannerHeight/2+yOff}, 3);
//		
//		int area=512+64;
//		for(int x=0; x<area; x++) {
//			for(int y=0; y<area; y++) {
//				int rx=width/2-area/2+x;
//				int ry=height/2-area/2+y;
//				image.setRGB(rx, ry+4, bannerShadow.getRGB(rx, ry));
//			}
//		}
//		graphics.setColor(Vars.PRIMARY_COLOR1);
//		graphics.fillRect(width/2-680/2, height/2-bannerHeight/2, 680, bannerHeight);
//		graphics.setColor(Vars.PRIMARY_COLOR2);
//		int j=-23;
//		graphics.setFont(new Font("Renogare Regular", 0, 66));
//		graphics.drawString(title, width/2-graphics.getFontMetrics().stringWidth(title)/2, height/2+graphics.getFontMetrics().getHeight()/3+j);
//		graphics.setFont(new Font("Renogare Regular", 1, 22));
//		String s2="Press the mouse";
//		graphics.drawString(s2, width/2-graphics.getFontMetrics().stringWidth(s2)/2, height/2+graphics.getFontMetrics().getHeight()/3+70+j);
//		
//		int k=(int)Math.round(120*(1-d)*dir);
//		BufferedImage image2=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		for(int x=0; x<width; x++) {
//			for(int y=0; y<height; y++) {
//				Color c=new Color(image.getRGB(x, y),true);
//				int setY=y+k;
//				if(setY>=0&&setY<image2.getHeight()) image2.setRGB(x, setY, new Color(c.getRed(),c.getGreen(),c.getBlue(),(int)Math.round(c.getAlpha()*d)).getRGB());
//			}
//		}
//		ImageIO.write(image2, "PNG", new File("src/assets/textures/overlay/"+path));
//	}
//	private static void savePopup(String title, String path, BufferedImage icon, double d) throws IOException {
//		d=sigmoid(d);
//		
//		BufferedImage image=new BufferedImage(680, 680, BufferedImage.TYPE_INT_ARGB);
//		int width=image.getWidth();
//		int height=image.getHeight();
//		BufferedImage bannerShadow=new BufferedImage(680, 680, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D graphics2=(Graphics2D) bannerShadow.getGraphics();
//		RenderUtil.setRenderingHints(graphics2);
//		int bannerWidth=335;
//		int bannerHeight=235;
//		int roundness=35;
//		graphics2.setColor(new Color(25,25,33,74));
//		graphics2.fillRoundRect(width/2-bannerWidth/2, height/2-bannerHeight/2, bannerWidth, bannerHeight, roundness, roundness);
//		bannerShadow=blurImage(bannerShadow, 28);
//		
//		Graphics2D graphics=(Graphics2D) image.getGraphics();
//		RenderUtil.setRenderingHints(graphics);
//		
//		int area=512+64;
//		for(int x=0; x<width; x++) {
//			for(int y=0; y<area; y++) {
//				int ry=height/2-area/2+y;
//				image.setRGB(x, ry+4, bannerShadow.getRGB(x, ry));
//			}
//		}
//		graphics.setColor(Vars.PRIMARY_COLOR2);
//		graphics.fillRoundRect(width/2-bannerWidth/2, height/2-bannerHeight/2, bannerWidth, bannerHeight, roundness, roundness);
//		int j=-21;
//		graphics.drawImage(icon, width/2-icon.getWidth()/2,height/2-icon.getHeight()/2+j, null);
//		graphics.setColor(Vars.PRIMARY_COLOR1);
//		graphics.setFont(new Font("Renogare Regular", 0, 20));
//		graphics.drawString(title, width/2-graphics.getFontMetrics().stringWidth(title)/2, height/2+graphics.getFontMetrics().getHeight()/3+icon.getWidth()/2+25+j);
//		
//		int k=(int)Math.round(30*(1-d));
//		BufferedImage image2=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		for(int x=0; x<width; x++) {
//			for(int y=0; y<height; y++) {
//				Color c=new Color(image.getRGB(x, y),true);
//				int setY=y+k;
//				if(setY>=0&&setY<image2.getHeight()) image2.setRGB(x, setY, new Color(c.getRed(),c.getGreen(),c.getBlue(),(int)Math.round(c.getAlpha()*d)).getRGB());
//			}
//		}
//		ImageIO.write(image2, "PNG", new File("src/assets/textures/overlay/"+path));
//	}
//	private static double sigmoid(double x) {
//		return (1/( 1 + Math.pow(Math.E,(-1*(x*16-8)))));
//	}
	private static BufferedImage loadImage(String name) throws IOException {
		return ImageIO.read(ImageUtil.class.getClassLoader().getResourceAsStream("assets/textures/"+name));
	}
	
	public static BufferedImage blurImage(BufferedImage image, int radius) {
		image=getGaussianBlurFilter(radius, true).filter(image, null);
		image=getGaussianBlurFilter(radius, false).filter(image, null);
		return image;
	}
	private static ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
		if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }
        
        int size = radius * 2 + 1;
        float[] data = new float[size];
        
        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;
        
        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }
        
        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }        
        
        Kernel kernel = null;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }
	
	public static void paintImage(BufferedImage image, Color c) {
		int r=c.getRed();
		int g=c.getGreen();
		int b=c.getBlue();
		for(int y=0; y<image.getHeight(); y++) {
			for(int x=0; x<image.getWidth(); x++) {
				Color cc=new Color(image.getRGB(x, y),true);
				image.setRGB(x, y, new Color(r,g,b,cc.getAlpha()).getRGB());
			}
		}
	}
	
	public static BufferedImage progressiveScaling(BufferedImage before, Integer longestSideLength) {
	    if (before != null) {
	        Integer w = before.getWidth();
	        Integer h = before.getHeight();
	        
	        Double ratio = h > w ? longestSideLength.doubleValue() / h : longestSideLength.doubleValue() / w;
	        
	        //Multi Step Rescale operation
	        //This technique is describen in Chris Campbell’s blog The Perils of Image.getScaledInstance(). As Chris mentions, when downscaling to something less than factor 0.5, you get the best result by doing multiple downscaling with a minimum factor of 0.5 (in other words: each scaling operation should scale to maximum half the size).
	        while (ratio < 0.75) {
	            BufferedImage tmp = scale(before, 0.75);
	            before = tmp;
	            w = before.getWidth();
	            h = before.getHeight();
	            ratio = h > w ? longestSideLength.doubleValue() / h : longestSideLength.doubleValue() / w;
	        }
	        BufferedImage after = scale(before, ratio);
	        return after;
	    }
	    return null;
	}
	private static BufferedImage scale(BufferedImage imageToScale, Double ratio) {
	    Integer dWidth = ((Double) (imageToScale.getWidth() * ratio)).intValue();
	    Integer dHeight = ((Double) (imageToScale.getHeight() * ratio)).intValue();
	    BufferedImage scaledImage = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = scaledImage.createGraphics();
	    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
	    graphics2D.dispose();
	    return scaledImage;
	}
	
}
