package com.ironyard.controller;

import com.ironyard.data.BarAddress;
import com.ironyard.data.BarAppUser;
import com.ironyard.data.BarDrinks;
import com.ironyard.data.BarInformation;
import com.ironyard.repo.BarAddressRepo;
import com.ironyard.repo.BarAppUserRepo;
import com.ironyard.repo.BarDrinkRepo;
import com.ironyard.repo.BarInformationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohanayub on 3/15/17.
 */
@RestController
public class BarRestController {

    @Autowired
    private BarAddressRepo storedBarAddressRepo;

    @Autowired
    private BarInformationRepo storedBarInformation;

    @Autowired
    private BarAppUserRepo storedBarUser;

    @Autowired
    private BarDrinkRepo storedDrinksandMixers;

    @RequestMapping(path = "/barLocation/createUser", method = RequestMethod.GET)
    public String createNewUser(@RequestParam String realName, @RequestParam String username, @RequestParam String password) {
        String accountCreationMessage = null;
        BarAppUser createdUser = new BarAppUser();
        createdUser.setUsername(username);
        createdUser.setRealName(realName);
        createdUser.setPassword(password);

        BarAppUser findUsernames = storedBarUser.findByUsername(username);


        if (findUsernames != null) {
            accountCreationMessage = "Username: " + username + " is already taken. Please choose a new username.";
            return accountCreationMessage;
        }
        {
            storedBarUser.save(createdUser);

            accountCreationMessage = "Thank you " + realName + "!\nYour account was created with username: " + username;

            return accountCreationMessage;
        }


    }

    @RequestMapping(path = "/barLocation/listAllBars", method = RequestMethod.GET)
    public Iterable<BarInformation> listAllBars() {
        return storedBarInformation.findAll();
    }

    @RequestMapping(path = "/barLocation/createYourBar", method = RequestMethod.POST)
    public String createBar(@RequestParam(value = "Please enter your username") String username, @RequestParam(value = "Please enter your password") String password,
                            @RequestParam String streetAddress, @RequestParam String cityName, @RequestParam String stateName,
                            @RequestParam Integer zipcode, @RequestParam String barName) {
        BarAppUser foundUsers = storedBarUser.findByUsername(username);
        BarInformation foundBars = storedBarInformation.findByBarName(barName);
        //BarAddress existingStreetNames = storedBarAddressRepo.findByStreetAddress(streetAddress);
        String resultOfBarSave = null;
        BarAddress barAddressToSave = new BarAddress();
        BarInformation barInformationToSave = new BarInformation();
        List<BarAddress> listSaveAddress = new ArrayList<>();


        if (foundUsers == null) {
            resultOfBarSave = "Your username " + username + " was not found. Please make sure you've created an account";
            return resultOfBarSave;
        }

        String correctPassword = foundUsers.getPassword();
        String displayOwnerName = foundUsers.getRealName();

        if (password.equals(correctPassword) && foundBars != null) {
            resultOfBarSave = "A bar with the name: " + barName + "\nand\nStreet Address: " + streetAddress + "\nalready exists";
            return resultOfBarSave;

        }

        if (password.equals(correctPassword) && foundBars == null) {
            barAddressToSave.setStreetAddress(streetAddress);
            barAddressToSave.setCityName(cityName);
            barAddressToSave.setStateName(stateName);
            barAddressToSave.setZipcode(zipcode);

            barAddressToSave.getStreetAddress();

            barInformationToSave.setBarName(barName);

            listSaveAddress.add(barAddressToSave);

            barInformationToSave.setBarAddress(listSaveAddress);

            storedBarAddressRepo.save(barAddressToSave);
            storedBarInformation.save(barInformationToSave);
            resultOfBarSave = String.format("Hello %s!\nYour bar %s was created.\nIt is located on:\n%s\n%s,%s\n" + zipcode, displayOwnerName, barName, streetAddress,
                    cityName, stateName);
            return resultOfBarSave;

        }

        {
            resultOfBarSave = "Sign in failed for username " + username + ".\nPlease check your password.";
            return resultOfBarSave;
        }
    }

