package com.fenoreste.voculares.dao;

import com.fenoreste.voculares.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {

	public User findUserByUsername(String username);
	
}
