package it.polito.tdp.food.model;

public class TypePortion {
      String name;

	public TypePortion(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
      
}
