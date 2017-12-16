package com.oui.alex.xspeedit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.oui.alex.xspeedit.domain.Box;
import com.oui.alex.xspeedit.domain.Package;

@RunWith(MockitoJUnitRunner.class)
public class PackBoxServiceTest {

	@InjectMocks
	private PackBoxService packBox;

	private List<Package> packages;
	private List<Box> boxes;

	@Before
	public void setUp(){
		packages = new ArrayList<>();
	}

	@After
	public void after(){
		boxes.forEach(box -> System.out.println(box.toString()));
	}

	@Test
	public void should_give_ONE_box_when_9_1(){
		//Given
		given_packages(9,1);

		//When
		boxes = packBox.pack(packages);

		//Then
		then_assert_boxes_list_has_size(1);
	}

	@Test
	public void should_give_TWO_box_when_9_1_4_6(){
		//Given
		given_packages(9,1,4,6);

		//When
		boxes = packBox.pack(packages);

		//Then
		then_assert_boxes_list_has_size(2);
	}

	@Test
	public void should_give_TWO_box_when_9_4_1_6(){
		//Given
		given_packages(9,4,1,6);

		//When
		boxes = packBox.pack(packages);

		//Then
		then_assert_boxes_list_has_size(2);
	}

	@Test
	public void should_give_FOUR_box_when_9_4_1_6_3_5_9(){
		//Given
		given_packages(9,4,1,6,3,5,9);

		//When
		boxes = packBox.pack(packages);

		//Then
		then_assert_boxes_list_has_size(4);
	}

	@Test
	public void should_optimize_TWO_box_when_3_5_9_1_2(){
		//Given
		given_packages(3,5,9,1,2);

		//When
		boxes = packBox.pack(packages);

		//Then
		then_assert_boxes_list_has_size(2);
	}

	@Test
	public void should_give_ONE_box_FULL_when_1_1_1_1_1_1_1_1_1_1(){
		//Given
		given_packages(1,1,1,1,1,1,1,1,1,1);

		//When
		boxes = packBox.pack(packages);

		//Then
		then_assert_boxes_list_has_size(1);
		assertThat(boxes.stream().allMatch(Box::isFull)).isTrue();
	}

	@Test
	public void should_give_EIGHT_box_when_1_6_3_8_4_1_6_8_9_5_2_5_7_7_3(){
		//Given
		given_packages(1,6,3,8,4,1,6,8,9,5,2,5,7,7,3);

		//When
		boxes = packBox.pack(packages);

		//Then
		then_assert_boxes_list_has_size(8);
	}

	private void given_packages(Integer... weights){
		Arrays.stream(weights).forEach(w -> packages.add(new Package(w)));
	}

	private void then_assert_boxes_list_has_size(int size){
		assertThat(boxes).hasSize(size);
	}

}