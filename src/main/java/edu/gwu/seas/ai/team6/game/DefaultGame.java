package edu.gwu.seas.ai.team6.game;

import edu.gwu.seas.ai.team6.game.alg.Algorithm;
import edu.gwu.seas.ai.team6.game.board.DefaultBoard;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;
import edu.gwu.seas.ai.team6.io.Portal;
import edu.gwu.seas.ai.team6.io.util.BoardInfo;
import edu.gwu.seas.ai.team6.io.util.Move;

/**
 * Default implementation of {@link Game}.
 *
 * @author qijiuzhi
 * @date 2019-03-05
 */
public class DefaultGame extends AbstractGame {

    /**
     * make constructor private so that {@link DefaultGame} object can only be constructed by createGame method
     */
    private DefaultGame(String opponentId, String gameId, Portal portal, int n, int m, Piece.PieceType ourPieceType) {
        super(opponentId, gameId, portal, n, m, ourPieceType);
    }

    /**
     * Creates a new game if we are the initializer of the game.
     *
     * @param opponentId   the id of the opponent team
     * @param portal       the portal used for communication
     * @param n            the size of the board
     * @param m            the goal
     * @param ourPieceType the type of our piece
     * @return the new game or null if failed to create a game
     */
    public static DefaultGame createGame(String opponentId, Portal portal, int n, int m, Piece.PieceType ourPieceType) {
        String gameId = portal.createGame(opponentId);
        return createGame(opponentId, portal, n, m, ourPieceType, gameId);
    }

    /**
     * Creates a new game if other team has already post a game to the game server.
     *
     * @param opponentId   the id of the opponent team
     * @param portal       the portal used for communication
     * @param n            the size of the board
     * @param m            the goal
     * @param ourPieceType the type of our piece
     * @param gameId       the id of the game
     * @return the new game or null if failed to create a game
     */
    public static DefaultGame createGame(String opponentId, Portal portal, int n, int m, Piece.PieceType ourPieceType, String gameId) {
        if (gameId == null) {
            return null;
        }
        return new DefaultGame(opponentId, gameId, portal, n, m, ourPieceType);
    }

    @Override
    public void run() {
        // create a blank board;
        DefaultBoard board = new DefaultBoard(n, m, ourPieceType);
        //fill the board with server boardinfo
        BoardInfo boardString = portal.getBoardString(gameId);
        String info = boardString.getOutput().replace("\n", "");
        for (int i = 0; i < n * n; i++) {
            String type = info.substring(i);
            if (!type.equals("Blank")) {
                Piece.PieceType type1 = Piece.PieceType.O;
                if (type.equals("X")) {
                    type1 = Piece.PieceType.X;
                }
                boolean curType = type1 == ourPieceType;
                board.moveAt(i, curType);
            }
        }
        while (!board.isGameOver()) {
            Move lastMove = portal.getLastMove(gameId);
            while (lastMove == null || lastMove.getPieceType() == ourPieceType) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lastMove = portal.getLastMove(gameId);
            }
            board.moveAt(lastMove.getCoordinate(), false);
            Algorithm.alphaBetaPruningAdvanced(board, 5);
            //records our AI's move, send it to server with portal
            //return AI move to server
            portal.moveAt(board.getLastPiece().getCoordinate(), gameId);
        }
    }

}
