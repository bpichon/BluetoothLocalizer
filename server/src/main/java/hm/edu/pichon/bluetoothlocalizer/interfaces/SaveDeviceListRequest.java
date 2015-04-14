package hm.edu.pichon.bluetoothlocalizer.interfaces;

import java.util.ArrayList;


public class SaveDeviceListRequest extends Request {
	public double longitude;
	public double latitude;
	public double accuracy;
	
	public ArrayList<DeviceData> deviceList;
	
	public SaveDeviceListRequest() {
		deviceList = new ArrayList<DeviceData>();
	}

	@Override
	protected String initRequestCode() {
		return "PUT";
	}	
}
