package com.dakgoot.dakgoot.repository;


import org.springframework.data.repository.CrudRepository;

import com.dakgoot.dakgoot.model.UserObj;

public interface UserRepository extends CrudRepository<UserObj, Integer> {}