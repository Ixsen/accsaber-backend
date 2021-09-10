package de.ixsen.accsaber.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ixsen.accsaber.business.exceptions.AccsaberOperationException;
import de.ixsen.accsaber.business.exceptions.ExceptionType;
import de.ixsen.accsaber.business.playlist.Playlist;
import de.ixsen.accsaber.business.playlist.PlaylistSong;
import de.ixsen.accsaber.business.playlist.PlaylistSongDifficulty;
import de.ixsen.accsaber.database.model.Category;
import de.ixsen.accsaber.database.model.maps.BeatMap;
import de.ixsen.accsaber.database.repositories.model.BeatMapRepository;
import de.ixsen.accsaber.database.repositories.model.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class PlaylistService {

    private final BeatMapRepository beatMapRepository;
    private final CategoryRepository categoryRepository;
    private final String playlistUrl;

    @Autowired
    public PlaylistService(BeatMapRepository beatMapRepository,
                           CategoryRepository categoryRepository,
                           @Value("${accsaber.playlist-url}") String playlistUrl) {
        this.beatMapRepository = beatMapRepository;
        this.categoryRepository = categoryRepository;
        this.playlistUrl = playlistUrl;
    }

    public byte[] getPlaylist(String categoryName) throws JsonProcessingException {
        if (categoryName.equals("all")) {
            return this.getRankedMapsJson(this.beatMapRepository.findAll(), categoryName, categoryName);
        }
        if (categoryName.equals("overall")) {
            return this.getRankedMapsJson(this.beatMapRepository.findAllCountingTowardsOverall(), categoryName, categoryName);
        }
        Category category = this.categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new AccsaberOperationException(ExceptionType.CATEGORY_NOT_FOUND, String.format("The category [%s] was not found.", categoryName)));

        return this.getRankedMapsJson(this.beatMapRepository.findAllForCategory(category), categoryName, category.getCategoryDisplayName());
    }

    private byte[] getRankedMapsJson(List<BeatMap> maps, String category, String categoryName) throws JsonProcessingException {
        Playlist playlist = new Playlist();
        playlist.setSyncURL(this.playlistUrl + category);
        playlist.setPlaylistTitle(String.format("AccSaber %s Ranked Maps", StringUtils.capitalize(categoryName)));
        playlist.setPlaylistAuthor("AccSaber");
        playlist.setImage(this.getPlaylistImage());

        for (BeatMap beatMap : maps) {
            PlaylistSong playlistSong = playlist.getSongs().stream().filter(s -> s.getHash().equals(beatMap.getSong().getSongHash())).findFirst().orElseGet(() -> {
                PlaylistSong newPlaylistSong = new PlaylistSong();
                newPlaylistSong.setHash(beatMap.getSong().getSongHash());
                newPlaylistSong.setSongName(beatMap.getSong().getSongName());

                playlist.getSongs().add(newPlaylistSong);
                return newPlaylistSong;
            });
            PlaylistSongDifficulty playlistSongDifficulty = new PlaylistSongDifficulty();
            playlistSongDifficulty.setName(beatMap.getDifficulty());
            playlistSongDifficulty.setCharacteristic("Standard");
            playlistSong.getDifficulties().add(playlistSongDifficulty);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(playlist);
    }

    private String getPlaylistImage() {
        ClassPathResource resource = new ClassPathResource("logo-data");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e); // TODO
        }
    }
}
