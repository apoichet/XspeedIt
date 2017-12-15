package com.oui.alex.xspeedit.service;

import com.oui.alex.xspeedit.domain.Box;
import com.oui.alex.xspeedit.domain.Package;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.oui.alex.xspeedit.domain.Box.MAX_CAPACITY;

public class PackBoxServiceOptimize implements IPackBox {

	private final Comparator<Box> REVERSE_WEIGHT = Comparator.comparing(Box::giveWeight).reversed();
	
	@Override
	public List<Box> pack(final List<Package> packages) {
		List<Box> boxes = new ArrayList<>();
		packages.sort(Comparator.comparingInt(Package::getWeight));

		//Maximum de paquet par boite
		for (final Package aPackage : packages) {

			final Optional<Box> boxWithCapacity = findBoxNotFull(boxes, aPackage);

			if (boxWithCapacity.isPresent()){
				boxWithCapacity.get().addPackage(aPackage);
			}
			else {
				addNewBox(boxes, aPackage);
			}
		}

		//Maximum de boite pleine


		return boxes;
	}

	private void addNewBox(final List<Box> boxes, final Package aPackage) {
		Box newBox = new Box(boxes.size() + 1);
		newBox.addPackage(aPackage);
		boxes.add(newBox);
	}

	private Optional<Box> findBoxNotFull(final List<Box> boxes, final Package aPackage) {
		return boxes.stream()
				.filter(findBoxWithCapacity(aPackage))
				.sorted((o1, o2) -> Integer.compare(o2.getNbrPackage(), o1.getNbrPackage()))
				.min(REVERSE_WEIGHT);
	}

	private Predicate<Box> findBoxWithCapacity(final Package aPackage) {
		return box -> !box.isFull() && box.giveWeight() + aPackage.getWeight() <= MAX_CAPACITY;
	}

}
