package edu.gwu.seas.ai.team6.io.util;

/**
 * An object representing the board info message returned from the game server. This class is mainly used by Gson,
 * so there is no public access to the constructor of {@link BoardInfo} class.
 *
 * @author qijiuzhi
 * @date 2019-03-07
 */
public class BoardInfo {
    private String output;
    private String code;

    private BoardInfo() {
    }

    public String getOutput() {
        return output;
    }

    public String getCode() {
        return code;
    }
}