    @RequestMapping(path = "/barLocation/createInventoryItem", method = RequestMethod.POST)
    public String createdItem(@RequestParam String username, @RequestParam String password, @RequestParam String barName, @RequestParam(value = "Enter a mixer name.\n(Optional)", required = false) String mixerName,
                              @RequestParam(value = "Enter the type of spirit,type of beer or if this is a mixer.") String beverageType, @RequestParam(value = "Enter an alcohol brand name.\n(Optional)", required = false) String alcoholName) {
        BarAppUser foundUsers = storedBarUser.findByUsername(username);
        BarInformation foundBar = storedBarInformation.findByBarName(barName);
        String upperCaseBeverage = beverageType.substring(0, 1).toUpperCase() + beverageType.substring(1);
        BarDrinks foundAlcoholByNameAndType = storedDrinksandMixers.findByAlcoholNameAndBeverageType(alcoholName, upperCaseBeverage);
        BarDrinks foundMixerByNameAndType = storedDrinksandMixers.findByMixerNameAndBeverageType(mixerName, upperCaseBeverage);
        BarDrinks createDrinkItem = new BarDrinks();
        String resultOfInventorySave = null;

        if (foundUsers == null) {
            resultOfInventorySave = "Your username " + username + " was not found. Please make sure you've created an account";
            return resultOfInventorySave;
        }

        if (foundBar == null) {
            resultOfInventorySave = "Your bar " + barName + " was not found. Please make sure you have created the bar.";
            return resultOfInventorySave;
        }


        List<BarDrinks> foundBarList = foundBar.getBarDrinkInformation();
        Integer listSize = foundBarList.size();
        String userPassword = foundUsers.getPassword();
        String foundName = foundUsers.getRealName();



        if (foundBarList.contains(foundAlcoholByNameAndType) && password.equals(userPassword)) {
            resultOfInventorySave = "You already have this item in your inventory.\nBeverage Type: " + upperCaseBeverage + "\nAlcohol Name: " + alcoholName;
            return resultOfInventorySave;
        }
        if (foundBarList.contains(foundMixerByNameAndType) && password.equals(userPassword)) {
            resultOfInventorySave = "You already have this item in your inventory.\nBeverage Type: " + upperCaseBeverage + "\nMixer Name: " + mixerName;
            return resultOfInventorySave;
        }


        if (password.equals(userPassword) && foundAlcoholByNameAndType != null && !(foundBarList.contains(foundAlcoholByNameAndType))) {

            foundBarList.add(foundAlcoholByNameAndType);
            foundBar.setBarDrinkInformation(foundBarList);
            storedBarInformation.save(foundBar);

            resultOfInventorySave = "Sign in successful " + foundName + "!\nYour item was created sucessfully and was added to " + barName + "'s existing inventory";
            return resultOfInventorySave;
        }


        if (password.equals(userPassword) && listSize == 0 && mixerName == null) {
            createDrinkItem.setMixerName("N/A");
            createDrinkItem.setAlcoholName(alcoholName);
            createDrinkItem.setBeverageType(upperCaseBeverage);


            storedDrinksandMixers.save(createDrinkItem);
            foundBarList.add(createDrinkItem);
            storedBarInformation.save(foundBar);
            resultOfInventorySave = "Sign in successful " + foundName + "!\nYour item was created sucessfully and was added to " + barName + "'s newly created inventory.";

            return resultOfInventorySave;

        }
        if (password.equals(userPassword) && foundBarList != null && mixerName == null) {

            createDrinkItem.setMixerName("N/A");
            createDrinkItem.setAlcoholName(alcoholName);
            createDrinkItem.setBeverageType(upperCaseBeverage);

            storedDrinksandMixers.save(createDrinkItem);


            foundBarList.add(createDrinkItem);

            foundBar.setBarDrinkInformation(foundBarList);
            storedBarInformation.save(foundBar);


            resultOfInventorySave = "Sign in successful " + foundName + "!\nYour item was created sucessfully and was added to " + barName + "'s inventory";
            return resultOfInventorySave;
        }
        if (password.equals(userPassword) && foundMixerByNameAndType != null && !(foundBarList.contains(foundMixerByNameAndType))) {

            foundBarList.add(foundMixerByNameAndType);
            foundBar.setBarDrinkInformation(foundBarList);
            storedBarInformation.save(foundBar);

            resultOfInventorySave = "Sign in successful " + foundName + "!\nYour item was created sucessfully and was added to " + barName + "'s existing inventory";
            return resultOfInventorySave;}

        if (password.equals(userPassword) && listSize == 0 && alcoholName == null) {
            createDrinkItem.setMixerName(mixerName);
            createDrinkItem.setAlcoholName("N/A");
            createDrinkItem.setBeverageType(upperCaseBeverage);
            storedDrinksandMixers.save(createDrinkItem);

            foundBarList.add(createDrinkItem);
            storedBarInformation.save(foundBar);
            resultOfInventorySave = "Sign in successful " + foundName + "!\nYour item was created sucessfully and was added to " + barName + "'s newly created inventory.";
            return resultOfInventorySave;

        }
         if (password.equals(userPassword) && foundBarList != null && alcoholName == null) {

            createDrinkItem.setMixerName(mixerName);
            createDrinkItem.setAlcoholName("N/A");
            createDrinkItem.setBeverageType(upperCaseBeverage);

            storedDrinksandMixers.save(createDrinkItem);


            foundBarList.add(createDrinkItem);

            foundBar.setBarDrinkInformation(foundBarList);
            storedBarInformation.save(foundBar);


            resultOfInventorySave = "Sign in successful " + foundName + "!\nYour item was created sucessfully and was added to " + barName + "'s existing inventory";
            return resultOfInventorySave;
        }
        {
            resultOfInventorySave = "Sign in failed for: " + username + ".\nPlease check your password.";
            return resultOfInventorySave;
        }
    }

