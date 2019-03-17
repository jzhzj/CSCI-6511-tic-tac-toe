package edu.gwu.seas.ai.team6.game.alg;

import edu.gwu.seas.ai.team6.game.board.DefaultBoard;

public class Algorithm {
    private Algorithm(){};

    public static void miniMax(DefaultBoard board,int MaxDepth){
        MiniMax.run(board,MaxDepth);
    }
}

