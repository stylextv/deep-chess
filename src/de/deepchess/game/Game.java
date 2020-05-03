package de.deepchess.game;

import java.util.ArrayList;

import de.deepchess.ai.MinimaxAi;

public class Game {
	
	private static String RANKS="abcdefgh";
	
	private Piece[] pieces=new Piece[64];
	
	private PieceColor whoseTurn;
	private int fiftyRule;
	
	public Game() {
		reset();
	}
	public void reset() {
		whoseTurn=PieceColor.WHITE;
		fiftyRule=0;
		for(int i=0; i<pieces.length; i++) pieces[i]=null;
		
		setPiece(0, 0, Piece.BLACK_ROOK.clone());
		setPiece(1, 0, Piece.BLACK_KNIGHT.clone());
		setPiece(2, 0, Piece.BLACK_BISHOP.clone());
		setPiece(3, 0, Piece.BLACK_QUEEN.clone());
		setPiece(4, 0, Piece.BLACK_KING.clone());
		setPiece(5, 0, Piece.BLACK_BISHOP.clone());
		setPiece(6, 0, Piece.BLACK_KNIGHT.clone());
		setPiece(7, 0, Piece.BLACK_ROOK.clone());
		
		setPiece(0, 7, Piece.WHITE_ROOK.clone());
		setPiece(1, 7, Piece.WHITE_KNIGHT.clone());
		setPiece(2, 7, Piece.WHITE_BISHOP.clone());
		setPiece(3, 7, Piece.WHITE_QUEEN.clone());
		setPiece(4, 7, Piece.WHITE_KING.clone());
		setPiece(5, 7, Piece.WHITE_BISHOP.clone());
		setPiece(6, 7, Piece.WHITE_KNIGHT.clone());
		setPiece(7, 7, Piece.WHITE_ROOK.clone());
		
		for(int x=0; x<8; x++) {
			setPiece(x, 1, Piece.BLACK_PAWN.clone());
			setPiece(x, 6, Piece.WHITE_PAWN.clone());
		}
		
//		setPiece( 3,0, Piece.BLACK_ROOK.clone());
//		setPiece( 6,0, Piece.BLACK_KING.clone());
//		setPiece( 7,0, Piece.BLACK_ROOK.clone());
//		setPiece( 2,1, Piece.BLACK_PAWN.clone());
//		setPiece( 3,1, Piece.BLACK_QUEEN.clone());
//		setPiece( 4,1, Piece.BLACK_BISHOP.clone());
//		setPiece( 5,1, Piece.BLACK_PAWN.clone());
//		setPiece( 6,1, Piece.BLACK_PAWN.clone());
//		setPiece( 7,1, Piece.BLACK_PAWN.clone());
//		setPiece( 0,2, Piece.BLACK_PAWN.clone());
//		setPiece( 2,2, Piece.BLACK_KNIGHT.clone());
//		setPiece( 4,2, Piece.BLACK_BISHOP.clone());
//		setPiece( 1,3, Piece.BLACK_PAWN.clone());
//		setPiece( 3,3, Piece.BLACK_PAWN.clone());
//		setPiece( 4,3, Piece.WHITE_PAWN.clone());
//		setPiece( 5,3, Piece.BLACK_KNIGHT.clone());
//		setPiece( 0,4, Piece.WHITE_BISHOP.clone());
//		setPiece( 1,4, Piece.WHITE_PAWN.clone());
//		setPiece( 7,4, Piece.WHITE_PAWN.clone());
//		setPiece( 0,5, Piece.WHITE_KNIGHT.clone());
//		setPiece( 2,5, Piece.WHITE_PAWN.clone());
//		setPiece( 4,5, Piece.WHITE_PAWN.clone());
//		setPiece( 5,5, Piece.WHITE_KNIGHT.clone());
//		setPiece( 0,6, Piece.WHITE_PAWN.clone());
//		setPiece( 4,6, Piece.WHITE_QUEEN.clone());
//		setPiece( 5,6, Piece.WHITE_PAWN.clone());
//		setPiece( 6,6, Piece.WHITE_PAWN.clone());
//		setPiece( 0,7, Piece.WHITE_ROOK.clone());
//		setPiece( 2,7, Piece.WHITE_BISHOP.clone());
//		setPiece( 3,7, Piece.WHITE_ROOK.clone());
//		setPiece( 6,7, Piece.WHITE_KING.clone());
//		for(Piece p:pieces) {
//			if(p!=null) p.setMoved(true);
//		}
	}
	
