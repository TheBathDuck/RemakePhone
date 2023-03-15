package nl.thebathduck.remakephone.objects.maps;

import lombok.Data;
import nl.thebathduck.remakephone.utils.ChatUtils;

import java.util.HashMap;

@Data
public class NavigationCategory {

    private String name;
    private String displayName;
    private String iconUrl;
    private HashMap<String, NavigationLocation> locations;

    public NavigationCategory(String name, String displayName, String iconUrl) {
        this.name = name;
        this.displayName = ChatUtils.color(displayName);
        this.locations = new HashMap<>();
        this.iconUrl = iconUrl;
    }

    public void setDisplayName(String name) {
        this.displayName = ChatUtils.color(name);
    }

    public void addLocation(NavigationLocation location) {
        locations.put(location.getName(), location);
    }

}
