package de.deepchess.game;

import java.util.ArrayList;

public class Move {
	
	private int from;
	private int to;
	
	private int castleFrom;
	private int castleTo;
	private Piece castlePiece;
	private boolean castle;
	
	private boolean promote;
	private boolean pawnJump;
	
	private Piece[] previousPieces;
	private ArrayList<Piece> previousPawnJumpers=new ArrayList<Piece>();
	private Piece capturedPawnJumper=null;
	private int capturedPawnJumperIndex=0;
	
	private int fiftyRule;
	private boolean marked;
	
	public Move(int from, int to) {
		this.from=from;
		this.to=to;
		
		this.previousPieces=new Piece[2];
	}
	
	public boolean isSameMove(Move other) {
		return from==other.getFrom()&&to==other.getTo();
	}
	
	public int getFrom() {
		return from;
	}
	public int getTo() {
		return to;
	}
	public Piece[] getPreviousPieces() {
		return previousPieces;
	}
	public ArrayList<Piece> getPreviousPawnJumpers() {
		return previousPawnJumpers;
	}
	
	public Piece getCapturedPawnJumper() {
		return capturedPawnJumper;
	}
	public void setCapturedPawnJumper(Piece capturedPawnJumper) {
		this.capturedPawnJumper = capturedPawnJumper;
	}
	public int getCapturedPawnJumperIndex() {
		return capturedPawnJumperIndex;
	}
	public void setCapturedPawnJumperIndex(int capturedPawnJumperIndex) {
		this.capturedPawnJumperIndex = capturedPawnJumperIndex;
	}
	
	public boolean isPromote() {
		return promote;
	}
	public void setPromote(boolean promote) {
		this.promote=promote;
	}
	public boolean isPawnJump() {
		return pawnJump;
	}
	public void setPawnJump(boolean pawnJump) {
		this.pawnJump = pawnJump;
	}
	
	public int getCastleFrom() {
		return castleFrom;
	}
	public void setCastleFrom(int castleFrom) {
		this.castleFrom = castleFrom;
	}
	public int getCastleTo() {
		return castleTo;
	}
	public void setCastleTo(int castleTo) {
		this.castleTo = castleTo;
	}
	public Piece getCastlePiece() {
		return castlePiece;
	}
	public void setCastlePiece(Piece castlePiece) {
		this.castlePiece = castlePiece;
	}
	public boolean isCastle() {
		return castle;
	}
	public void setCastle(boolean castle) {
		this.castle = castle;
	}
	
	public boolean isMarked() {
		return marked;
	}
	public void setMarked(boolean marked) {
		this.marked=marked;
	}
	public int getFiftyRule() {
		return fiftyRule;
	}
	public void setFiftyRule(int fiftyRule) {
		this.fiftyRule = fiftyRule;
	}
	
}
