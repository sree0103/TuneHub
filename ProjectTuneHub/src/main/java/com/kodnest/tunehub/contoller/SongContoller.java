package com.kodnest.tunehub.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.serviceimp.SongServiceImpl;

@Controller
public class SongContoller {

	@Autowired
	SongServiceImpl songService;
	
	@PostMapping("/addsong")
	public String addsong(@ModelAttribute Song song ) {
		boolean status = songService.songExists(song.getName());
		if(status == false) {
			songService.addSong(song);
			System.out.println("Song added");
			}
		else {
			System.out.println("Song already exists");
		}
			return "adminhome";
    }
	@GetMapping("/viewsongs")
		public String viewsongs(Model model) {
		List<Song> songList = songService.fetchAllSongs();
//		System.out.println(songList);
		model.addAttribute("songs", songList);
			return "displaysongs";
		}
	
	@GetMapping("/playsongs")
	public String playsongs(Model model) {
		boolean premium = true;
		if(premium) {
	       List<Song> songList = songService.fetchAllSongs();
//	       System.out.println(songList);
	       model.addAttribute("songs", songList);
		   return "displaysongs";
		}
		else {
			return "subscriptionform";
		}
		
	}
	
	}

