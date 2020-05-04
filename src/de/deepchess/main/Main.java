package de.deepchess.main;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JFrame;

import de.deepchess.ai.MinimaxAi;
import de.deepchess.ai.StockfishAi;
import de.deepchess.game.Game;
import de.deepchess.game.Move;
import de.deepchess.game.Piece;
import de.deepchess.game.PieceColor;
import de.deepchess.game.Winner;
import de.deepchess.render.Renderer;
import de.deepchess.util.AudioUtil;
import de.deepchess.util.ImageUtil;
import de.deepchess.util.MathUtil;

public class Main {
	
	public static String LANGUAGE="english";
	
	private static JFrame frame;
	private static Renderer renderer;
	
	private static boolean running=true;
	private static int width,height;
	private static int mouseX,mouseY;
	private static int mouseXMoved;
	
	private static Game game;
	private static Piece hand;
	private static ArrayList<Move> handMoves;
	private static int handFromX,handFromY;
	private static int handRenderX,handRenderY;
	private static double handRotation;
	private static Point click;
	
	private static Move lastAiMove;
	private static double lastAiMoveAni;
	
	private static Winner winner=Winner.NONE;
	private static int bannerFrame=0;
	private static boolean drawErrorPopup;
	private static int errorPopupFrame=0;
	private static boolean resetGame=false;
	
