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
 * Created by rohanayub on 3/31/17.
 */
@RestController
public class BarRestJsonController {

    @Autowired
    private BarAddressRepo storedBarAddressRepo;

    @Autowired
    private BarInformationRepo storedBarInformation;

    @Autowired
    private BarAppUserRepo storedBarUser;

    @Autowired
    private BarDrinkRepo storedDrinksandMixers;

    @RequestMapping(path = "/barLocationJson/createUser", method = RequestMethod.GET)
    public BarAppUser createNewUser(@RequestParam String realName, @RequestParam String username, @RequestParam String password) {
        String accountCreationMessage = null;
        BarAppUser createdUser = new BarAppUser();
        createdUser.setUsername(username);
        createdUser.setRealName(realName);
        createdUser.setPassword(password);

        BarAppUser findUsernames = storedBarUser.findByUsername(username);


        if (findUsernames != null) {
            accountCreationMessage = "Username: " + username + " is already taken. Please choose a new username.";
            createdUser.setJsonResult(accountCreationMessage);
            return createdUser;
        }
        {
            storedBarUser.save(createdUser);

            accountCreationMessage = "Thank you " + realName + "! Your account was created with username: " + username;
            createdUser.setJsonResult(accountCreationMessage);
            return createdUser;
        }


    }

    @RequestMapping(path = "/barLocationJson/listAllBars", method = RequestMethod.GET)
    public Iterable<BarInformation> listAllBars() {
        return storedBarInformation.findAll();
    }

    @RequestMapping(path = "/barLocationJson/createYourBar", method = RequestMethod.POST)
    public BarAddress createBar(@RequestParam(value = "Please enter your username") String username, @RequestParam(value = "Please enter your password") String password,
                                @RequestParam String streetAddress, @RequestParam String cityName, @RequestParam String stateName,
                                @RequestParam Integer zipcode, @RequestParam String barName) {
        BarAppUser foundUsers = storedBarUser.findByUsername(username);
        BarInformation foundBars = storedBarInformation.findByBarName(barName);
        String resultOfBarSave = null;
        BarAddress barAddressToSave = new BarAddress();
        BarInformation barInformationToSave = new BarInformation();
        List<BarAddress> listSaveAddress = new ArrayList<>();


        if (foundUsers == null) {
            resultOfBarSave = "Your username " + username + " was not found. Please make sure you've created an account";
            barAddressToSave.setJsonResult(resultOfBarSave);
            return barAddressToSave;
        }

        String correctPassword = foundUsers.getPassword();
        String displayOwnerName = foundUsers.getRealName();

        if (password.equals(correctPassword) && foundBars != null) {
            barAddressToSave.setStreetAddress(streetAddress);
            barAddressToSave.setCityName(cityName);
            barAddressToSave.setStateName(stateName);
            barAddressToSave.setZipcode(zipcode);
            resultOfBarSave = "A bar with the name: " + barName + " and Street Address: " + streetAddress + " already exists";
            barAddressToSave.setJsonResult(resultOfBarSave);
            return barAddressToSave;

        }

        if (password.equals(correctPassword) && foundBars == null) {
            barAddressToSave.setStreetAddress(streetAddress);
            barAddressToSave.setCityName(cityName);
            barAddressToSave.setStateName(stateName);
            barAddressToSave.setZipcode(zipcode);

            resultOfBarSave = String.format("Hello %s! Your bar %s was created. It is located on: %s %s,%s " + zipcode, displayOwnerName, barName, streetAddress,
                    cityName, stateName);


            barInformationToSave.setBarName(barName);

            listSaveAddress.add(barAddressToSave);

            barInformationToSave.setBarAddress(listSaveAddress);

            storedBarAddressRepo.save(barAddressToSave);
            storedBarInformation.save(barInformationToSave);

            barAddressToSave.setJsonResult(resultOfBarSave);
            return barAddressToSave;

        }

        {

            resultOfBarSave = "Sign in failed for username " + username + ". Please check your password.";
            barAddressToSave.setJsonResult(resultOfBarSave);
            return barAddressToSave;
        }
    }

    @RequestMapping(path = "/barLocationJson/createInventoryItem", method = RequestMethod.POST)
    public BarDrinks createdItem(@RequestParam String username, @RequestParam String password, @RequestParam String barName, @RequestParam(value = "Enter a mixer name.\n(Optional)", required = false) String mixerName,
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
            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;
        }

