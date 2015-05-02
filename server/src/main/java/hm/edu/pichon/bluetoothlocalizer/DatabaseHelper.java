package hm.edu.pichon.bluetoothlocalizer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper {

	private static DatabaseHelper dbHelper;
	private Connection c;

    public void init() throws SQLException {
        final Statement s = c.createStatement();
        String dropQuery = "DROP TABLE IF EXISTS COLLECTED_DATA";
        s.executeUpdate(dropQuery);

        String createQuery = "CREATE TABLE IF NOT EXISTS COLLECTED_DATA " +
                "(ID            INT     NOT NULL    PRIMARY KEY," +
                " device_id     TEXT    NOT NULL, " +
                " time          INT     NOT NULL, " +
                " longitude     INT, " +
                " latitude      INT, " +
                " accuracy      INT)";
        s.executeUpdate(createQuery);
    }
	
	public static DatabaseHelper getInstance() {
		if (dbHelper == null) {
			try {
				dbHelper = new DatabaseHelper();
			} catch (SQLException e) {
				e.printStackTrace();
                return null; // Wenn Connector nicht init werden kann return null.
			}
		}
		return dbHelper;
	}
	
	private DatabaseHelper() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
		c = DriverManager.getConnection("jdbc:sqlite:test.db");
        init();
	}

    public void insertDevice(String deviceId, int time) {
        String query = "INSERT INTO `COLLECTED_DATA` (`id`, `device_id`, `time`, `longitude`, `latitude`, `accuracy`) VALUES (0, '" + deviceId + "', " + time + ", 0, 0, 0);";
        try {
            final Statement s = c.createStatement();
            s.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Debugging Method. Prints all devices
     */
    public void printAll() {
        try {
            final Statement s = c.createStatement();
            ResultSet resultSet;
            resultSet = s.executeQuery(""
                    + "SELECT * "
                    + "FROM `COLLECTED_DATA`");

            while (resultSet.next()) {
                System.out.printf(
                        "DevId: '%s' | Long: %s Lat: %s | Time: %s | Acc: %s\n",
                        resultSet.getString("device_id"),
                        resultSet.getDouble("longitude"),
                        resultSet.getDouble("latitude"),
                        resultSet.getDouble("time"),
                        resultSet.getDouble("accuracy"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public ArrayList<DeviceInfo> getAllDevices(List<String> deviceIds, int time) {
		try {
			final Statement s = c.createStatement();
			ResultSet resultSet;
			resultSet = s.executeQuery(""
					+ "SELECT * "
					+ "FROM `COLLECTED_DATA` "
					+ "WHERE `device_id` IN (" + implode(", ", deviceIds) + ")"); // TODO: time
		
			ArrayList<DeviceInfo> deviceInfoList = new ArrayList<>();
			while (resultSet.next()) {
				deviceInfoList.add(new DeviceInfo(resultSet.getString("device_id"), resultSet.getDouble("latitude"), resultSet.getDouble("longitude"), resultSet.getDouble("accuracy")));
			}
			return deviceInfoList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String implode(String separator, List<String> list) {
		StringBuilder b = new StringBuilder();
		
		for (int i = 0; i < list.size(); i++) {
			b.append(list.get(i));
			if (i < list.size() - 1) {
				b.append(separator);
			}
		}
		return b.toString();
		
	}
}
