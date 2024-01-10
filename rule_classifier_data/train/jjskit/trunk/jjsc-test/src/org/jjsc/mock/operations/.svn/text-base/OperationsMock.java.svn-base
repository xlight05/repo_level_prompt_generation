package org.jjsc.mock.operations;

public class OperationsMock {
	private static int internal;
	
	public static int performOperations(int i){
		int x = i+1;
		int z = i*x;
		z++;
		int time = (int) System.currentTimeMillis();
		internal--;
		internal -= 10;
		internal += 5;
		z = internal/time;
		return z;
	}
	
	public double performUnaryOperations(double a,double b, int c){
		a++;
		a+=5;
		++a;
		b--;
		b-=1;
		c/=5;
		c*=2;
		c &= 5;
		c |= 0;
		c ^= 10;
		b = a++ + c++;
		return a+b+c;
	}
	
	public int performByteOperations(byte a, byte b, byte c){
		int x = a^b;
		int y = a|c;
		int z = b&c;
		int t = b<<c;
		int s = b>>c;
		int m = b>>>c;
		return (x|y)&z|t&s|m;
	}
	
	public boolean performBooleanOperations(
			boolean a, boolean b, boolean c){
		a = a&&b;
		b = (c||a)&&b;
		if(a||b||c||System.out!=null||internal>0){
			return a&&!b||c;
		}
		return true;
	}
}
