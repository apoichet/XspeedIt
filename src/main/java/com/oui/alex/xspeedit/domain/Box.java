package com.oui.alex.xspeedit.domain;

import java.util.ArrayList;
import java.util.List;

public class Box {

	private List<Package> packages;
	private int number;

	public final static int MAX_CAPACITY = 10;

	public Box(final int number) {
		packages = new ArrayList<>();
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public boolean isFull() {
		return giveWeight() == MAX_CAPACITY;
	}

	public int giveWeight(){
		   return packages.stream()
				   .mapToInt(Package::getWeight)
				   .sum();
	}

	public void addPackage(Package aPackage){
		int newWeight = giveWeight() + aPackage.getWeight();
		if (newWeight <= MAX_CAPACITY){
			packages.add(aPackage);
		}
	}

	public int getNbrPackage(){
		return packages.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Box ");
		sb.append(number);
		sb.append(isFull()?" full ":" not full ");
		packages.forEach(p -> sb.append(p.toString()));
		return sb.toString();
	}
}