        if (foundBar == null) {
            resultOfInventorySave = "Your bar " + barName + " was not found. Please make sure you have created the bar.";
            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;
        }


        List<BarDrinks> foundBarList = foundBar.getBarDrinkInformation();
        Integer listSize = foundBarList.size();
        String userPassword = foundUsers.getPassword();
        String foundName = foundUsers.getRealName();


        if (foundBarList.contains(foundAlcoholByNameAndType) && password.equals(userPassword)) {
            createDrinkItem.setMixerName("N/A");
            createDrinkItem.setAlcoholName(alcoholName);
            createDrinkItem.setBeverageType(upperCaseBeverage);
            resultOfInventorySave = "You already have this item in your inventory. Beverage Type: " + upperCaseBeverage + " Alcohol Name: " + alcoholName;
            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;
        }
        if (foundBarList.contains(foundMixerByNameAndType) && password.equals(userPassword)) {
            createDrinkItem.setMixerName(mixerName);
            createDrinkItem.setAlcoholName("N/A");
            createDrinkItem.setBeverageType(upperCaseBeverage);
            resultOfInventorySave = "You already have this item in your inventory. Beverage Type: " + upperCaseBeverage + " Mixer Name: " + mixerName;
            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;
        }


        if (password.equals(userPassword) && foundAlcoholByNameAndType != null && !(foundBarList.contains(foundAlcoholByNameAndType))) {

            foundBarList.add(foundAlcoholByNameAndType);
            foundBar.setBarDrinkInformation(foundBarList);
            storedBarInformation.save(foundBar);

            resultOfInventorySave = "Sign in successful " + foundName + "! Your item was created sucessfully and was added to " + barName + "'s existing inventory";
            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;
        }


        if (password.equals(userPassword) && listSize == 0 && mixerName == null) {
            createDrinkItem.setMixerName("N/A");
            createDrinkItem.setAlcoholName(alcoholName);
            createDrinkItem.setBeverageType(upperCaseBeverage);


            storedDrinksandMixers.save(createDrinkItem);
            foundBarList.add(createDrinkItem);
            storedBarInformation.save(foundBar);
            resultOfInventorySave = "Sign in successful " + foundName + "! Your item was created sucessfully and was added to " + barName + "'s newly created inventory.";
            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;

        }
        if (password.equals(userPassword) && foundBarList != null && mixerName == null) {

            createDrinkItem.setMixerName("N/A");
            createDrinkItem.setAlcoholName(alcoholName);
            createDrinkItem.setBeverageType(upperCaseBeverage);

            storedDrinksandMixers.save(createDrinkItem);


            foundBarList.add(createDrinkItem);

            foundBar.setBarDrinkInformation(foundBarList);
            storedBarInformation.save(foundBar);

            resultOfInventorySave = "Sign in successful " + foundName + "! Your item was created sucessfully and was added to " + barName + "'s inventory";
            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;
        }
        if (password.equals(userPassword) && foundMixerByNameAndType != null && !(foundBarList.contains(foundMixerByNameAndType))) {

            foundBarList.add(foundMixerByNameAndType);
            foundBar.setBarDrinkInformation(foundBarList);
            storedBarInformation.save(foundBar);

            resultOfInventorySave = "Sign in successful " + foundName + "! Your item was created sucessfully and was added to " + barName + "'s existing inventory";
            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;
        }