	public ArrayList<Move> getAllMoves() {
		ArrayList<Move> moves=new ArrayList<Move>();
		for(int x=0; x<8; x++) {
			for(int y=0; y<8; y++) {
				Piece checkPiece=getPiece(x, y);
				if(checkPiece!=null&&checkPiece.getColor()==whoseTurn) {
					getMoves(x, y, moves, true, true);
				}
			}
		}
		return moves;
	}
	public void getMoves(int x, int y, ArrayList<Move> moves, boolean checkCheck, boolean checkCastle) {
		int from=y*8+x;
		
		Piece p=getPiece(x, y);
		PieceColor pColor=p.getColor();
		switch(p.getType()) {
		case BISHOP:
			
			int i=1;
			while(true) {
				int rx=x+i;
				int ry=y+i;
				if(rx>7||ry>7) break;
				Piece checkPiece=getPiece(rx, ry);
				if(checkPiece==null) {
					addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
					break;
				}
				i++;
			}
			i=1;
			while(true) {
				int rx=x-i;
				int ry=y+i;
				if(rx<0||ry>7) break;
				Piece checkPiece=getPiece(rx, ry);
				if(checkPiece==null) {
					addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
					break;
				}
				i++;
			}
			i=1;
			while(true) {
				int rx=x+i;
				int ry=y-i;
				if(rx>7||ry<0) break;
				Piece checkPiece=getPiece(rx, ry);
				if(checkPiece==null) {
					addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
					break;
				}
				i++;
			}
			i=1;
			while(true) {
				int rx=x-i;
				int ry=y-i;
				if(rx<0||ry<0) break;
				Piece checkPiece=getPiece(rx, ry);
				if(checkPiece==null) {
					addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
					break;
				}
				i++;
			}
			
			break;
		case KING:
			
			for(int cx=-1; cx<=1; cx++) {
				for(int cy=-1; cy<=1; cy++) {
					int rx=x+cx;
					int ry=y+cy;
					if(rx>=0&&ry>=0 && rx<8&&ry<8) {
						Piece checkPiece=getPiece(rx, ry);
						if(checkPiece==null||checkPiece.getColor()!=p.getColor()) addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
					}
				}
			}
			p=getPiece(x, y);
			if(checkCastle&&!p.hasMoved()) {
				
				Piece rook1=getPiece(0, y);
				Piece rook2=getPiece(7, y);
				if(rook1!=null&&!rook1.hasMoved()) {
					
					boolean eyeSight=true;
					for(int cx=x-1; cx>0; cx--) {
						if(getPiece(cx, y)!=null) {
							eyeSight=false;
							break;
						}
					}
					if(eyeSight) {
						boolean save=true;
						for(int cx=x; cx>=x-2; cx--) {
							if(isUnderAttack(p.getColor(), cx, y)) {
								save=false;
								break;
							}
						}
						if(save) {
							Move m=new Move(from, y*8+x-2);
							m.setCastle(true);
							m.setCastleFrom(y*8+0);
							m.setCastleTo(y*8+x-1);
							addMove(m, pColor, moves, false);
						}
					}
					
				}
				if(rook2!=null&&!rook2.hasMoved()) {
					
					boolean eyeSight=true;
					for(int cx=x+1; cx<7; cx++) {
						if(getPiece(cx, y)!=null) {
							eyeSight=false;
							break;
						}
					}
					if(eyeSight) {
						boolean save=true;
						for(int cx=x; cx<=x+2; cx++) {
							if(isUnderAttack(p.getColor(), cx, y)) {
								save=false;
								break;
							}
						}
						if(save) {
							Move m=new Move(from, y*8+x+2);
							m.setCastle(true);
							m.setCastleFrom(y*8+7);
							m.setCastleTo(y*8+x+1);
							addMove(m, pColor, moves, false);
						}
					}
					
				}
				
			}
			
			break;
		case KNIGHT:
			
			int jx=x+1;
			int jy=y-2;
			if(jx<8&&jy>=0) {
				Piece checkPiece=getPiece(jx, jy);
				if(checkPiece==null||checkPiece.getColor()!=p.getColor()) addMove(new Move(from, jy*8+jx), pColor, moves, checkCheck);
			}
			jx=x+2;
			jy=y-1;
			if(jx<8&&jy>=0) {
				Piece checkPiece=getPiece(jx, jy);
				if(checkPiece==null||checkPiece.getColor()!=p.getColor()) addMove(new Move(from, jy*8+jx), pColor, moves, checkCheck);
			}
			jx=x+2;
			jy=y+1;
			if(jx<8&&jy<8) {
				Piece checkPiece=getPiece(jx, jy);
				if(checkPiece==null||checkPiece.getColor()!=p.getColor()) addMove(new Move(from, jy*8+jx), pColor, moves, checkCheck);
			}
			jx=x+1;
			jy=y+2;
			if(jx<8&&jy<8) {
				Piece checkPiece=getPiece(jx, jy);
				if(checkPiece==null||checkPiece.getColor()!=p.getColor()) addMove(new Move(from, jy*8+jx), pColor, moves, checkCheck);
			}
			jx=x-1;
			jy=y+2;
			if(jx>=0&&jy<8) {
				Piece checkPiece=getPiece(jx, jy);
				if(checkPiece==null||checkPiece.getColor()!=p.getColor()) addMove(new Move(from, jy*8+jx), pColor, moves, checkCheck);
			}
			jx=x-2;
			jy=y+1;
			if(jx>=0&&jy<8) {
				Piece checkPiece=getPiece(jx, jy);
				if(checkPiece==null||checkPiece.getColor()!=p.getColor()) addMove(new Move(from, jy*8+jx), pColor, moves, checkCheck);
			}
			jx=x-2;
			jy=y-1;
			if(jx>=0&&jy>=0) {
				Piece checkPiece=getPiece(jx, jy);
				if(checkPiece==null||checkPiece.getColor()!=p.getColor()) addMove(new Move(from, jy*8+jx), pColor, moves, checkCheck);
			}
			jx=x-1;
			jy=y-2;
			if(jx>=0&&jy>=0) {
				Piece checkPiece=getPiece(jx, jy);
				if(checkPiece==null||checkPiece.getColor()!=p.getColor()) addMove(new Move(from, jy*8+jx), pColor, moves, checkCheck);
			}
			
			break;
		case PAWN:
			
			int facing=p.getColor()==PieceColor.BLACK ? 1 : -1;
			int forwardY=y+facing;
			if(forwardY>=0 && forwardY<8) {
				if(x-1>=0) {
					Piece checkPiece=getPiece(x-1, forwardY);
					if(checkPiece!=null&&checkPiece.getColor()!=p.getColor()) {
						Move m=new Move(from, forwardY*8+x-1);
						m.setPromote((facing==1&&forwardY==7)||(facing==-1&&forwardY==0));
						addMove(m, pColor, moves, checkCheck);
					}
					Piece checkPiece2=getPiece(x-1, y);
					if(checkPiece2!=null&&checkPiece2.didPawnJump()&&checkPiece2.getColor()!=p.getColor()) {
						Move m=new Move(from, forwardY*8+x-1);
						m.setPromote((facing==1&&forwardY==7)||(facing==-1&&forwardY==0));
						m.setCapturedPawnJumper(checkPiece2);
						m.setCapturedPawnJumperIndex(y*8+x-1);
						addMove(m, pColor, moves, checkCheck);
					}
				}
				if(x+1<8) {
					Piece checkPiece=getPiece(x+1, forwardY);
					if(checkPiece!=null&&checkPiece.getColor()!=p.getColor()) {
						Move m=new Move(from, forwardY*8+x+1);
						m.setPromote((facing==1&&forwardY==7)||(facing==-1&&forwardY==0));
						addMove(m, pColor, moves, checkCheck);
					}
					Piece checkPiece2=getPiece(x+1, y);
					if(checkPiece2!=null&&checkPiece2.didPawnJump()&&checkPiece2.getColor()!=p.getColor()) {
						Move m=new Move(from, forwardY*8+x+1);
						m.setPromote((facing==1&&forwardY==7)||(facing==-1&&forwardY==0));
						m.setCapturedPawnJumper(checkPiece2);
						m.setCapturedPawnJumperIndex(y*8+x+1);
						addMove(m, pColor, moves, checkCheck);
					}
				}
				
				Piece checkPiece=getPiece(x, forwardY);
				if(checkPiece==null) {
					Move m2=new Move(from, forwardY*8+x);
					m2.setPromote((facing==1&&forwardY==7)||(facing==-1&&forwardY==0));
					addMove(m2, pColor, moves, checkCheck);
					p=getPiece(x, y);
					if(!p.hasMoved()) {
						int forwardY2=forwardY+facing;
						if(forwardY2>=0 && forwardY2<8 && getPiece(x, forwardY2)==null) {
							Move m=new Move(from, forwardY2*8+x);
							m.setPromote((facing==1&&forwardY2==7)||(facing==-1&&forwardY2==0));
							m.setPawnJump(true);
							addMove(m, pColor, moves, checkCheck);
						}
					}
				}
			}
			
			break;
		case QUEEN:
			
			for(int cx=x-1; cx>=0; cx--) {
				Piece checkPiece=getPiece(cx, y);
				if(checkPiece==null) {
					addMove(new Move(from, y*8+cx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, y*8+cx), pColor, moves, checkCheck);
					break;
				}
			}
			for(int cx=x+1; cx<8; cx++) {
				Piece checkPiece=getPiece(cx, y);
				if(checkPiece==null) {
					addMove(new Move(from, y*8+cx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, y*8+cx), pColor, moves, checkCheck);
					break;
				}
			}
			for(int cy=y-1; cy>=0; cy--) {
				Piece checkPiece=getPiece(x, cy);
				if(checkPiece==null) {
					addMove(new Move(from, cy*8+x), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, cy*8+x), pColor, moves, checkCheck);
					break;
				}
			}
			for(int cy=y+1; cy<8; cy++) {
				Piece checkPiece=getPiece(x, cy);
				if(checkPiece==null) {
					addMove(new Move(from, cy*8+x), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, cy*8+x), pColor, moves, checkCheck);
					break;
				}
			}
			i=1;
			while(true) {
				int rx=x+i;
				int ry=y+i;
				if(rx>7||ry>7) break;
				Piece checkPiece=getPiece(rx, ry);
				if(checkPiece==null) {
					addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
					break;
				}
				i++;
			}
			i=1;
			while(true) {
				int rx=x-i;
				int ry=y+i;
				if(rx<0||ry>7) break;
				Piece checkPiece=getPiece(rx, ry);
				if(checkPiece==null) {
					addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
					break;
				}
				i++;
			}
			i=1;
			while(true) {
				int rx=x+i;
				int ry=y-i;
				if(rx>7||ry<0) break;
				Piece checkPiece=getPiece(rx, ry);
				if(checkPiece==null) {
					addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
					break;
				}
				i++;
			}
			i=1;
			while(true) {
				int rx=x-i;
				int ry=y-i;
				if(rx<0||ry<0) break;
				Piece checkPiece=getPiece(rx, ry);
				if(checkPiece==null) {
					addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, ry*8+rx), pColor, moves, checkCheck);
					break;
				}
				i++;
			}
			
			break;
		case ROOK:
			
			for(int cx=x-1; cx>=0; cx--) {
				Piece checkPiece=getPiece(cx, y);
				if(checkPiece==null) {
					addMove(new Move(from, y*8+cx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, y*8+cx), pColor, moves, checkCheck);
					break;
				}
			}
			for(int cx=x+1; cx<8; cx++) {
				Piece checkPiece=getPiece(cx, y);
				if(checkPiece==null) {
					addMove(new Move(from, y*8+cx), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, y*8+cx), pColor, moves, checkCheck);
					break;
				}
			}
			for(int cy=y-1; cy>=0; cy--) {
				Piece checkPiece=getPiece(x, cy);
				if(checkPiece==null) {
					addMove(new Move(from, cy*8+x), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, cy*8+x), pColor, moves, checkCheck);
					break;
				}
			}
			for(int cy=y+1; cy<8; cy++) {
				Piece checkPiece=getPiece(x, cy);
				if(checkPiece==null) {
					addMove(new Move(from, cy*8+x), pColor, moves, checkCheck);
				} else {
					if(checkPiece.getColor()!=p.getColor()) addMove(new Move(from, cy*8+x), pColor, moves, checkCheck);
					break;
				}
			}
			
			break;
		default:
			break;
		}
		
	}
	private void addMove(Move m, PieceColor pColor, ArrayList<Move> moves, boolean checkCheck) {
		if(checkCheck) {
			performMove(m);
			if(!isInCheck(pColor)) moves.add(m);
			undoMove(m);
		} else {
			moves.add(m);
		}
	}
	public void performMove(Move m) {
		for(Piece p:pieces) {
			if(p!=null&&p.didPawnJump()) {
				p.setDidPawnJump(false);
				m.getPreviousPawnJumpers().add(p);
			}
		}
		
		Piece p=pieces[m.getFrom()];
		Piece target=pieces[m.getTo()];
		m.setFiftyRule(fiftyRule);
		if(p.getType()==PieceType.PAWN||target!=null) fiftyRule=0;
		else fiftyRule+=1;
		
		m.getPreviousPieces()[0]=p.clone();
		m.getPreviousPieces()[1]=target != null ? target.clone() : null;
		if(m.isPromote()) {
			if(p.getColor()==PieceColor.BLACK) pieces[m.getTo()]=Piece.BLACK_QUEEN;
			else pieces[m.getTo()]=Piece.WHITE_QUEEN;
		} else {
			pieces[m.getTo()]=p;
			if(m.isPawnJump()) p.setDidPawnJump(true);
		}
		pieces[m.getTo()].setMoved(true);
		pieces[m.getFrom()]=null;
		
		if(m.getCapturedPawnJumper()!=null) {
			setPiece(m.getCapturedPawnJumperIndex(), null);
		}
		if(m.isCastle()) {
			Piece rook=pieces[m.getCastleFrom()];
			m.setCastlePiece(rook.clone());
			pieces[m.getCastleTo()]=rook;
			rook.setMoved(true);
			pieces[m.getCastleFrom()]=null;
		}
		whoseTurn=whoseTurn.opposite();
	}
	public void undoMove(Move m) {
		whoseTurn=whoseTurn.opposite();
		if(m.isCastle()) {
			pieces[m.getCastleFrom()]=m.getCastlePiece();
			pieces[m.getCastleTo()]=null;
		}
		if(m.getCapturedPawnJumper()!=null) {
			setPiece(m.getCapturedPawnJumperIndex(), m.getCapturedPawnJumper());
		}
		pieces[m.getFrom()]=m.getPreviousPieces()[0];
		pieces[m.getTo()]=m.getPreviousPieces()[1];
		for(Piece p:m.getPreviousPawnJumpers()) {
			p.setDidPawnJump(true);
		}
		fiftyRule=m.getFiftyRule();
		m.getPreviousPawnJumpers().clear();
	}
	
	public Move performAiMove() {
		return MinimaxAi.performMove(this);
	}
	
	public boolean isInCheck(PieceColor color) {
		for(int cx=0; cx<8; cx++) {
			for(int cy=0; cy<8; cy++) {
				Piece checkPiece=getPiece(cx, cy);
				if(checkPiece!=null&&checkPiece.getType()==PieceType.KING&&checkPiece.getColor()==color) {
					return isUnderAttack(color, cx, cy);
				}
			}
		}
		return true;
	}
	public boolean isUnderAttack(PieceColor color, int x, int y) {
		int index=y*8+x;
		for(int cx=0; cx<8; cx++) {
			for(int cy=0; cy<8; cy++) {
				Piece checkPiece=getPiece(cx, cy);
				if(checkPiece!=null&&checkPiece.getColor()!=color) {
					ArrayList<Move> moves=new ArrayList<Move>();
					getMoves(cx, cy, moves, false, false);
					for(Move m:moves) {
						if(m.getTo()==index) return true;
					}
				}
			}
		}
		return false;
	}
	public Winner getWinner() {
		ArrayList<Move> moves=getAllMoves();
		return getWinner(moves.size()==0);
	}
	public Winner getWinner(boolean noMovesLeft) {
		if(fiftyRule==50) return Winner.DRAW;
		if(noMovesLeft) {
			if(!isInCheck(whoseTurn)) return Winner.DRAW;
			if(whoseTurn==PieceColor.BLACK) return Winner.WHITE;
			return Winner.BLACK;
		}
		return Winner.NONE;
	}
	
	public Piece getPiece(int x, int y) {
		return pieces[y*8+x];
	}
	public Piece getPiece(int index) {
		return pieces[index];
	}
	public void setPiece(int x, int y, Piece p) {
		pieces[y*8+x]=p;
	}
	public void setPiece(int index, Piece p) {
		pieces[index]=p;
	}
	
	public PieceColor whoseTurn() {
		return whoseTurn;
	}
	public void setWhoseTurn(PieceColor whoseTurn) {
		this.whoseTurn=whoseTurn;
	}
	
	public String getHashWithoutMoveCounters() {
		String key="";
		int nullCounter=0;
		for(int i=0; i<64; i++) {
			Piece p=pieces[i];
			if(p==null) {
				nullCounter++;
			} else {
				if(nullCounter!=0) {
					key=key+nullCounter;
					nullCounter=0;
				}
				
				char ch=0;
				switch(p.getType()) {
				case BISHOP:
					ch='b';
					break;
				case KING:
					ch='k';
					break;
				case KNIGHT:
					ch='n';
					break;
				case PAWN:
					ch='p';
					break;
				case QUEEN:
					ch='q';
					break;
				case ROOK:
					ch='r';
					break;
				}
				if(p.getColor()==PieceColor.BLACK) key=key+ch;
				else key=key+(ch+"").toUpperCase();
			}
			
			if(i%8==7&&i!=63) {
				if(nullCounter!=0) {
					key=key+nullCounter;
					nullCounter=0;
				}
				key=key+"/";
			}
		}
		if(nullCounter!=0) {
			key=key+nullCounter;
			nullCounter=0;
		}
		
		if(whoseTurn==PieceColor.WHITE) key=key+" w";
		else key=key+" b";
		String castles="";
		Piece kingWhite=pieces[7*8+4];
		Piece kingBlack=pieces[4];
		if(kingWhite!=null&&kingWhite.getType()==PieceType.KING&&kingWhite.getColor()==PieceColor.WHITE&&!kingWhite.hasMoved()) {
			Piece rook1=pieces[7*8+7];
			Piece rook2=pieces[7*8];
			if(rook1!=null&&rook1.getType()==PieceType.ROOK&&rook1.getColor()==PieceColor.WHITE&&!rook1.hasMoved()) {
				castles=castles+"K";
			}
			if(rook2!=null&&rook2.getType()==PieceType.ROOK&&rook2.getColor()==PieceColor.WHITE&&!rook2.hasMoved()) {
				castles=castles+"Q";
			}
		}
		if(kingBlack!=null&&kingBlack.getType()==PieceType.KING&&kingBlack.getColor()==PieceColor.BLACK&&!kingBlack.hasMoved()) {
			Piece rook1=pieces[7];
			Piece rook2=pieces[0];
			if(rook1!=null&&rook1.getType()==PieceType.ROOK&&rook1.getColor()==PieceColor.BLACK&&!rook1.hasMoved()) {
				castles=castles+"k";
			}
			if(rook2!=null&&rook2.getType()==PieceType.ROOK&&rook2.getColor()==PieceColor.BLACK&&!rook2.hasMoved()) {
				castles=castles+"q";
			}
		}
		if(castles.length()==0) castles="-";
		key=key+" "+castles;
		
		boolean hasPawnJumper=false;
		for(int i=0; i<64; i++) {
			Piece p=pieces[i];
			if(p!=null&&p.getColor()!=whoseTurn&&p.didPawnJump()) {
				int x=i%8;
				int y=i/8;
				if(p.getColor()==PieceColor.BLACK) y--;
				else y++;
				key=key+" "+RANKS.charAt(x)+((7-y)+1);
				hasPawnJumper=true;
				break;
			}
		}
		if(!hasPawnJumper) {
			key=key+" -";
		}
		
		return key;
	}
	
}
