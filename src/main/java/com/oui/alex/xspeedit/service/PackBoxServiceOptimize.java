package com.oui.alex.xspeedit.service;

import com.oui.alex.xspeedit.domain.Box;
import com.oui.alex.xspeedit.domain.Package;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

public class PackBoxServiceOptimize implements IPackBox {

	@Override
	public List<Box> pack(final List<Package> packages) {
		List<Box> boxes = new ArrayList<>();
		int count = 1;

		packages.sort(comparingInt(Package::getWeight));

		for (Package aPackage : packages) {

			if (!aPackage.isPackaged()){

				Box newBox = new Box(count);
				boxes.add(newBox);
				newBox.addPackage(aPackage);

				//On essaye d'ajouter paquet par parquet pour faire le max
				for (Package packageIn : packages) {
					if (aPackage != packageIn && !packageIn.isPackaged()){
						if (aPackage.getWeight() + packageIn.getWeight() <= Box.MAX_CAPACITY){
							newBox.addPackage(packageIn);
						}
						else{
							break;
						}
					}
				}

				if (newBox.isFull()){
					//Ok on a réussi a remplir une boite avec des paquets
					newBox.getPackages().forEach(packageIn -> packageIn.setPackaged(true));
					count++;
					continue;
				}
				else{
					//On annule
					newBox.getPackages().clear();
					newBox.addPackage(aPackage);
				}

				//On essaye de trouver le paquet complémentaire pour faire le max
				Optional<Package> complementaryPack = packages.stream()
						.filter(packageIn -> packageIn != aPackage && !packageIn.isPackaged()
						&& packageIn.getWeight() + aPackage.getWeight() == Box.MAX_CAPACITY)
						.findFirst();

				if (complementaryPack.isPresent()){
					//On a trouver le paquet complémentaire pour faire le max
					newBox.addPackage(complementaryPack.get());
					newBox.getPackages().forEach(packageIn -> packageIn.setPackaged(true));
					count++;
					continue;
				}

				//Le paquet se retrouve seul
				aPackage.setPackaged(true);
				count++;
			}
		}

		//On optimize le remplissage entre boite non pleine
		for (Box box : boxes) {
			if (!box.isFull()){

				boxes.forEach(boxIn -> {
					if (box != boxIn && !boxIn.isFull() && box.giveWeight() + boxIn.giveWeight() <= Box.MAX_CAPACITY){
						box.getPackages().addAll(boxIn.getPackages());
						boxIn.getPackages().clear();
					}
				});

			}
		}

		List<Box> boxesToRemove = boxes.stream().filter(box -> box.getPackages().isEmpty()).collect(Collectors.toList());
		boxes.removeAll(boxesToRemove);

		return boxes;
	}

}
