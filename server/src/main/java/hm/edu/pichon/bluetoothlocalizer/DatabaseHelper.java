package hm.edu.pichon.bluetoothlocalizer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper {

	private DatabaseHelper dbHelper;
	private Connection c;
	
	public DatabaseHelper getInstance() {
		if (dbHelper == null) {
			try {
				dbHelper = new DatabaseHelper();
			} catch (SQLException e) {
				return null; // Wenn Connector nicht init werden kann return null.
			}
		}
		return dbHelper;
	}
	
	private DatabaseHelper() throws SQLException {
		c = DriverManager.getConnection("jdbc:mysql://localhost:3306/soccer", "root", "");
		
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
