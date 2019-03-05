package edu.gwu.seas.ai.team6.game.board.interfaces;

public interface Piece {
    enum PieceType {
        /**
         * stands for Crosses
         */
        X,

        /**
         * stands for Noughts
         */
        O
    }

    /**
     * get the coordinate of the piece
     *
     * @return the coordinate of the piece
     */
    Coordinate getCoordinate();

    /**
     * get the type of the piece
     *
     * @return the type of the piece
     */
    PieceType getType();
}