	public static void main(String[] args) {
		String lang=Locale.getDefault().getLanguage();
		if(lang.equals(new Locale("de").getLanguage())) LANGUAGE="german";
		
		if(!StockfishAi.start()) {
			ImageUtil.loadErrorPopup();
			drawErrorPopup=true;
		}
		
		AudioUtil.load();
		ImageUtil.load();
		game=new Game();
		createWindow();
		
		startGameLoop();
	}
	private static void createWindow() {
		frame=new JFrame(Vars.WINDOW_TITLE);
		frame.setSize(Vars.WINDOW_WIDTH+16, Vars.WINDOW_HEIGHT+39);
		Dimension monitor=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(monitor.width/2-frame.getWidth()/2, (monitor.height-40)/2-frame.getHeight()/2);
		frame.setIconImages(ImageUtil.ICONS);
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent event) {
				frame.setVisible(false);
		    	running=false;
		    }
		});
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(winner==Winner.NONE) {
					
					if(game.whoseTurn()==PieceColor.WHITE&&e.getButton()==MouseEvent.BUTTON1) {
						Point mouse=renderer.getMousePosition();
						if(mouse!=null) {
							click=mouse;
						}
					}
					
				} else if(bannerFrame==30) {
					resetGame=true;
				}
			}
		});
		
		frame.add(renderer=new Renderer());
		
		frame.setResizable(false);
		frame.setVisible(true);
	}
	private static void startGameLoop() {
		while(running) {
			runGameLoop();
		}
		StockfishAi.kill();
		System.exit(0);
	}
	
	private static void runGameLoop() {
		long before=System.nanoTime();
		
		if(!drawErrorPopup) {
			if(lastAiMove!=null) {
				if(lastAiMoveAni<1) {
					lastAiMoveAni+=1/32.0;
					if(lastAiMoveAni==0.75) AudioUtil.play(AudioUtil.PIECE_PUT);
				} else lastAiMove=null;
			}
			renderer.paintImmediately(0, 0, renderer.getWidth(), renderer.getHeight());
			if(winner==Winner.NONE&&game.whoseTurn()==PieceColor.BLACK) {
				lastAiMove=game.performAiMove();
				lastAiMoveAni=0;
				checkForWinner();
			}
			if(click!=null) {
				int mx=click.x-(width/2-256);
				int my=click.y-(height/2-256);
				click=null;
				if(mx>=0&&my>=0) {
					int boardX=mx/64;
					int boardY=my/64;
					if(boardX<8&&boardY<8) {
						if(hand==null) {
							Piece p=game.getPiece(boardX, boardY);
							if(p!=null&&p.getColor()==game.whoseTurn()) {
								ArrayList<Move> moves=new ArrayList<Move>();
								game.getMoves(boardX, boardY, moves, true, true);
								if(moves.size()!=0) {
									hand=game.getPiece(boardX, boardY);
									handMoves=moves;
									game.setPiece(boardX, boardY, null);
									handFromX=boardX;
									handFromY=boardY;
									handRenderX=boardX*64-mx;
									handRenderY=boardY*64-my;
								}
							}
						} else {
							int index=boardY*8+boardX;
							Move m=null;
							for(Move check:handMoves) {
								if(index==check.getTo()) {
									m=check;
									break;
								}
							}
							if(m!=null) {
								game.setPiece(handFromX, handFromY, hand);
								hand=null;
								game.performMove(m);
								AudioUtil.play(AudioUtil.PIECE_PUT);
								checkForWinner();
							} else {
								game.setPiece(handFromX, handFromY, hand);
								hand=null;
							}
						}
					} else if(hand!=null) {
						game.setPiece(handFromX, handFromY, hand);
						hand=null;
					}
				} else if(hand!=null) {
					game.setPiece(handFromX, handFromY, hand);
					hand=null;
				}
			}
		} else {
			renderer.paintImmediately(0, 0, renderer.getWidth(), renderer.getHeight());
		}
		
		long after=System.nanoTime();
		long sleep=16666667-(after-before);
		if(sleep>0) {
			try {
				Thread.sleep(sleep/1000000, (int)(sleep%1000000));
			} catch (InterruptedException ex) {}
		}
	}
	public static void drawFrame(Graphics2D graphics) {
		width=renderer.getWidth();
		height=renderer.getHeight();
		Point mouse=renderer.getMousePosition();
		if(mouse!=null) {
			mouseXMoved=mouse.x-mouseX;
			mouseX=mouse.x;
			mouseY=mouse.y;
		}
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		graphics.setColor(Vars.PRIMARY_COLOR2);
		graphics.fillRect(0, 0, width, height);
		drawBoard(graphics);
		drawHand(graphics);
		drawBanner(graphics);
		drawPopup(graphics);
		
	}
	private static void drawBoard(Graphics2D graphics) {
		graphics.drawImage(ImageUtil.BOARD_SHADOW, width/2-400, height/2-400 +4, null);
		graphics.drawImage(ImageUtil.BOARD_PATTERN, width/2-288, height/2-288, null);
		
		int ox=width/2-256;
		int oy=height/2-256;
		for(int x=0; x<8; x++) {
			for(int y=0; y<8; y++) {
				Piece p=game.getPiece(x, y);
				if(p!=null) {
					if(lastAiMove!=null) {
						if(lastAiMove.getTo()==y*8+x) {
							int pos1x=ox+lastAiMove.getFrom()%8*64;
							int pos1y=oy+lastAiMove.getFrom()/8*64;
							int pos2x=ox+x*64;
							int pos2y=oy+y*64;
							double d=MathUtil.sigmoid(lastAiMoveAni);
							AffineTransform trans=new AffineTransform();
							trans.translate(pos1x+(pos2x-pos1x)*d, pos1y+(pos2y-pos1y)*d);
							graphics.drawImage(p.getImage(), trans, null);
						} else if(lastAiMove.getCastleTo()==y*8+x) {
							int pos1x=ox+lastAiMove.getCastleFrom()%8*64;
							int pos1y=oy+lastAiMove.getCastleFrom()/8*64;
							int pos2x=ox+x*64;
							int pos2y=oy+y*64;
							double d=MathUtil.sigmoid(lastAiMoveAni);
							graphics.drawImage(p.getImage(), (int) (pos1x+(pos2x-pos1x)*d), (int) (pos1y+(pos2y-pos1y)*d), null);
						} else graphics.drawImage(p.getImage(), ox+x*64, oy+y*64, null);
					} else graphics.drawImage(p.getImage(), ox+x*64, oy+y*64, null);
				}
			}
		}
	}
	private static void drawHand(Graphics2D graphics) {
		if(hand!=null) {
//			graphics.setColor(Color.RED);
//			int ox=width/2-256;
//			int oy=height/2-256;
//			for(Move m:handMoves) {
//				int to=m.getTo();
//				graphics.fillArc(ox+to%8*64+32-10, oy+to/8*64+32-10, 20, 20, 0, 360);
//			}
			
			BufferedImage handImage=hand.getImage();
			AffineTransform trans=new AffineTransform();
			trans.translate(mouseX+handRenderX, mouseY+handRenderY -4);
			double targetValue=mouseXMoved*4;
			if(targetValue>90) targetValue=90;
			else if(targetValue<-90) targetValue=-90;
			handRotation=MathUtil.lerp(handRotation, targetValue, 0.25);
			trans.rotate(Math.toRadians(handRotation), handImage.getWidth()/2, 10);
			graphics.drawImage(handImage, trans, null);
		}
	}
	private static void drawBanner(Graphics2D graphics) {
		if(winner!=Winner.NONE&&lastAiMove==null) {
			BufferedImage banner=null;
			switch(winner) {
			case BLACK:
				banner=ImageUtil.BANNER_DEFEAT[bannerFrame];
				break;
			case DRAW:
				banner=ImageUtil.BANNER_DRAW[bannerFrame];
				break;
			case WHITE:
				banner=ImageUtil.BANNER_VICTORY[bannerFrame];
				break;
			default:
				break;
			}
			if(bannerFrame!=30) {
				bannerFrame++;
				if(bannerFrame==61) {
					bannerFrame=0;
					winner=Winner.NONE;
				}
			} else if(resetGame) {
				MinimaxAi.clearCache();
				game.reset();
				bannerFrame++;
				resetGame=false;
			}
			
			if(banner!=null) graphics.drawImage(banner, width/2-banner.getWidth()/2, height/2-banner.getHeight()/2, null);
		}
	}
	private static void drawPopup(Graphics2D graphics) {
		if(drawErrorPopup) {
			BufferedImage frame=ImageUtil.POPUP_ERROR[errorPopupFrame];
			if(errorPopupFrame!=30) {
				errorPopupFrame++;
			}
			
			if(frame!=null) graphics.drawImage(frame, width/2-frame.getWidth()/2, height/2-frame.getHeight()/2, null);
		}
	}
	
	private static void checkForWinner() {
		winner=game.getWinner();
	}
	
}
