package de.deepchess.ai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import de.deepchess.game.Game;

public class StockfishAi {
	
	private static Process process;
	private static boolean running;
	private static boolean kill;
	
	private static Game inputGame;
	private static String outputMove;
	
	public static boolean start() {
		try {
			running=true;
			process = new ProcessBuilder("engines/stockfish_20011801_x64.exe").start();
			
			new Thread(() -> {
				try {
					InputStream is = process.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String line;
					OutputStream os = process.getOutputStream();
					OutputStreamWriter osr=new OutputStreamWriter(os);
					BufferedWriter bw=new BufferedWriter(osr);
					
					while(!kill) {
						if(br.ready()) {
							line=br.readLine();
							if(line.startsWith("bestmove ")) {
								outputMove=line;
							}
						}
						if(inputGame!=null) {
							writeLine(bw, "position fen "+inputGame.getHash());
							writeLine(bw, "go movetime 1000");
							inputGame=null;
						}
						
						Thread.sleep(100);
					}
					br.close();
					bw.close();
				} catch (IOException | InterruptedException ex) {
					ex.printStackTrace();
				}
				running=false;
			}).start();
		} catch (IOException ex) {
			return false;
		}
		return true;
	}
	private static void writeLine(BufferedWriter bw, String s) throws IOException {
		bw.write(s);
		bw.newLine();
		bw.flush();
	}
	
	public static void kill() {
		if(process!=null) {
			kill=true;
			process.destroy();
			while(running) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException ex) {}
			}
		}
	}
	
	public static void setInputGame(Game game) {
		inputGame=game;
	}
	public static boolean hasOutputMove() {
		return outputMove!=null;
	}
	public static String getOutputMove() {
		return outputMove;
	}
	public static void flushOutputMove() {
		outputMove=null;
	}
	
}
