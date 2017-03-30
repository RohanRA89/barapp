package com.ironyard;

import com.ironyard.data.BarDrinks;
import com.ironyard.data.BarInformation;
import com.ironyard.repo.BarDrinkRepo;
import com.ironyard.repo.BarInformationRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BarappApplicationTests {
	@Autowired
	private BarDrinkRepo testRepo;
	@Autowired
	private BarInformationRepo testBarRepo;
	@Test
	public void contextLoads() {



		String mixerName = "Sprite";
		String beverageTypeMix = "Mixer";

		String alcoholName = "Kraken";
		String beverageTypeAlcohol = "Rum";

		String alcoholNameTwo = "Captain Morgan";
		String beverageTypeAlcoholTwo = "Rum";

		String alcoholNameThree = "El Dorado";
		String beverageTypeAlcoholThree = "Rum";

		String alcoholNameFour = "Rum";
		String beverageTypeAlcoholFour = "Rum";

		BarDrinks createAlcoholicDrink = new BarDrinks();
		BarDrinks createAlcoholicDrinkTwo = new BarDrinks();
		BarDrinks createAlcoholicDrinkThree = new BarDrinks();
		BarDrinks createAlcoholicDrinkFour = new BarDrinks();
		BarDrinks createMixerType = new BarDrinks();
//
//		BarDrinks createDrink = new BarDrinks();
//
//		createDrink.setMixerName("Ginger Beer");
//		createDrink.setBeverageType("Mixer");
//		testRepo.save(createDrink);
//
//		BarDrinks createDrinkTwo = new BarDrinks();
//		createDrinkTwo.setBeverageType("Vodka");
//		createDrinkTwo.setAlcoholName("Touch");
//		testRepo.save(createDrinkTwo);
//
//		BarDrinks found = testRepo.findByMixerNameAndBeverageType("Ginger Beer","Mixer");
//		System.out.println(found);
//
//		BarDrinks foundTwo = testRepo.findByAlcoholNameAndBeverageType("Touch","Vodka");
//		System.out.println(foundTwo);


		createMixerType.setMixerName(mixerName);
		createMixerType.setBeverageType(beverageTypeMix);
		testRepo.save(createMixerType);

//		BarDrinks foundDrink = testRepo.findByMixerNameAndBeverageType(mixerName,beverageTypeMix);
//		System.out.println(foundDrink);


		createAlcoholicDrink.setAlcoholName(alcoholName);
		createAlcoholicDrink.setBeverageType(beverageTypeAlcohol);
		testRepo.save(createAlcoholicDrink);

//		BarDrinks foundAlcoholDrink = testRepo.findByAlcoholNameAndBeverageType(alcoholName,beverageTypeAlcohol);
//		System.out.println(foundAlcoholDrink);



		createAlcoholicDrinkTwo.setAlcoholName(alcoholNameTwo);
		createAlcoholicDrinkTwo.setBeverageType(beverageTypeAlcoholTwo);
//		if(foundAlcoholDrink != null){
//			System.out.println("This value is already saved to your inventory.");
//		}
		testRepo.save(createAlcoholicDrinkTwo);

		createAlcoholicDrinkThree.setAlcoholName(alcoholNameThree);
		createAlcoholicDrinkThree.setBeverageType(beverageTypeAlcoholThree);
		testRepo.save(createAlcoholicDrinkThree);

		createAlcoholicDrinkFour.setAlcoholName(alcoholNameFour);
		createAlcoholicDrinkFour.setBeverageType(beverageTypeAlcoholFour);
		testRepo.save(createAlcoholicDrinkFour);

//		List<BarDrinks> data = testRepo.findByBeverageType("Rum");
//
//		data.forEach(aBarDrink -> System.out.println(aBarDrink.getAlcoholName()));
//
//		for(BarDrinks aBarDrink: data){
//			System.out.println(aBarDrink.getAlcoholName());

			BarInformation newBar = new BarInformation();
			BarInformation newBarTwo = new BarInformation();

			String barNameOne = "public House";
			String barNameTwo = "Ale House";


			String upperCase = barNameOne.substring(0,1).toUpperCase()+barNameOne.substring(1);
			System.out.println(upperCase);


		}

	}


