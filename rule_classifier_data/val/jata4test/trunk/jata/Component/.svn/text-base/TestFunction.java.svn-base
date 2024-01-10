package jata.Component;

import jata.exception.JataException;

public abstract class TestFunction <Tcomponent extends TestComponent> extends Thread{
	protected  Tcomponent component;
	public abstract void Func(Tcomponent component) throws JataException;
	
	public void run() {
		try {
			Func(component);
		} catch (JataException e) {
			component.Error();
			System.out.println(e.toString());
		}
	}
	public TestFunction(Tcomponent comp) {
		this.component = comp;
	}
}
