package jata.example.tiny;

import jata.alt.*;
import jata.exception.*;
import jata.message.*;
import jata.port.PipeCenter;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			CheckCode mc = new CheckCode();
			mc.decode((Object)"sfeE");
			
			Sys sys = new Sys();
			Ads ads = new Ads();
			
			PipeCenter.Map(ads, sys);
			
			ads.send(mc);

			Alt alt = new Alt();
			Timer t = new Timer();
			t.start();
			
			alt.addBranch(ads, new Package());
			alt.addBranch(t, new SetTimeMessage(7000));
			alt.proc();
			
			AltResult r = alt.getResult();
			
			System.out.println("-------"+r.index);
			r = null;
		} catch (JataException e) {
			System.out.println( e.toString());
		}
	}

}
