package co.edu.unbosque.tiendagenerica.utils;

public class ResponseMessagesCSV {

	private String message;
	//private String fileDownloadUri;
	
		// I added the void but it was not here...
	  public ResponseMessagesCSV(String message) {
	    this.message = message;
	  }
	  
	  /*
	   	public ResponseMessageCSV(String message, String fileDownloadUri) {
	    this.message = message;
	    this.fileDownloadUri = fileDownloadUri;
	  }
	   
	   */

	  public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }
	  
	/*  
	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}
	*/

}