package it.polito.tdp.food.model;

public class Collegamento {
    TypePortion tp1;
    TypePortion tp2;
    int peso;
	public Collegamento(TypePortion tp1, TypePortion tp2, int peso) {
		this.tp1 = tp1;
		this.tp2 = tp2;
		this.peso = peso;
	}
	public TypePortion getTp1() {
		return tp1;
	}
	public void setTp1(TypePortion tp1) {
		this.tp1 = tp1;
	}
	public TypePortion getTp2() {
		return tp2;
	}
	public void setTp2(TypePortion tp2) {
		this.tp2 = tp2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return tp1 + " " + tp2 + " " + peso;
	}
    
}