        if (password.equals(userPassword) && listSize == 0 && alcoholName == null) {
            createDrinkItem.setMixerName(mixerName);
            createDrinkItem.setAlcoholName("N/A");
            createDrinkItem.setBeverageType(upperCaseBeverage);
            storedDrinksandMixers.save(createDrinkItem);

            foundBarList.add(createDrinkItem);
            storedBarInformation.save(foundBar);
            resultOfInventorySave = "Sign in successful " + foundName + "! Your item was created sucessfully and was added to " + barName + "'s newly created inventory.";

            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;

        }
        if (password.equals(userPassword) && foundBarList != null && alcoholName == null) {

            createDrinkItem.setMixerName(mixerName);
            createDrinkItem.setAlcoholName("N/A");
            createDrinkItem.setBeverageType(upperCaseBeverage);

            storedDrinksandMixers.save(createDrinkItem);


            foundBarList.add(createDrinkItem);

            foundBar.setBarDrinkInformation(foundBarList);
            storedBarInformation.save(foundBar);


            resultOfInventorySave = "Sign in successful " + foundName + "! Your item was created sucessfully and was added to " + barName + "'s existing inventory";

            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;
        }
        {
            resultOfInventorySave = "Sign in failed for: " + username + ". Please check your password.";

            createDrinkItem.setJsonResult(resultOfInventorySave);
            return createDrinkItem;
        }
    }

    @RequestMapping(path = "/barLocationJson/getDrinksByType", method = RequestMethod.GET)
    public List<BarDrinks> findAllDrinksByAlcoholType(@RequestParam(value = "Please enter the type of spirit for a list of " +
            "alcohol brands or enter mixer for a list of mixers") String beverageType) {

        String upperCaseBeverage = beverageType.substring(0, 1).toUpperCase() + beverageType.substring(1);
        List<BarDrinks> foundDrinks = storedDrinksandMixers.findByBeverageType(upperCaseBeverage);
        foundDrinks.forEach(aListOfDrinks -> aListOfDrinks.getAlcoholName());


        return foundDrinks;

    }

    @RequestMapping(path = "/barLocationJson/getDrinksByBar", method = RequestMethod.GET)
    public List<BarInformation> findDrinksByBarAndType(@RequestParam(value = "Please enter alcohol name.", required = false) String alcoholName, @RequestParam(value = "Please enter mixer you are looking for", required = false) String mixerName) {

        BarDrinks findDrinkByName = storedDrinksandMixers.findByAlcoholName(alcoholName);
        BarDrinks findMixerByName = storedDrinksandMixers.findByMixerName(mixerName);

        if (findDrinkByName != null && mixerName == null) {
            List<BarInformation> returnBarInformation = storedBarInformation.findAlcoholByBarDrinkInformation(findDrinkByName);
            return returnBarInformation;
        }

        if (findMixerByName != null && alcoholName == null) {
            List<BarInformation> returnBarInformation = storedBarInformation.findMixerByBarDrinkInformation(findMixerByName);
            return returnBarInformation;
        }
        List<BarInformation> returnValue = storedBarInformation.findMixerAndAlcoholByBarDrinkInformation(findDrinkByName, findMixerByName);
        return returnValue;
    }


    @RequestMapping(path = "/barLocationJson/removeDrinkFromInventory", method = RequestMethod.DELETE)
    public BarInformation removeDrinkFromInventory(@RequestParam String username, @RequestParam String password, @RequestParam String barName, @RequestParam String alcoholName) {
        BarAppUser foundUser = storedBarUser.findByUsername(username);
        BarInformation foundBar = storedBarInformation.findByBarName(barName);
        BarInformation barNotFound = new BarInformation();

        String result = null;


        if(foundBar == null){
            result = barName+" does not exist. Please make sure you've created your bar.";
            barNotFound.setJsonResult(result);
            return barNotFound;
        }

        if (foundUser == null) {
            result = "User " + username + " not found. Unable to delete " + alcoholName + " from " + barName + "'s inventory";
            foundBar.setJsonResult(result);
            return foundBar;
        }


        String storedPassword = foundUser.getPassword();
        BarDrinks findDrinkByName = storedDrinksandMixers.findByAlcoholName(alcoholName);
        List<BarDrinks> listInventory = foundBar.getBarDrinkInformation();


        if (password.equals(storedPassword) && listInventory.contains(findDrinkByName)) {
            listInventory.remove(findDrinkByName);
            storedBarInformation.save(foundBar);

            result = "Inventory item " + alcoholName + " was removed from " + barName + "'s inventory.";
            foundBar.setJsonResult(result);
            return foundBar;
        }

        if (password.equals(storedPassword) && !(listInventory.contains(findDrinkByName))) {
            result = "Inventory item " + alcoholName + " does not exist or has already been removed from " + barName + "'s inventory.";
            foundBar.setJsonResult(result);
            return foundBar;
        }
        result = "Sign in failed for " + username + ". Please check your password. Item "+alcoholName+" was not removed.";
        foundBar.setJsonResult(result);
        return foundBar;
    }
}
