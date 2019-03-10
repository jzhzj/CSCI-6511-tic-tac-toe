package edu.gwu.seas.ai.team6.io;

import static edu.gwu.seas.ai.team6.util.ProjProps.TEST_FLAG;
import static edu.gwu.seas.ai.team6.util.ProjProps.TEST_STR;

/**
 * @author qijiuzhi
 */
public abstract class AbstractPortal implements Portal {

    public void log(String info) {
        if (TEST_FLAG) {
            System.out.println(TEST_STR + info);
            System.out.println();
            System.out.println();
        }
    }
}
