package nl.thebathduck.remakephone.objects;

import lombok.Getter;

import java.util.UUID;

public class Contact {

    private @Getter UUID owner;
    private @Getter int number;


    public Contact(UUID uuid, int number) {
        this.owner = uuid;
        this.number = number;

    }

}
