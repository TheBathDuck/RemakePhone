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

    public void init(String ip, int port, String databaseName, String user, String password) {
        HikariConfig hikariConfig = new HikariConfig();

        try {
            Class.forName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikariConfig.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        } catch (ClassNotFoundException e) {
            hikariConfig.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
        }
        hikariConfig.addDataSourceProperty("serverName", ip);
        hikariConfig.addDataSourceProperty("port", port);
        hikariConfig.addDataSourceProperty("databaseName", databaseName);
        hikariConfig.addDataSourceProperty("user", user);
        hikariConfig.addDataSourceProperty("password", password);
        hikariConfig.setLeakDetectionThreshold(10000L);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2084");
        hikariConfig.setMaxLifetime(480000L);
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
            contactTable.executeUpdate("CREATE TABLE IF NOT EXISTS Contacts(number INTEGER, contactUuid VARCHAR(36), contactNumber INTEGER, PRIMARY KEY(number))");
            contactTable.close();
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


