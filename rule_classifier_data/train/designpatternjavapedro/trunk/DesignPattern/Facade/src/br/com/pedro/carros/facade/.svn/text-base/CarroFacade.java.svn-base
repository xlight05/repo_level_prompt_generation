package br.com.pedro.carros.facade;

import br.com.pedro.carros.componentes.Farol;
import br.com.pedro.carros.componentes.Motor;
import br.com.pedro.carros.componentes.TocaCD;

public class CarroFacade {
	Farol farol;
	Motor motor;
	TocaCD tocacd;
	public CarroFacade(Farol farol, Motor motor, TocaCD tocacd) {
		super();
		this.farol = farol;
		this.motor = motor;
		this.tocacd = tocacd;
	}
	public void ligaDispositivos (){
		this.farol.acendeFarol();
		this.motor.ligaMotor();
		this.tocacd.ligaTocaCD();
	}
	public void posLigar(){
		this.farol.aumentaRange();
		this.motor.geraCombustao();
		this.tocacd.aumentaVolume();
	}
	public void desligar(){
		this.farol.desligaFarol();
		this.motor.desligaMotor();
		this.tocacd.desligaTocaCD();
	}
	

}
