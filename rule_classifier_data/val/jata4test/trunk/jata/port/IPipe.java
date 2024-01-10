package jata.port;

import jata.message.*;
import jata.exception.JataPortException;

public interface IPipe {
	void pipe2Me(Message m) throws JataPortException;
	void UnMap();
}
