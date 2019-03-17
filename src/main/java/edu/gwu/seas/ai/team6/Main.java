package edu.gwu.seas.ai.team6;

import edu.gwu.seas.ai.team6.game.DefaultGame;
import edu.gwu.seas.ai.team6.game.alg.Algorithm;
import edu.gwu.seas.ai.team6.game.board.DefaultBoard;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

import java.util.Scanner;

public class Main {
    static DefaultBoard board = new DefaultBoard(3,3, Piece.PieceType.X);
    static Scanner sc = new Scanner(System.in);

    private static void getPlayerMove () {
        System.out.print("Index of move: ");

        int move = sc.nextInt();
        board.moveAt(move,false);
    }
    private static void playMove () {
        if (board.getTurn() != board.getOurtype()) {
            getPlayerMove();
        } else {
            Algorithm.miniMax(board,7);
        }
    }
    private static void printGameStatus () {
        System.out.println("\n" + board + "\n");
        System.out.println(board.getTurn().toString() + "'s turn.");
    }

    private static void pringWinner(){
        Piece.PieceType winner = board.getWinner();
        if(winner == null){System.out.println("Draw");}
        else{
            System.out.println("Player "+ winner.toString()+" wins");
        }
    }

    public static void main(String[] args) {
        System.out.println("starting a new game");
        board.moveAt(4,false);
        while(board.isGameOver()!=true) {
            printGameStatus();
            playMove();
        }
        pringWinner();
    }
}
