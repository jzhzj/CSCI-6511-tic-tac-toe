package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;

abstract class AbstractCoordinate implements Coordinate {
    int x;
    int y;

    AbstractCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
