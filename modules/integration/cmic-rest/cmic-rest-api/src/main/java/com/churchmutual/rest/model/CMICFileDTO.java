package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICFileDTO extends CMICObjectDTO {

	public String getBytes() {
		return _bytes;
	}

	public String getId() {
		return _id;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public String getName() {
		return _name;
	}

	public String getUrl() {
		return _url;
	}

	public void setBytes(String bytes) {
		_bytes = bytes;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setMimeType(String mimeType) {
		_mimeType = mimeType;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private String _bytes;
	private String _id;
	private String _mimeType;
	private String _name;
	private String _url;

}