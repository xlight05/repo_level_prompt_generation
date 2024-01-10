package br.com.pedro.carros.cliente;

import br.com.pedro.carros.componentes.Farol;
import br.com.pedro.carros.componentes.Motor;
import br.com.pedro.carros.componentes.TocaCD;

public class Test {
	
		/**
		 * Complexidade aumenta acada nova implementacao do subsistema
		 * 
		 * 
		 */
	
		public static void main(String[] args){
			Farol farol =new Farol();
			Motor motor=new Motor();
			TocaCD tocacd=new TocaCD();
			farol.acendeFarol();
			motor.ligaMotor();
			tocacd.ligaTocaCD();
			motor.geraCombustao();
			farol.aumentaRange();
			tocacd.aumentaVolume();
			tocacd.trocaCD();
			
			
		}

}
