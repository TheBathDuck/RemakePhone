package nl.thebathduck.remakephone.objects;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.thebathduck.remakephone.enums.PlotState;

import java.util.UUID;

public class Plot {

    private ProtectedRegion region;
    private PlotState state;
    private int price;
    private UUID owner;

    public Plot(ProtectedRegion region, PlotState state, int price, UUID owner) {
        this.region = region;
        this.state = state;
        this.price = price;
        this.owner = owner;
    }


}
