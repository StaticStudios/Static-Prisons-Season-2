package net.staticstudios.prisons.reclaim;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class PersistenceManager {

    private static Logger logger = Logger.getLogger("PersistenceManager");

    private static String url;
    private static final Properties properties = new Properties();

    public static void init() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.severe("Could not find PostgreSQL JDBC driver");
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(
                new File(StaticPrisons.getInstance().getDataFolder() + "/persistence.yml")
        );

        url = config.getString("databaseUrl");
        String username = config.getString("databaseUsername");
        String password = config.getString("databasePassword");

        if (url == null) {
            throw new RuntimeException("Database URL not set in persistence.yml - cannot log new package claim");
        }
        if (username == null) {
            throw new RuntimeException("Database username not set in persistence.yml - cannot log new package claim");
        }
        if (password == null) {
            throw new RuntimeException("Database password not set in persistence.yml - cannot log new package claim");
        }

        properties.put("user", username);
        properties.put("password", password);
    }

    public static void saveNewPackageClaim(String playerName, String playerUUID, String packageID) {
        try (Connection connection = DriverManager.getConnection(url, properties)) {
            var statement = connection.prepareStatement("""
                    INSERT INTO customers (player_name, player_uuid, package_id) VALUES (?, ?, ?)
                    """);

            statement.setString(1, playerName);
            statement.setString(2, playerUUID);
            statement.setString(3, packageID);

            statement.execute();

            if (!connection.getAutoCommit()) connection.commit();
        } catch (SQLException e) {
            logger.severe("Could not log new package claim for " + playerName + " (" + playerUUID + ") for package " + packageID);
            logger.severe(e.getMessage());
        }
    }
}
