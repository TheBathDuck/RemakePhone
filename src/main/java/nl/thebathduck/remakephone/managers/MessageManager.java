package nl.thebathduck.remakephone.managers;

import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.objects.PhoneMessage;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MessageManager {
    private static MessageManager instance;

    public void addMessage(UUID uuid, int target, int sender, String message) {
        Player onlinePlayer = Bukkit.getPlayer(uuid);
        UUID messageUuid = UUID.randomUUID();
        long time = System.currentTimeMillis();
        if(onlinePlayer != null) {
            Phone onlinePhone = PhoneManager.getInstance().getPhone(onlinePlayer.getUniqueId());
            PhoneMessage phoneMessage = new PhoneMessage(messageUuid, sender, message, false, time);

            onlinePhone.addMessage(phoneMessage);
            onlinePlayer.sendMessage(ChatUtils.color("&3Je hebt een nieuw bericht ontvangen &b06-" + sender + "&3."));
        }

        Bukkit.getScheduler().runTaskAsynchronously(RemakePhone.getInstance(), () -> {
            try (Connection connection = SQLManager.getInstance().getHikari().getConnection()) {
                PreparedStatement statement = null;
                try {
                    String SQL = "INSERT INTO Messages(uuid, number, sender, message, hasread, time) VALUES(?, ?, ?, ?, ?, ?)";
                    statement = connection.prepareStatement(SQL);
                    statement.setString(1, messageUuid.toString());
                    statement.setInt(2, target);
                    statement.setInt(3, sender);
                    statement.setString(4, message);
                    statement.setBoolean(5, false);
                    statement.setLong(6, time);
                    statement.executeUpdate();
                } finally {
                    statement.close();
                    statement.getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadMessages(Phone phone) {
        Bukkit.getScheduler().runTaskAsynchronously(RemakePhone.getInstance(), () -> {
            try (Connection connection = SQLManager.getInstance().getHikari().getConnection()) {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    String SQL = "SELECT * FROM Messages WHERE number=?";
                    statement = connection.prepareStatement(SQL);
                    statement.setInt(1, phone.getNumber());
                    resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        UUID messageUuid = UUID.fromString(resultSet.getString("uuid"));
                        int sender = resultSet.getInt("sender");
                        String message = resultSet.getString("message");
                        boolean hasread = resultSet.getBoolean("hasread");
                        long time = resultSet.getLong("time");
                        PhoneMessage phoneMessage = new PhoneMessage(messageUuid, sender, message, hasread, time);
                        phone.addMessage(phoneMessage);
                        Bukkit.getLogger().info("[MessageLoader] Time: " + phoneMessage.getDate() + " Loaded: " + messageUuid);
                    }

                } finally {
                    resultSet.close();
                    statement.close();
                    statement.getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void setRead(UUID messageUuid, boolean read) {
        Bukkit.getScheduler().runTaskAsynchronously(RemakePhone.getInstance(), () -> {
            try (Connection connection = SQLManager.getInstance().getHikari().getConnection()) {
                PreparedStatement statement = null;
                try {
                    String SQL = "UPDATE Messages SET hasread=? WHERE uuid=?";
                    statement = connection.prepareStatement(SQL);
                    statement.setBoolean(1, read);
                    statement.setString(2, messageUuid.toString());
                    statement.executeUpdate();
                } finally {
                    statement.close();
                    statement.getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


    public static MessageManager getInstance() {
        if(instance == null) instance = new MessageManager();
        return instance;
    }

}
