package de.deepchess.game;

public enum PieceColor {
	
	BLACK,WHITE;
	
	public PieceColor opposite() {
		if(this==BLACK) return WHITE;
		return BLACK;
	}
	
}
