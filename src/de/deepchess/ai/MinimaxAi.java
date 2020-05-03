package de.deepchess.ai;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import de.deepchess.game.Game;
import de.deepchess.game.Move;
import de.deepchess.game.PieceColor;
import de.deepchess.game.Winner;

public class MinimaxAi {
	
	private static DecimalFormat DECIMAL_FORMAT=new DecimalFormat("###,###.###");
	
	private static HashMap<String, Integer> transpositionTable=new HashMap<String, Integer>();
	private static int transpositionUses;
	
	private static int INFINITY=100000;
	private static int MAX_DEPTH=7;
	private static int MAX_ADJUSTED_DEPTH=3;
	
	private static Move[] killerMoves=new Move[(MAX_DEPTH-1)*2];
	private static int killerMoveSwap=0;
	private static int evaluted=0;
	
	public static void clearCache() {
		Iterator<Entry<String, Integer>> iter=transpositionTable.entrySet().iterator();
		while(transpositionTable.size()>1500000) {
			iter.next();
			iter.remove();
		}
	}
	
	public static Move performMove(Game game) {
		for(int i=0; i<killerMoves.length; i++) {
			killerMoves[i]=null;
		}
		evaluted=0;
		
		ArrayList<Move> moves=game.getAllMoves();
		
		System.out.println("starting search");
		long before=System.currentTimeMillis();
		Move bestMove=null;
		int bestMoveScore=0;
		for(int i=0; i<moves.size(); i++) {
			Move m=moves.get(i);
			boolean isCapture=game.getPiece(m.getTo())!=null;
			game.performMove(m);
			
			int score=min(game, bestMoveScore, 1, isCapture ? 0 : 1);
			if(score>bestMoveScore||bestMove==null) {
				bestMoveScore=score;
				bestMove=m;
			}
			
			game.undoMove(m);
			System.out.println((int)((i+1.0)/(moves.size())*100)+"%");
		}
		long after=System.currentTimeMillis();
		System.out.println("took "+DECIMAL_FORMAT.format((after-before)/1000.0)+"s");
		System.out.println("evaluted states: "+DECIMAL_FORMAT.format(evaluted));
		System.out.println("prediction: "+bestMoveScore);
		System.out.println("transposition uses: "+DECIMAL_FORMAT.format(transpositionUses));
		
		transpositionUses=0;
		game.performMove(bestMove);
		return bestMove;
	}
	private static int min(Game game, int currentMaxScore, int depth, int adjustedDepth) {
		String hash=game.getHashWithoutMoveCounters();
		Integer got=transpositionTable.get(hash);
		if(got!=null) {
			transpositionUses++;
			return got;
		}
		
		ArrayList<Move> moves=game.getAllMoves();
		Winner winner=game.getWinner(moves.size()==0);
		if(winner!=Winner.NONE) {
			evaluted++;
			int score=0;
			switch(winner) {
			case BLACK:
				score=INFINITY;
			case WHITE:
				score=-INFINITY;
			default:
				break;
			}
			transpositionTable.put(hash, score);
			return score;
		}
		if(depth==MAX_DEPTH||adjustedDepth==MAX_ADJUSTED_DEPTH) {
			evaluted++;
			int score=Evaluator.eval(game, PieceColor.BLACK);
			transpositionTable.put(hash, score);
			return score;
		}
		
		int killerIndex=(depth-1)*2;
		Move killerMove1=killerMoves[killerIndex];
		Move killerMove2=killerMoves[killerIndex+1];
		int bestMoveScore=Integer.MAX_VALUE;
		if(killerMove1!=null||killerMove2!=null) for(Move m:moves) {
			if((killerMove1!=null&&m.isSameMove(killerMove1))||(killerMove2!=null&&m.isSameMove(killerMove2))) {
				
				boolean isCapture=game.getPiece(m.getTo())!=null;
				game.performMove(m);
				
				int score=max(game, bestMoveScore, depth+1, isCapture ? adjustedDepth : adjustedDepth+1);
				if(score<bestMoveScore) {
					bestMoveScore=score;
				}
				
				game.undoMove(m);
				if(bestMoveScore<=currentMaxScore) {
					return bestMoveScore;
				}
				
				m.setMarked(true);
				
			}
		}
		for(Move m:moves) {
			if(!m.isMarked()) {
				boolean isCapture=game.getPiece(m.getTo())!=null;
				game.performMove(m);
				
				int score=max(game, bestMoveScore, depth+1, isCapture ? adjustedDepth : adjustedDepth+1);
				if(score<bestMoveScore) {
					bestMoveScore=score;
				}
				
				game.undoMove(m);
				if(bestMoveScore<=currentMaxScore) {
					if(killerMoves[killerIndex]==null) killerMoves[killerIndex]=m;
					else if(killerMoves[killerIndex+1]==null) killerMoves[killerIndex+1]=m;
					else if(killerMoveSwap==0) {
						killerMoves[killerIndex]=m;
						killerMoveSwap=1;
					} else {
						killerMoves[killerIndex+1]=m;
						killerMoveSwap=0;
					}
					return bestMoveScore;
				}
			}
		}
		
		return bestMoveScore;
	}
	private static int max(Game game, int currentMinScore, int depth, int adjustedDepth) {
		String hash=game.getHashWithoutMoveCounters();
		Integer got=transpositionTable.get(hash);
		if(got!=null) {
			transpositionUses++;
			return got;
		}
		
		ArrayList<Move> moves=game.getAllMoves();
		Winner winner=game.getWinner(moves.size()==0);
		if(winner!=Winner.NONE) {
			evaluted++;
			int score=0;
			switch(winner) {
			case BLACK:
				score=INFINITY;
			case WHITE:
				score=-INFINITY;
			default:
				break;
			}
			transpositionTable.put(hash, score);
			return score;
		}
		if(depth==MAX_DEPTH||adjustedDepth==MAX_ADJUSTED_DEPTH) {
			evaluted++;
			int score=Evaluator.eval(game, PieceColor.BLACK);
			transpositionTable.put(hash, score);
			return score;
		}
		
		int killerIndex=(depth-1)*2;
		Move killerMove1=killerMoves[killerIndex];
		Move killerMove2=killerMoves[killerIndex+1];
		int bestMoveScore=Integer.MIN_VALUE;
		if(killerMove1!=null||killerMove2!=null) for(Move m:moves) {
			if((killerMove1!=null&&m.isSameMove(killerMove1))||(killerMove2!=null&&m.isSameMove(killerMove2))) {
				
				boolean isCapture=game.getPiece(m.getTo())!=null;
				game.performMove(m);
				
				int score=min(game, bestMoveScore, depth+1, isCapture ? adjustedDepth : adjustedDepth+1);
				if(score>bestMoveScore) {
					bestMoveScore=score;
				}
				
				game.undoMove(m);
				if(bestMoveScore>=currentMinScore) {
					return bestMoveScore;
				}
				
				m.setMarked(true);
				
			}
		}
		for(Move m:moves) {
			if(!m.isMarked()) {
				boolean isCapture=game.getPiece(m.getTo())!=null;
				game.performMove(m);
				
				int score=min(game, bestMoveScore, depth+1, isCapture ? adjustedDepth : adjustedDepth+1);
				if(score>bestMoveScore) {
					bestMoveScore=score;
				}
				
				game.undoMove(m);
				if(bestMoveScore>=currentMinScore) {
					if(killerMoves[killerIndex]==null) killerMoves[killerIndex]=m;
					else if(killerMoves[killerIndex+1]==null) killerMoves[killerIndex+1]=m;
					else if(killerMoveSwap==0) {
						killerMoves[killerIndex]=m;
						killerMoveSwap=1;
					} else {
						killerMoves[killerIndex+1]=m;
						killerMoveSwap=0;
					}
					return bestMoveScore;
				}
			}
		}
		
		return bestMoveScore;
	}
	
}
