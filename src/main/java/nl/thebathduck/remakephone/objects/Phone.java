package nl.thebathduck.remakephone.objects;

import lombok.Getter;
import lombok.Setter;
import nl.thebathduck.remakephone.enums.PhoneSkin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Phone {

    private @Getter UUID owner;
    private @Getter int number;
    private @Getter
    @Setter PhoneSkin skin;
    private @Getter
    @Setter double credit;
    private @Getter HashMap<Integer, Contact> contacts;
    private @Getter HashMap<UUID, PhoneMessage> messages;

    public Phone(UUID owner, double credit, int number, PhoneSkin skin) {
        this.skin = skin;
        this.owner = owner;
        this.credit = credit;
        this.number = number;
        this.contacts = new HashMap<>();
        this.messages = new HashMap<>();
    }

    public void addCredit(double credit) {
        this.credit = credit + this.credit;
    }

    public void addContact(Contact contact) {
        contacts.put(contact.getNumber(), contact);
    }
    public Contact getContact(int number) {
        return contacts.get(number);
    }
    public void removeContact(int number) {
        this.contacts.remove(number);
    }

    public void addMessage(PhoneMessage message) {
        this.messages.put(message.getUuid(), message);
    }
    public void removeMessage(UUID uuid) {
        this.messages.remove(uuid);
    }
    public PhoneMessage getMessage(UUID uuid) {
        return this.messages.get(uuid);
    }


}
