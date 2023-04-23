package nl.thebathduck.remakephone.utils.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.*;

public class SQLManager {

    private static SQLManager instance;

    public static SQLManager getInstance() {
        if (instance == null) instance = new SQLManager();
        return instance;
    }

    private @Getter HikariDataSource hikari;

    public void init(String ip, int port, String databaseName, String user, String password, String dataSource) {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.addDataSourceProperty("serverName", ip);
        hikariConfig.addDataSourceProperty("port", port);
        hikariConfig.addDataSourceProperty("databaseName", databaseName);
        hikariConfig.addDataSourceProperty("user", user);
        hikariConfig.addDataSourceProperty("password", password);
        hikariConfig.setDataSourceClassName(dataSource);
        hikariConfig.setLeakDetectionThreshold(10000L);
        hikariConfig.setMaximumPoolSize(8);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.setMaxLifetime(120000L); // Was 480000L, now: 120000L
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("RemakePhone");
        hikari = new HikariDataSource(hikariConfig);

        Bukkit.getLogger().info("Connected to RemakePhone Database.");
    }

    public void createTables() {
        try (Connection connection = hikari.getConnection()) {
            Statement phonesTable = connection.createStatement();
            phonesTable.executeUpdate("CREATE TABLE IF NOT EXISTS Phones(uuid VARCHAR(36), number INTEGER, credit DOUBLE, skin VARCHAR(128), PRIMARY KEY(uuid))");
            phonesTable.close();

            Statement contactTable = connection.createStatement();
            contactTable.executeUpdate("CREATE TABLE IF NOT EXISTS Contacts(id INT AUTO_INCREMENT, number INTEGER, contactUuid VARCHAR(36), contactNumber INTEGER, PRIMARY KEY (id))");
            contactTable.close();

            Statement messageTable = connection.createStatement();
            messageTable.executeUpdate("CREATE TABLE IF NOT EXISTS Messages(uuid VARCHAR(36), number INTEGER, sender INTEGER, message TEXT, hasread TINYINT, time BIGINT, PRIMARY KEY (uuid))");
            messageTable.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void shutdown() throws SQLException {
        if (hikari.getConnection() != null) {
            hikari.close();
        }
    }

    public void closeStatement(PreparedStatement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeResultSet(ResultSet set) {
        try {
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


