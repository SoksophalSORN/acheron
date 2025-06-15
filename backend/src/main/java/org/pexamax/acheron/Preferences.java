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
        // preferenceID = lastPreferencesID++;
        // this.blockList = new ArrayList<String>(Arrays.asList(blockList.split(",")));
        // this.selfDestruct = selfDestruct;
        // this.defaultDestructTimer = defaultDestructTimer;
        this.preferenceID(lastPreferencesID++);
        this.blockList(new ArrayList<String>(Arrays.asList(blockList.split(","))));
        this.selfDestruct(selfDestruct);
        this.defaultDestructTimer(defaultDestructTimer);
    }

    public int preferenceID() { return this.preferenceID; }
    public void preferenceID(int preferenceID) { this.preferenceID = preferenceID; }

    public int userID() { return this.userID; }
    public void userID(int userID) { this.userID = userID; }

    public ArrayList<String> blockList() { return this.blockList; }
    public void blockList(ArrayList<String> blockList) { this.blockList = blockList; }

    public boolean selfDestruct() { return this.selfDestruct; }
    public void selfDestruct(boolean selfDestruct) { this.selfDestruct = selfDestruct; }

    public int defaultDestructTimer() { return this.defaultDestructTimer; }
    public void defaultDestructTimer(int defaultDestructTimer) { this.defaultDestructTimer = defaultDestructTimer; }


}
