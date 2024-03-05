package com.kodnest.tunehub.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kodnest.tunehub.entity.Playlist;
import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.serviceimp.PlaylistServiceImpl;
import com.kodnest.tunehub.serviceimp.SongServiceImpl;

@Controller
public class PlaylistController {
 
	@Autowired
	SongServiceImpl songServ;
	
	
	@Autowired
	PlaylistServiceImpl playServ;
	
	@GetMapping("/createplaylists")
	public String createPlaylists(Model model) {
	List<Song> songList = songServ.fetchAllSongs();
	model.addAttribute("songs", songList);
		return "createplaylists";
	}
	
	@PostMapping("/addplaylist")
	public String addplaylist(@ModelAttribute Playlist playlist) {
		//updating the playlist table
		playServ.addplaylist(playlist);
		
		//updating the song table
		List<Song> songList = playlist.getSongs();
		for(Song s : songList) {
			s.getPlaylists().add(playlist);
			songServ.updateSong(s);
		}
		return "adminhome";
	}
	@GetMapping("/viewplaylist")
	public String viewPlaylist(Model model) {
	List<Playlist> allPlaylists = playServ.fetchAllPlaylists();
//	System.out.println(songList);
	model.addAttribute("allPlaylists", allPlaylists);
		return "displayplaylist";
}
}