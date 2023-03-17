package nl.thebathduck.remakephone.managers;

public class MarktManager {

    private static MarktManager instance;

    public void init() {

    }


    public static MarktManager getInstance() {
        if (instance == null) instance = new MarktManager();
        return instance;
    }

}
