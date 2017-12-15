package com.oui.alex.xspeedit.service;

import java.util.List;

import com.oui.alex.xspeedit.domain.Box;
import com.oui.alex.xspeedit.domain.Package;

public interface IPackBox {

	List<Box> pack(List<Package> packages);

}
