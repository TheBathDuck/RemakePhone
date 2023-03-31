package nl.thebathduck.remakephone.objects;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PhoneMessage {

    private @Getter UUID uuid;
    private @Getter int sender;
    private @Getter String message;
    private @Getter long time;
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
