package hm.edu.pichon.bluetoothlocalizer;

public class DeviceInfo {

	public String id;
	public double latitude;
	public double longitude;
	public double accuracy;
	
	public DeviceInfo(String id, double lat, double lon, double acc) {
		this.id = id;
		this.latitude = lat;
		this.longitude = lon;
		this.accuracy = acc;
	}
}
