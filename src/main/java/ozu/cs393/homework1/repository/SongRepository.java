package ozu.cs393.homework1.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ozu.cs393.homework1.model.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {
	
	public Set<Song> findSongByAlbumArtistId(int id);
	
	
	public Set<Song> findSongByArtistId(int id);

	@Query(value = "SELECT s.* FROM Song s WHERE s.name = :name", nativeQuery = true)
	Song findByName(@Param("name") String name);


	@Query(value = "SELECT s.*, a.name AS artist_name FROM Song s JOIN Artist a ON s.artist_id = a.id WHERE s.name = :name OR a.name = :name", nativeQuery = true)
	List<Song> findSongByArtistOrName(@Param("name") String name);
}

