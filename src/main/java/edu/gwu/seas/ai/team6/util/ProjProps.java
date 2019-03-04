package edu.gwu.seas.ai.team6.util;

import java.util.ResourceBundle;

public class ProjProps {
    static {
        ResourceBundle rb = ResourceBundle.getBundle("prop");

        SERVER_ADDR = rb.getString("SERVER_ADDR");
    }

    public static final String SERVER_ADDR;
}
