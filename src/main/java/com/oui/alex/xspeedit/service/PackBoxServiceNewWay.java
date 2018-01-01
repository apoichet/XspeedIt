package com.oui.alex.xspeedit.service;

import com.oui.alex.xspeedit.domain.Box;
import com.oui.alex.xspeedit.domain.Package;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Collections2.permutations;
import static java.util.Comparator.comparingInt;

public class PackBoxServiceNewWay implements IPackBox{

    @Override
    public List<Box> pack(List<Package> packages) {

        //Genere les combinaisons possibles
        Collection<List<Package>> packagePermutations = permutations(packages);

        //Donne la liste des boites candidates
        List<Box> boxCandidates = buildFullBoxes(packagePermutations);

        //Garde que les boite avec nombre de paquet max
        List<Box> boxWithMaxPackages = keepBoxWithMaxPackage(boxCandidates);

        //Recupere les paquets non encore en boite
        List<Package> unBoxPackages = packages.stream()
                .filter(Package::unBox)
                .sorted(weightComparator())
                .collect(Collectors.toList());

        //Construit des boites non remplies
        List<Box> incompleteBoxes = buildBoxes(unBoxPackages);

        //Combine les résultats
        boxWithMaxPackages.addAll(incompleteBoxes);

        return boxWithMaxPackages;
    }

    private Comparator<Package> weightComparator() {
        return (p1, p2) -> Integer.compare(p2.getWeight(), p1.getWeight());
    }

    private List<Box> buildBoxes(List<Package> packages){

        if (CollectionUtils.isEmpty(packages)){
            return new ArrayList();
        }

        //Un maximum de paquet par boite
        List<Package> collect = packages.stream()
                .sorted(comparingInt(Package::getWeight).reversed())
                .collect(Collectors.toList());

        List<Box> incompleteBoxes = new ArrayList<>();


        Box incompleteBox = new Box();
        incompleteBoxes.add(incompleteBox);
        for (Package aPackage : collect) {

            boolean addBox = incompleteBox.addPackage(aPackage);

            if (!addBox){
                incompleteBox = new Box();
                incompleteBoxes.add(incompleteBox);
                incompleteBox.addPackage(aPackage);
            }
        }

        return incompleteBoxes;
    }


    private List<Box> buildFullBoxes(Collection<List<Package>> packagePermutations){
        List<Box> fullBoxes = new ArrayList<>();

        for (List<Package> permutations : packagePermutations) {

            Box boxFull = new Box();

            for (Package aPackage : permutations) {

                boolean addPackage = boxFull.addPackage(aPackage);
                if (boxFull.isFull()){
                    fullBoxes.add(boxFull);
                    break;
                }
                if (!addPackage){
                    break;
                }
            }
        }
        return fullBoxes;
    }

    private List<Box> keepBoxWithMaxPackage(List<Box> boxeCandidates){
        List<Box> collect = boxeCandidates.stream()
                .sorted(comparingInt(Box::getNbrPackage).reversed())
                .collect(Collectors.toList());
        List<Box> boxWithMaxPackageList = new ArrayList<>();

        collect.forEach(box -> {

            if (box.getPackages().stream().allMatch(Package::unBox)){

                box.getPackages().forEach(aPackage -> aPackage.setPackaged(true));
                Box boxWithMaxPackage = new Box(box.getPackages());
                boxWithMaxPackageList.add(boxWithMaxPackage);

            }

        });

        return boxWithMaxPackageList;
    }

}
