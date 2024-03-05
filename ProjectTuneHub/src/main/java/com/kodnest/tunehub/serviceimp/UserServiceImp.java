package com.kodnest.tunehub.serviceimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodnest.tunehub.entity.User;
import com.kodnest.tunehub.repository.UserRepository;
import com.kodnest.tunehub.service.UserService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userrepo;

	public String addUser(User user) {
		userrepo.save(user);
		return "user added";
	}
    // checking duplicate entries
	public boolean emailExists(String email) {
		if(userrepo.findByEmail(email) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean validateUser(String email, String password) {
		User user = userrepo.findByEmail(email);
		
		String dbpwd = user.getPassword();
		if(password.equals(dbpwd)) {
			return true;
		}
		else {
		return false;
		}
	}
	public String getRole(String email) {
		// TODO Auto-generated method stub
		User user = userrepo.findByEmail(email);
		return user.getRole();
	}
	
	@Override
	public User getUser(String email) {
		return userrepo.findByEmail(email);
	}
	@Override
	public void updateUser(User user) {
		userrepo.save(user);
		
	}


}
