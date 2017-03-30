package com.ironyard.repo;

import com.ironyard.data.BarAppUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rohanayub on 3/17/17.
 */
public interface BarAppUserRepo extends CrudRepository<BarAppUser,Long>{
    public BarAppUser findByUsername(String username);
}
