package edu.gwu.seas.ai.team6.util;

import java.util.ResourceBundle;

public class ProjProps {
    static {
        ResourceBundle rb = ResourceBundle.getBundle("prop");

        SERVER_ADDR = rb.getString("SERVER_ADDR");
        X_API_KEY = rb.getString("X_API_KEY");
        USER_ID = rb.getString("USER_ID");
        CONTENT_TYPE = rb.getString("CONTENT_TYPE");
        BODY_TYPE_TEAM = rb.getString("BODY_TYPE_TEAM");
        BODY_TYPE_MEMBER = rb.getString("BODY_TYPE_MEMBER");
        BODY_TYPE_GAME = rb.getString("BODY_TYPE_GAME");
        BODY_TYPE_MOVE = rb.getString("BODY_TYPE_MOVE");
        BODY_GAME_TYPE_TTT = rb.getString("BODY_GAME_TYPE_TTT");
        PARAMS_TYPE_TEAM = rb.getString("PARAMS_TYPE_TEAM");
        PARAMS_TYPE_MOVES = rb.getString("PARAMS_TYPE_MOVES");
        PARAMS_TYPE_BOARD_STRING = rb.getString("PARAMS_TYPE_BOARD_STRING");
        PARAMS_TYPE_BOARD_MAP = rb.getString("PARAMS_TYPE_BOARD_MAP");
        CODE_OK = rb.getString("CODE_OK");
        CODE_FAIL = rb.getString("CODE_FAIL");
        TEAM_ID = rb.getString("TEAM_ID");
    }

    public static final String SERVER_ADDR;
    public static final String X_API_KEY;
    public static final String USER_ID;
    public static final String CONTENT_TYPE;
    public static final String BODY_TYPE_TEAM;
    public static final String BODY_TYPE_MEMBER;
    public static final String BODY_TYPE_GAME;
    public static final String BODY_TYPE_MOVE;
    public static final String BODY_GAME_TYPE_TTT;
    public static final String PARAMS_TYPE_TEAM;
    public static final String PARAMS_TYPE_MOVES;
    public static final String PARAMS_TYPE_BOARD_STRING;
    public static final String PARAMS_TYPE_BOARD_MAP;
    public static final String CODE_OK;
    public static final String CODE_FAIL;
    public static final String TEAM_ID;
}
