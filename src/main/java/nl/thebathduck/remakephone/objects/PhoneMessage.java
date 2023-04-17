package nl.thebathduck.remakephone.objects;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PhoneMessage {

    private @Getter @Setter UUID uuid;
    private @Getter @Setter int sender;
    private @Getter @Setter String message;
    private @Getter @Setter long time;
    private boolean read;

    public PhoneMessage(UUID messageUuid, int sender, String message, boolean read, long time) {
        this.sender = sender;
        this.message = message;
        this.uuid = messageUuid;
        this.time = time;
        this.read = read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean getRead() {
        return read;
    }

    public String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date(time));
    }

}
