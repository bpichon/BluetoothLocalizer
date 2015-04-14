package hm.edu.pichon.bluetoothlocalizer.interfaces;

public class TestConnectionResponse extends Response {

	private boolean success;
	private String message;

    public TestConnectionResponse() {
        this.success = true;
        this.message = "Successfully connected";
    }

    public TestConnectionResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
