package br.com.tst.services.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Pedro Daniel Aoki
 *
 */
@XmlRootElement(name = "log")
@XmlAccessorType(XmlAccessType.FIELD)
public class SvnLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@XmlElement(name = "logentry")
	private List<LogEntry> entries;

	/**
	 * @return
	 */
	public List<LogEntry> getEntries() {
		return entries;
	}

	/**
	 * @param entries
	 */
	public void setEntries(List<LogEntry> entries) {
		this.entries = entries;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String tostring = "";
		for (LogEntry logentry : this.getEntries()) {
			tostring = tostring + "Nome:" + logentry.getAuthor() + ",";
			tostring = tostring + "Message:" + logentry.getMessage() + ",";
			tostring = tostring + "Date:" + logentry.getDate() + ",";
			tostring = tostring + "Paths [";

			for (String path : logentry.getPath()) {
				tostring = tostring + path + ",";

			}
			tostring = tostring + "]";

		}

		return tostring;
	}

}
