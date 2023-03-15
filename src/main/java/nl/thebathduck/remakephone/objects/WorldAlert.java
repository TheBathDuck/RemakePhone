package nl.thebathduck.remakephone.objects;

import lombok.Getter;
import nl.thebathduck.remakephone.utils.ChatUtils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class WorldAlert {

    private @Getter String alert;
    private @Getter String author;
    private @Getter String date;

    public WorldAlert(String alert, String author, long time) {
        this.alert = ChatUtils.color(alert);
        this.author = author;
        this.date = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date(time));
    }

    public void editMessage(String alert) {
        this.alert = alert;
    }

}
