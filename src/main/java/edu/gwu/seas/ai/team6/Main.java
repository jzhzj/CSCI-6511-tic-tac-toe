package edu.gwu.seas.ai.team6;

import edu.gwu.seas.ai.team6.game.DefaultGame;
import edu.gwu.seas.ai.team6.game.alg.Algorithm;
import edu.gwu.seas.ai.team6.game.board.DefaultBoard;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

import java.util.Scanner;

public class Main {
    static DefaultBoard board = new DefaultBoard(3,3, Piece.PieceType.X);
    static Scanner sc = new Scanner(System.in);

    private static void getPlayerMove () { // get opponent move, use Portal to get the coordinate
//        System.out.print("Index of move: ");
//
//        int move = sc.nextInt();
//        board.moveAt(move,false);
        System.out.println("move X and Y: ");
        int x = sc.nextInt();
        int y = sc.nextInt();
        board.moveAt(x,y,false);
    }
    private static void playMove () {
        if (board.getTurn() != board.getOurtype()) {
            getPlayerMove();        //get opponent move
        } else {
            Algorithm.alphaBetaPruning(board,7);
            //board.getLastPiece().getCoordinate().getX(),board.getLastPiece().getCoordinate().getY()
            //records our AI's move, send it to server with portal
        }
    }
    private static void printGameStatus () {
        System.out.println("\n" + board + "\n");
        System.out.println(board.getTurn().toString() + "'s turn.");
    }

    private static void printWinner(){
        Piece.PieceType winner = board.getWinner();
        if(winner == null){System.out.println("Draw");}
        else{
            System.out.println("Player "+ winner.toString()+" wins");
        }
    }

    public static void main(String[] args) {
        System.out.println("starting a new game/n");
        System.out.println("(O) opponent begins in :/n");
        System.out.println("move X and Y: ");
        int x = sc.nextInt();
        int y = sc.nextInt();
        board.moveAt(x,y,false);//true: start with us; false: start with opponent
        while(board.isGameOver()!=true) {
            printGameStatus();
            playMove();// place piece on board
        }
        printWinner();
    }
}