    @RequestMapping(path = "/barLocation/getDrinksByType", method = RequestMethod.GET)
    public List<BarDrinks> findAllDrinksByAlcoholType(@RequestParam(value = "Please enter the type of spirit for a list of " +
            "alcohol brands or enter mixer for a list of mixers") String beverageType) {
        //List<BarDrinks> returnDrinks = null;

        String upperCaseBeverage = beverageType.substring(0, 1).toUpperCase() + beverageType.substring(1);
        List<BarDrinks> foundDrinks = storedDrinksandMixers.findByBeverageType(upperCaseBeverage);
        foundDrinks.forEach(aListOfDrinks -> aListOfDrinks.getAlcoholName());


        return foundDrinks;

    }

    @RequestMapping(path = "/barLocation/getDrinksByBar", method = RequestMethod.GET)
    public String findDrinksByBarAndType(@RequestParam(value = "Please enter alcohol name you are looking for to show a " +
            "list of bars that have this liquor.", required = false) String alcoholName, @RequestParam(value = "Please enter mixer you are looking for to show a " +
            "list of bars that have this mixer.", required = false) String mixerName) {

        BarDrinks findDrinkByName = storedDrinksandMixers.findByAlcoholName(alcoholName);
        BarDrinks findMixerByName = storedDrinksandMixers.findByMixerName(mixerName);
        if (alcoholName != null && mixerName != null) {
            return "Please choose either an alcohol name or a mixer name. Unable to search with both values populated.";

//            List<BarInformation> findByAlcoholName = storedBarInformation.findMixerAndAlcoholByBarDrinkInformation(findDrinkByName,findMixerByName);
//            findByAlcoholName.forEach(p -> p.getBarName().toString());
//
//            return "Here are a list of bars that have " + alcoholName + " & "+mixerName+"\n" + findByAlcoholName;
        }

        if (findDrinkByName != null && mixerName == null) {
            List<BarInformation> findByAlcoholName = storedBarInformation.findAlcoholByBarDrinkInformation(findDrinkByName);
            findByAlcoholName.forEach(p -> p.getBarName().toString());

            return "Here are a list of bars that have " + alcoholName + "\n" + findByAlcoholName;
        }
        if (findMixerByName != null && alcoholName == null) {
            List<BarInformation> findByMixerName = storedBarInformation.findAlcoholByBarDrinkInformation(findMixerByName);
            findByMixerName.forEach(p -> p.getBarName().toString());

            return "Here are a list of bars that have " + mixerName + "\n" + findByMixerName;
        }
        if (findDrinkByName == null && mixerName == null) {
            return "We are unable to find any bars with " + alcoholName + ".";
        }
        if (findMixerByName == null && alcoholName == null) {
            return "We are unable to find any bars with " + mixerName + ".";

        }
        return "Please enter a alcohol name or mixer type to search for.";
    }
}


