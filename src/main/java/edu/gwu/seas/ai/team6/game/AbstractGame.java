package edu.gwu.seas.ai.team6.game;

import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;
import edu.gwu.seas.ai.team6.io.Portal;
import edu.gwu.seas.ai.team6.util.ProjProps;

abstract class AbstractGame implements Game {
    final String ourTeamId = ProjProps.TEAM_ID;
    final String opponentId;
    final String gameId;
    final Portal portal;
    final int n;
    final int m;
    final Piece.PieceType ourPieceType;

    AbstractGame(String opponentId, String gameId, Portal portal, int n, int m, Piece.PieceType ourPieceType) {
        this.opponentId = opponentId;
        this.gameId = gameId;
        this.portal = portal;
        this.n = n;
        this.m = m;
        this.ourPieceType = ourPieceType;
    }
}
