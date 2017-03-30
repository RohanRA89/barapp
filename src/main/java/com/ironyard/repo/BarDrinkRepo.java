package com.ironyard.repo;

import com.ironyard.data.BarDrinks;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rohanayub on 3/17/17.
 */
public interface BarDrinkRepo extends CrudRepository<BarDrinks,Long>{
   public BarDrinks findByAlcoholNameAndBeverageType(String alcoholName,String beverageType);
   public BarDrinks findByMixerNameAndBeverageType(String mixerName,String beverageType);
   public BarDrinks findByMixerName(String mixerName);
   public List<BarDrinks> findByBeverageType(String beverageType);
   public BarDrinks findByAlcoholName(String alcoholName);
}
