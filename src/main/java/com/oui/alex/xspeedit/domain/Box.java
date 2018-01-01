package com.oui.alex.xspeedit.domain;

import java.util.ArrayList;
import java.util.List;

public class Box {

	private List<Package> packages;
	private List<Integer> stuff;
	private int number;

	public final static int MAX_CAPACITY = 10;

	public Box(final int number) {
		packages = new ArrayList<>();
		this.number = number;
	}

	public Box() {
		//this.packages = new ArrayList<>();
		this.stuff = new ArrayList<>();
	}
	public Box(List<Package> packages) {
		this.packages = packages;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	/*
	public boolean isFull() {
		return giveWeight() == MAX_CAPACITY;
	}
	*/

	public boolean isFull() {
		return giveWeightStuff() == MAX_CAPACITY;
	}

	public int giveWeightStuff(){
		return stuff.stream()
				.mapToInt(Integer::intValue)
				.sum();
	}

	public int giveWeight(){
		   return packages.stream()
				   .mapToInt(Package::getWeight)
				   .sum();
	}

	public boolean addPackage(Package aPackage){
		int newWeight = giveWeight() + aPackage.getWeight();
		if (newWeight <= MAX_CAPACITY){
			return packages.add(aPackage);
		}
		return false;
	}

	public boolean addStuff(Integer paketWeight){
		int newWeight = giveWeightStuff() + paketWeight;
		if (newWeight <= MAX_CAPACITY){
			return stuff.add(paketWeight);
		}
		return false;
	}

	public int getNbrPackage(){
		return packages.size();
	}

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}

	public List<Integer> getStuff() {
		return stuff;
	}

	public void setStuff(List<Integer> stuff) {
		this.stuff = stuff;
	}

	/*
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Box ");
		sb.append(number);
		sb.append(isFull()?" full ":" not full ");
		packages.forEach(p -> sb.append(p.toString()));
		return sb.toString();
	}
	*/

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Box ");
		sb.append(number);
		sb.append(isFull()?" full ":" not full ");
		stuff.forEach(p -> sb.append(p.toString()));
		return sb.toString();
	}
}
