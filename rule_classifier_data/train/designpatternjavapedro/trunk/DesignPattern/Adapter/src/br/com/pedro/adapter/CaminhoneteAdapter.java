package br.com.pedro.adapter;

import br.com.pedro.automoveis.CaminhoneteInterface;

import br.com.pedro.automoveis.CarroInterface;

public class CaminhoneteAdapter implements CaminhoneteInterface {
	CarroInterface c1;
	public CaminhoneteAdapter(CarroInterface c2){
		super();
		this.c1=c2;
	}
	@Override
	public void acelera() {
	c1.acelera();	
	}

}
