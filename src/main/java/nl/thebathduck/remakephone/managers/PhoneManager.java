package nl.thebathduck.remakephone.managers;

import lombok.Getter;
import nl.thebathduck.remakephone.enums.PhoneSkin;
import nl.thebathduck.remakephone.objects.Contact;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import nl.thebathduck.remakephone.utils.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PhoneManager {

    private static PhoneManager instance;
    private @Getter HashMap<UUID, Phone> phones;

    public static PhoneManager getInstance() {
        if (instance == null) instance = new PhoneManager();
        return instance;
    }

    public PhoneManager() {
        phones = new HashMap<>();
    }


    public Phone cachePhone(UUID uuid, Phone phone) {
        phones.put(uuid, phone);
        return phone;
    }

    public Phone getPhone(UUID uuid) {
        return phones.get(uuid);
    }

    public Phone getFromDatabase(UUID uuid) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = SQLManager.getInstance().getHikari().getConnection()) {
            String SQL = "SELECT `number`, `credit`, `skin` FROM Phones WHERE uuid=?";
            statement = connection.prepareStatement(SQL);
            statement.setString(1, uuid.toString());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int number = resultSet.getInt("number");
                double credit = resultSet.getDouble("credit");
                String skin = resultSet.getString("skin");
                Phone phone = new Phone(uuid, credit, number, PhoneSkin.valueOf(skin));
                return phone;
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void savePhone(Phone phone) {
        PreparedStatement statement = null;

        try (Connection connection = SQLManager.getInstance().getHikari().getConnection()) {

            String SQL = "UPDATE Phones SET credit=?, skin=? WHERE number=?";
            statement = connection.prepareStatement(SQL);
            statement.setDouble(1, phone.getCredit());
            statement.setString(2, phone.getSkin().toString());
            statement.setInt(3, phone.getNumber());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Phone registerPhoneNumber(UUID owner, int number) {
        PreparedStatement statement = null;
        try (Connection connection = SQLManager.getInstance().getHikari().getConnection()) {
            String SQL = "INSERT INTO Phones (uuid, number, credit, skin) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(SQL);
            statement.setString(1, owner.toString());
            statement.setInt(2, number);
            statement.setDouble(3, 0.00d);
            statement.setString(4, PhoneSkin.DEFAULT.toString());
            statement.executeUpdate();
            statement.close();
            Phone phone = new Phone(owner, 0.00d, number, PhoneSkin.DEFAULT);
            phones.put(owner, phone);
            return phone;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public List<Contact> getContacts(Phone phone) {
//        List<Contact> contacts = new ArrayList<>();
//        PreparedStatement statement = null;
//        ResultSet set = null;
//        SQLManager database = SQLManager.getInstance();
//        try (Connection connection = database.getHikari().getConnection()) {
//            String SQL = "SELECT contactnumber FROM Contacts WHERE number=?";
//            statement = connection.prepareStatement(SQL);
//            statement.setInt(1, phone.getNumber());
//            set = statement.executeQuery();
//            while(set.next()) {
//                phone.addContact(new Contact(UUID.fromString(set.getString("contactUuid")), set.getInt("contactNumber")));
//                Bukkit.broadcastMessage("# " + set.getString("contactUuid") + ": " + set.getInt("contactNumber"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return contacts;
//        }
//        return contacts;
//    }
//
//    public void addContact(Phone owner, UUID uuid, int number) {
//        if(numberExists(number)) {
//            PreparedStatement statement = null;
//            SQLManager database = SQLManager.getInstance();
//            try (Connection connection = database.getHikari().getConnection()) {
//                String SQL = "INSERT INTO Contacts(number, contactUuid, contactNumber) VALUES(?, ?, ?)";
//                statement = connection.prepareStatement(SQL);
//                statement.setInt(1, owner.getNumber());
//                statement.setString(2, uuid.toString());
//                statement.setInt(3, number);
//                statement.executeUpdate();
//                Bukkit.getLogger().info("Saved new contact for 06-" + owner.getNumber() + " with number: 06-" + number);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Bukkit.getLogger().severe("[PhoneContacts] Couldn't find " + number + " in the database");
//        }
//    }

    public boolean isInDatabase(UUID uuid) {
        PreparedStatement statement = null;
        ResultSet set = null;
        boolean isIn = false;
        SQLManager database = SQLManager.getInstance();
        try (Connection connection = database.getHikari().getConnection()) {
            String SQL = "SELECT uuid FROM Phones WHERE UUID=?";
            statement = connection.prepareStatement(SQL);
            statement.setString(1, uuid.toString());
            set = statement.executeQuery();
            isIn = set.next();
            statement.close();
            set.close();
            return isIn;
        } catch (SQLException e) {
            e.printStackTrace();
            return isIn;
        }
    }

    public boolean numberExists(int number) {
        PreparedStatement statement = null;
        ResultSet set = null;
        boolean isIn = false;
        SQLManager database = SQLManager.getInstance();
        try (Connection connection = database.getHikari().getConnection()) {
            String SQL = "SELECT number FROM Phones WHERE number=?";
            statement = connection.prepareStatement(SQL);
            statement.setInt(1, number);
            set = statement.executeQuery();
            isIn = set.next();
            statement.close();
            set.close();
            return isIn;
        } catch (SQLException e) {
            e.printStackTrace();
            return isIn;
        }
    }

    public int getRandomNumber() {
        Random random = new Random();
        int digits = 8;
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        return random.nextInt(max - min + 1) + min;
    }

    public void loadItem(Player player, Phone phone) {
        PhoneSkin skin = phone.getSkin();

        ItemBuilder phoneItem = new ItemBuilder(Material.BLAZE_POWDER);
        phoneItem.setColoredName("&b" + skin.getName());
        phoneItem.addLoreLine("&3Nummer: &b06-" + phone.getNumber());
        phoneItem.setNBT("rmtphoneitem", "true");
        phoneItem.setNBT("mtwcustom", skin.getNbt());
        phoneItem.setItemFlags();
        player.getInventory().setItem(8, phoneItem.build());
    }

    public void setunloadItem(Player player) {
        player.getInventory().setItem(8, new ItemBuilder(Material.BLAZE_POWDER)
                .setColoredName("&cTelefoon aan het inladen")
                .setNBT("rmtphoneitem", "true")
                .addLoreLine("")
                .addLoreLine("&cBlijft dit item in je inventory?")
                .addLoreLine("&copen een ticket en tag een developer!")
                .addLoreLine("")
                .setItemFlags()
                .build()
        );
    }

}
