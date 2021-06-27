package it.polito.tdp.food.model;

public class Corrispondenza {
        TypePortion tp;
        int peso;
		public Corrispondenza(TypePortion tp, int peso) {
			this.tp = tp;
			this.peso = peso;
		}
		public TypePortion getTp() {
			return tp;
		}
		public void setTp(TypePortion tp) {
			this.tp = tp;
		}
		public int getPeso() {
			return peso;
		}
		public void setPeso(int peso) {
			this.peso = peso;
		}
		@Override
		public String toString() {
			return tp + " " + peso;
		}
        
}
