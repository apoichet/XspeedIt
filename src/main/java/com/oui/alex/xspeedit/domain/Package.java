package com.oui.alex.xspeedit.domain;

public class Package {

	private int weight;
	private boolean packaged;

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

	public boolean isPackaged() {
		return packaged;
	}

	public boolean unBox() {
		return !packaged;
	}

	public void setPackaged(boolean packaged) {
		this.packaged = packaged;
	}

	@Override
	public String toString() {
		return "/"+weight;
	}
}
