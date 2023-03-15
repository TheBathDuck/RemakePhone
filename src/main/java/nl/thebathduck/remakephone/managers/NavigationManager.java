package nl.thebathduck.remakephone.managers;

import lombok.Getter;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.objects.maps.NavigationCategory;
import nl.thebathduck.remakephone.objects.maps.NavigationLocation;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class NavigationManager {

    private static NavigationManager instance;
    public static NavigationManager getInstance() {
        if(instance == null) instance = new NavigationManager();
        return instance;
    }


    private @Getter HashMap<String, NavigationCategory> categories;
    private HashMap<String, NavigationLocation> allLocations;

    public NavigationManager() {
        categories = new HashMap<>();
        allLocations = new HashMap<>();
    }

    public NavigationLocation getNavigationLocation(String name) {
        return allLocations.get(name);
    }

    public void initialize() {
        FileConfiguration config = RemakePhone.getInstance().getConfig();

        for(String catString : config.getConfigurationSection("categorieen").getKeys(false)) {
            String displayName = config.getString("categorieen." + catString + ".displayname");
            String iconUrl = config.getString("categorieen." + catString + ".icon");
            NavigationCategory navCategory = new NavigationCategory(catString, displayName, iconUrl);
            categories.put(navCategory.getName(), navCategory);

            for(String locString : config.getConfigurationSection("categorieen." + catString + ".locaties").getKeys(false)) {
                String displayname = config.getString("categorieen." + catString + ".locaties." + locString + ".displayname");
                String gpsvalue = config.getString("categorieen." + catString + ".locaties." + locString + ".gpsvalue");
                String navIconUrl = config.getString("categorieen." + catString + ".locaties." + locString + ".icon");
                NavigationLocation navLocation = new NavigationLocation(locString, gpsvalue, displayname, navIconUrl);
                navCategory.addLocation(navLocation);
                allLocations.put(navLocation.getName(), navLocation);
            }

        }
    }



}
