package ozu.cs393.homework1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ozu.cs393.homework1.DTO.SongDTO;
import ozu.cs393.homework1.service.PlaylistService;

import java.util.List;

@RestController()
@CrossOrigin
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;


    @GetMapping(value = "songs/")
    public ResponseEntity<Object> getSongs(@RequestParam("name") String name){
        List<SongDTO> songs = playlistService.getSongsWithSearch(name);
        return ResponseEntity.status(HttpStatus.OK).body(songs);
    }

    @PostMapping(value = "add/playlist/")
    public ResponseEntity<Object> savePlaylist( @RequestParam("name") String name,@RequestParam("songNames") List<String> songNames){
        playlistService.createPlayList(name,songNames);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }



}
