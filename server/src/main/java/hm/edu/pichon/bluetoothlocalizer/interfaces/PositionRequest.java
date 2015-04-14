package hm.edu.pichon.bluetoothlocalizer.interfaces;

public class PositionRequest extends Request{

	@Override
	protected String initRequestCode() {
		return "GET";
	}

}
