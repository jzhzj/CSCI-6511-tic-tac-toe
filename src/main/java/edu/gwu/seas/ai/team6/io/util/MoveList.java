package edu.gwu.seas.ai.team6.io.util;

import java.util.ArrayList;
import java.util.List;

/**
 * An object representing a list of {@link Move}s. This class is mainly used by Gson,
 * so there is no public access to the constructor of {@link MoveList} class.
 *
 * @author qijiuzhi
 * @date 2019-03-05
 */
public class MoveList {
    private ArrayList<Move> moves;

    private MoveList() {

    }

    public List<Move> getMoves() {
        return moves;
    }
}