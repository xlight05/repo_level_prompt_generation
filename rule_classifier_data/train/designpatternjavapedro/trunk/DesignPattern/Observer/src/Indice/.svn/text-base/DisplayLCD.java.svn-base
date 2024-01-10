package Indice;

public class DisplayLCD implements ObserverInterface{
	
	
	public DisplayLCD(SubjectInterface subject) {
		super();
		subject.addObserver(this);
	}

	

	@Override
	public void update(Double cotacao_dolar, Double cotacao_real) {
		System.out.println("nova cotação do dolar eh :"+cotacao_dolar+" , nova cotacao do real eh :"+cotacao_real);
		
	}

}
