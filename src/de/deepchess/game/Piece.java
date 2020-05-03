package de.deepchess.game;

import java.awt.image.BufferedImage;

import de.deepchess.util.ImageUtil;

public class Piece {
	
	public static Piece BLACK_BISHOP=new Piece(PieceColor.BLACK,PieceType.BISHOP,ImageUtil.BLACK_BISHOP);
	public static Piece BLACK_KING=new Piece(PieceColor.BLACK,PieceType.KING,ImageUtil.BLACK_KING);
	public static Piece BLACK_KNIGHT=new Piece(PieceColor.BLACK,PieceType.KNIGHT,ImageUtil.BLACK_KNIGHT);
	public static Piece BLACK_PAWN=new Piece(PieceColor.BLACK,PieceType.PAWN,ImageUtil.BLACK_PAWN);
	public static Piece BLACK_QUEEN=new Piece(PieceColor.BLACK,PieceType.QUEEN,ImageUtil.BLACK_QUEEN);
	public static Piece BLACK_ROOK=new Piece(PieceColor.BLACK,PieceType.ROOK,ImageUtil.BLACK_ROOK);
	
	public static Piece WHITE_BISHOP=new Piece(PieceColor.WHITE,PieceType.BISHOP,ImageUtil.WHITE_BISHOP);
	public static Piece WHITE_KING=new Piece(PieceColor.WHITE,PieceType.KING,ImageUtil.WHITE_KING);
	public static Piece WHITE_KNIGHT=new Piece(PieceColor.WHITE,PieceType.KNIGHT,ImageUtil.WHITE_KNIGHT);
	public static Piece WHITE_PAWN=new Piece(PieceColor.WHITE,PieceType.PAWN,ImageUtil.WHITE_PAWN);
	public static Piece WHITE_QUEEN=new Piece(PieceColor.WHITE,PieceType.QUEEN,ImageUtil.WHITE_QUEEN);
	public static Piece WHITE_ROOK=new Piece(PieceColor.WHITE,PieceType.ROOK,ImageUtil.WHITE_ROOK);
	
	private PieceColor color;
	private PieceType type;
	private BufferedImage image;
	
	private boolean moved=false;
	private boolean didPawnJump=false;
	
	public Piece(PieceColor color, PieceType type, BufferedImage image) {
		this.color=color;
		this.type=type;
		this.image=image;
	}
	
	public PieceColor getColor() {
		return color;
	}
	public PieceType getType() {
		return type;
	}
	public BufferedImage getImage() {
		return image;
	}
	
	public boolean hasMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved=moved;
	}
	public boolean didPawnJump() {
		return didPawnJump;
	}
	public void setDidPawnJump(boolean didPawnJump) {
		this.didPawnJump = didPawnJump;
	}
	
	@Override
	public Piece clone() {
		Piece clone=new Piece(color, type, image);
		clone.setMoved(moved);
		clone.setDidPawnJump(didPawnJump);
		return clone;
	}
	
}
