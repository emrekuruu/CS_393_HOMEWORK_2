package ozu.cs393.homework1.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ozu.cs393.homework1.DTO.SongDTO;
import ozu.cs393.homework1.model.Song;

@Mapper(componentModel = "spring")
public interface SongMapper {
    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);
    @Mapping(source = "name",target = "name")
    @Mapping(source = "artist.name", target = "artist")
    @Mapping(source = "album.name",target = "album")
    SongDTO songToDTO(Song song);

}
