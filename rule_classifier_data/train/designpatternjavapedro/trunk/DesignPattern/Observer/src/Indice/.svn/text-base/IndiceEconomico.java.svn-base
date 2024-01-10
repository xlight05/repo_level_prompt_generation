package Indice;

import java.util.ArrayList;
import java.util.List;

public class IndiceEconomico implements SubjectInterface{
		private List<ObserverInterface> list=new ArrayList<ObserverInterface>();
		private Double CotacaoDolar;
		private Double CotacaodoReal;
		
		public void addObserver(ObserverInterface obs){
			list.add(obs);
		}
		
		public void measurementUpdate(Double d1,Double d2){
			this.CotacaoDolar=d1;
			this.CotacaodoReal=d2;
			for (ObserverInterface obs : list) {
				obs.update(d1, d2);
			}
		}
		
		public Double getCotacaoDolar() {
			return CotacaoDolar;
		}
		public void setCotacaoDolar(Double cotacaoDolar) {
			CotacaoDolar = cotacaoDolar;
		}
		public Double getCotacaodoReal() {
			return CotacaodoReal;
		}
		public void setCotacaodoReal(Double cotacaodoReal) {
			CotacaodoReal = cotacaodoReal;
		}
		
	


}
