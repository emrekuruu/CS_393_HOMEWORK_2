package ozu.cs393.homework1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ozu.cs393.homework1.DTO.SongDTO;
import ozu.cs393.homework1.Mapper.SongMapper;
import ozu.cs393.homework1.model.Album;
import ozu.cs393.homework1.model.Artist;
import ozu.cs393.homework1.model.Playlist;
import ozu.cs393.homework1.model.Song;
import ozu.cs393.homework1.repository.AlbumRepository;
import ozu.cs393.homework1.repository.ArtistRepository;
import ozu.cs393.homework1.repository.PlaylistRepository;
import ozu.cs393.homework1.repository.SongRepository;

@Service
public class PlaylistService {
	@Autowired
	SongRepository songRepository;
	
	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	AlbumRepository albumRepository;
	
	@Autowired
	PlaylistRepository playlistRepository;

	@Autowired
	SongMapper songMapper;

	public void saveArtist(Artist artist) {
		artistRepository.save(artist);
	}
	
	public void saveAlbum(Album album) {
		albumRepository.save(album);
	}
		
	public void saveSong(Song song) {
		songRepository.save(song);
	}
	


	public List<SongDTO> getSongsWithSearch(String name){
		List<SongDTO> result = new ArrayList<>();
		List<Song> songs = songRepository.findSongByArtistOrName(name);
		for(Song song : songs){
			SongDTO temp = songMapper.songToDTO(song);
			result.add(temp);
		}
		return result;
	}

	@Transactional
	public void addSongToAlbum(int albumId, Song song) {
		Album album= albumRepository.findById(albumId).get();
		album.getSongs().add(song);
		song.setAlbum(album);
		albumRepository.save(album);
	}

	@Transactional
	public void assignArtistToAlbum(int artistId , int albumId) {
		Album album= albumRepository.findById(albumId).get();
		Artist artist=artistRepository.findById(artistId).get();
		album.setArtist(artist);
		artist.getAlbums().add(album);
		albumRepository.save(album);
		
	}

	@Transactional
	public void deleteAlbum(int albumId) {
		Album album= albumRepository.findById(albumId).get();
		for (Song song : album.getSongs()) {
			song.setAlbum(null);
			songRepository.save(song);
		}
		albumRepository.deleteById(albumId);
	}
	
	public void deleteSong(int songId) {	
		songRepository.deleteById(songId);
	}
	
	public List<SongDTO> getAllSongs() {

		List<SongDTO> result = new ArrayList<>();
		List<Song> songs = songRepository.findAll();
		for(Song song : songs){
			SongDTO temp = songMapper.songToDTO(song);
			result.add(temp);
		}
		return result;
	}
	
	public void createPlayList(String name, List<Song> songs, List<Playlist> playLists) {
		Playlist pl=new Playlist(name,songs, playLists);
		
		playlistRepository.save(pl);
	}
	
	public Playlist findPlaylistById(int id) {
		return playlistRepository.findById(id).get();
	}
	
	public Set<Song> findSongsWithArtistId(int id) {
		Set<Song> list1=songRepository.findSongByAlbumArtistId(id);
		Set<Song> list2=songRepository.findSongByArtistId(id);
	
		list1.addAll(list2);
		return list1;
	}

	@Transactional
	public void createPlayList(String name, List<String> songNames) {
		Playlist pl=new Playlist();
		List<Song> songs = new ArrayList<>();

		for(String songName : songNames){
			Song temp = songRepository.findByName(songName);
			songs.add(temp);
			songRepository.save(temp);
		}
		pl.setName(name);
		pl.setSongs(songs);
		playlistRepository.save(pl);
	}

}
