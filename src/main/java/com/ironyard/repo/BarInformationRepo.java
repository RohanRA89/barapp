package com.ironyard.repo;

import com.ironyard.data.BarDrinks;
import com.ironyard.data.BarInformation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rohanayub on 3/15/17.
 */
public interface BarInformationRepo extends CrudRepository<BarInformation,Long> {
    public BarInformation findByBarName(String barName);
    public List<BarInformation> findAlcoholByBarDrinkInformation(BarDrinks alcoholName);
    public List<BarInformation> findMixerByBarDrinkInformation(BarDrinks mixerName);
    public List<BarInformation> findMixerAndAlcoholByBarDrinkInformation(BarDrinks alcoholName, BarDrinks mixerName);
    public BarInformation findBarsAlcoholNameByBarDrinkInformation(BarDrinks alcoholName);

}
