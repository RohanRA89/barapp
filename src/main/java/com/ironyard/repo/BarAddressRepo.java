package com.ironyard.repo;

import com.ironyard.data.BarAddress;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rohanayub on 3/15/17.
 */
public interface BarAddressRepo extends CrudRepository<BarAddress,Long> {
public BarAddress findByStreetAddress(String streetAddress);

}
