package org.pexamax.acheron;

import java.util.Arrays;
import java.util.ArrayList;

class Preferences {
    private static int lastPreferencesID = 0;

    private int preferenceID;
    private int userID;
    private ArrayList<String> blockList;
    private boolean selfDestruct;
    private int defaultDestructTimer;

    public Preferences(int userID, String blockList, boolean selfDestruct, int defaultDestructTimer) {
        preferenceID = lastPreferencesID++;
        this.blockList = new ArrayList<String>(Arrays.asList(blockList.split(",")));
        this.selfDestruct = selfDestruct;
        this.defaultDestructTimer = defaultDestructTimer;
    }
}
