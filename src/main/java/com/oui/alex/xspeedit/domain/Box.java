package com.oui.alex.xspeedit.domain;

import java.util.ArrayList;
import java.util.List;

public class Box {

	private List<Package> packages;
	private int number;
	private boolean full;

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

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(final List<Package> packages) {
		this.packages = packages;
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(final boolean full) {
		this.full = full;
	}

	public int giveWeight(){
		   return packages.stream()
				   .mapToInt(Package::getWeight)
				   .sum();
	}

	public boolean addPackage(Package aPackage){
		return packages.add(aPackage);

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Box ");
		sb.append(number);
		sb.append(full?" full ":" not full ");
		packages.forEach(p -> sb.append(p.toString()));
		return sb.toString();
	}
}
