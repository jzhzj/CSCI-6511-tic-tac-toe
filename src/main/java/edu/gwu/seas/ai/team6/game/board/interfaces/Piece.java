package edu.gwu.seas.ai.team6.game.board.interfaces;

/**
 * An object representing pieces of the game.
 *
 * @author qijiuzhi
 */
public interface Piece {

    /**
     * Enumerates two types of piece, crosses and noughts.
     */
    enum PieceType {
        /**
         * stands for Crosses
         */
        X,

        /**
         * stands for Noughts
         */
        O,

        /**
         * stands for blanks
         */
        Blank
    }

    /**
     * get the {@link Coordinate} object of the piece
     *
     * @return the {@link Coordinate} object of the piece
     */
    Coordinate getCoordinate();

    /**
     * get the type of the piece
     *
     * @return the {@link PieceType}
     */
    PieceType getType();
}
