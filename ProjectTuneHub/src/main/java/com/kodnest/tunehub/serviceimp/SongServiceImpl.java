package com.kodnest.tunehub.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.repository.SongRepository;

@Service
public class SongServiceImpl {
  
	@Autowired
	SongRepository songrepo;


	public String addSong(Song song) {
		// TODO Auto-generated method stub
		songrepo.save(song);
		return "song added";
		
	}

	public boolean songExists(String name) {
		Song song = songrepo.findByName(name);
		if(song == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public List<Song> fetchAllSongs() {
		List<Song> songs = songrepo.findAll();
		return songs;
	}

	public void updateSong(Song s) {
		
		songrepo.save(s);
	}

	
}




