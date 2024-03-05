package com.kodnest.tunehub.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.entity.User;
import com.kodnest.tunehub.serviceimp.SongServiceImpl;
import com.kodnest.tunehub.serviceimp.UserServiceImp;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	UserServiceImp serviceimp;
	
	@Autowired
	SongServiceImpl songService;
	
	@PostMapping(value="/register")
	public String addUser(@ModelAttribute User user) {
		//email taken from registration form
		String email= user.getEmail();
		//checking if email as entered in registration form is present in db or not
		boolean status = serviceimp.emailExists(email);
		if(status == false) {
		 serviceimp.addUser(user);
		 System.out.println("User added");
		}
		else {
			System.out.println("User already exists");
		}
		 return "login";
	}
	
	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
		if(serviceimp.validateUser(email, password) == true) {
			String role = serviceimp.getRole(email);
			session.setAttribute("email", email);
			if(role.equals("admin")) {
				return "adminhome";
			}
			else {
				User user = serviceimp.getUser(email);
				boolean userstatus = user.isIspremium();
				List<Song> Songs = songService.fetchAllSongs();
				model.addAttribute("songs", Songs);
				model.addAttribute("ispremium", userstatus);
				
				return "customerhome";
			}
		}
		else {
			
			return "login";
		}
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
}
