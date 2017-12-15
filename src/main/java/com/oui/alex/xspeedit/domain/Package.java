package com.oui.alex.xspeedit.domain;

public class Package {

	private int weight;

	public Package() {
	}

	public Package(final int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(final int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "/"+weight;
	}
}
