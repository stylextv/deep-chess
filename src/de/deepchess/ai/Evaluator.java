package de.deepchess.ai;

import de.deepchess.game.Game;
import de.deepchess.game.Piece;
import de.deepchess.game.PieceColor;

public class Evaluator {
	
	private static int VALUE_PAWN=10;
	private static int VALUE_KNIGHT=32;
	private static int VALUE_BISHOP=33;
	private static int VALUE_ROOK=51;
	private static int VALUE_QUEEN=88;
	private static int VALUE_KING=90;
	private static int[] TABLE_PAWN=new int[]{
			 0,  0,  0,  0,  0,  0,  0,  0,
			 5, 10, 10,-20,-20, 10, 10,  5,
			 5, -5,-10,  0,  0,-10, -5,  5,
			 0,  0,  0, 20, 20,  0,  0,  0,
			 5,  5, 10, 25, 25, 10,  5,  5,
			10, 10, 20, 30, 30, 20, 10, 10,
			50, 50, 50, 50, 50, 50, 50, 50,
			 0,  0,  0,  0,  0,  0,  0,  0
	};
	private static int[] TABLE_KNIGHT=new int[]{
			-50,-40,-30,-30,-30,-30,-40,-50,
			-40,-20,  0,  5,  5,  0,-20,-40,
			-30,  5, 10, 15, 15, 10,  5,-30,
			-30,  0, 15, 20, 20, 15,  0,-30,
			-30,  5, 15, 20, 20, 15,  5,-30,
			-30,  0, 10, 15, 15, 10,  0,-30,
			-40,-20,  0,  0,  0,  0,-20,-40,
			-50,-40,-30,-30,-30,-30,-40,-50
	};
	private static int[] TABLE_BISHOP=new int[]{
			-20,-10,-10,-10,-10,-10,-10,-20,
			-10,  5,  0,  0,  0,  0,  5,-10,
			-10, 10, 10, 10, 10, 10, 10,-10,
			-10,  0, 10, 10, 10, 10,  0,-10,
			-10,  5,  5, 10, 10,  5,  5,-10,
			-10,  0,  5, 10, 10,  5,  0,-10,
			-10,  0,  0,  0,  0,  0,  0,-10,
			-20,-10,-10,-10,-10,-10,-10,-20
	};
	private static int[] TABLE_ROOK=new int[]{
			 0,  0,  0,  5,  5,  0,  0,  0,
			-5,  0,  0,  0,  0,  0,  0, -5,
			-5,  0,  0,  0,  0,  0,  0, -5,
			-5,  0,  0,  0,  0,  0,  0, -5,
			-5,  0,  0,  0,  0,  0,  0, -5,
			-5,  0,  0,  0,  0,  0,  0, -5,
			 5, 10, 10, 10, 10, 10, 10,  5,
			 0,  0,  0,  0,  0,  0,  0,  0
	};
	private static int[] TABLE_QUEEN=new int[]{
			-20,-10,-10, -5, -5,-10,-10,-20,
			-10,  0,  5,  0,  0,  0,  0,-10,
			-10,  5,  5,  5,  5,  5,  0,-10,
			0,  0,  5,  5,  5,  5,  0, -5,
			-5,  0,  5,  5,  5,  5,  0, -5,
			-10,  0,  5,  5,  5,  5,  0,-10,
			-10,  0,  0,  0,  0,  0,  0,-10,
			-20,-10,-10, -5, -5,-10,-10,-20
	};
	private static int[] TABLE_KING=new int[]{
			 20, 30, 10,  0,  0, 10, 30, 20,
			 20, 20,  0,  0,  0,  0, 20, 20,
			-10,-20,-20,-20,-20,-20,-20,-10,
			-20,-30,-30,-40,-40,-30,-30,-20,
			-30,-40,-40,-50,-50,-40,-40,-30,
			-30,-40,-40,-50,-50,-40,-40,-30,
			-30,-40,-40,-50,-50,-40,-40,-30,
			-30,-40,-40,-50,-50,-40,-40,-30
	};
	static {
		for(int i=0; i<64; i++) {
			TABLE_PAWN[i]=reduceValue(TABLE_PAWN[i]);
			TABLE_KNIGHT[i]=reduceValue(TABLE_KNIGHT[i]);
			TABLE_BISHOP[i]=reduceValue(TABLE_BISHOP[i]);
			TABLE_ROOK[i]=reduceValue(TABLE_ROOK[i]);
			TABLE_QUEEN[i]=reduceValue(TABLE_QUEEN[i]);
			TABLE_KING[i]=reduceValue(TABLE_KING[i]);
		}
	}
	private static int reduceValue(int i) {
		return Math.round(i*0.4f);
	}
	
	public static int eval(Game game, PieceColor color) {
		int score=0;
		int enemyScore=0;
		for(int i=0; i<64; i++) {
			Piece checkPiece=game.getPiece(i);
			if(checkPiece!=null) {
				if(checkPiece.getColor()==color) {
					int pieceScore=0;
					switch(checkPiece.getType()) {
					case BISHOP:
						pieceScore=VALUE_BISHOP+TABLE_BISHOP[i];
						break;
					case KNIGHT:
						pieceScore=VALUE_KNIGHT+TABLE_KNIGHT[i];
						break;
					case PAWN:
						pieceScore=VALUE_PAWN+TABLE_PAWN[i];
						break;
					case QUEEN:
						pieceScore=VALUE_QUEEN+TABLE_QUEEN[i];
						break;
					case ROOK:
						pieceScore=VALUE_ROOK+TABLE_ROOK[i];
						break;
					case KING:
						pieceScore=VALUE_KING+TABLE_KING[i];
						break;
					}
					score+=pieceScore;
				} else {
					int pieceScore=0;
					switch(checkPiece.getType()) {
					case BISHOP:
						pieceScore=VALUE_BISHOP+TABLE_BISHOP[63-i];
						break;
					case KNIGHT:
						pieceScore=VALUE_KNIGHT+TABLE_KNIGHT[63-i];
						break;
					case PAWN:
						pieceScore=VALUE_PAWN+TABLE_PAWN[63-i];
						break;
					case QUEEN:
						pieceScore=VALUE_QUEEN+TABLE_QUEEN[63-i];
						break;
					case ROOK:
						pieceScore=VALUE_ROOK+TABLE_ROOK[63-i];
						break;
					case KING:
						pieceScore=VALUE_KING+TABLE_KING[63-i];
						break;
					}
					enemyScore+=pieceScore;
				}
			}
		}
		return score-enemyScore;
	}
	
}
