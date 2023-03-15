package nl.thebathduck.remakephone.objects.maps;

import lombok.Data;
import nl.thebathduck.remakephone.utils.ChatUtils;

@Data
public class NavigationLocation {

    private String name;
    private String iconUrl;
    private String displayName;
    private String gpsValue;

    public NavigationLocation(String name, String gpsValue, String displayName, String iconUrl) {
        this.name = name;
        this.gpsValue = gpsValue;
        this.displayName = ChatUtils.color(displayName);
        this.iconUrl = iconUrl;
    }

}
