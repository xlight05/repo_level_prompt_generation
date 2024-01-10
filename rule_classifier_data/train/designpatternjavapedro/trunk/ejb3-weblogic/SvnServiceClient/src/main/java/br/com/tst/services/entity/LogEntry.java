package br.com.tst.services.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Pedro Daniel Aoki
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "logentry")
public class LogEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**(non-Javadoc)
	 * @
	 */
	@XmlElement
	private String author;
	/**
	 * 
	 */
	@XmlElement
	private String date;
	/**
	 * 
	 */
	@XmlElement(name = "msg")
	private String message;
	/**
	 * 
	 */
	@XmlElementWrapper(name = "paths")
	@XmlElement(name = "path")
	private List<String> path;

	/**
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return
	 * @
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
	/*asdasda*/
		
		this.message = message;
	}

	/**
	 * @return
	 */
	public List<String> getPath() {
		return path;
	}

	/**
	 * @param path
	 */
	public void setPath(List<String> path) {
		this.path = path;
	}

}
