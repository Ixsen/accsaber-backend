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
import de.ixsen.accsaber.database.model.maps.Song;
import de.ixsen.accsaber.database.model.players.ScoreData;
import de.ixsen.accsaber.database.repositories.model.BeatMapRepository;
import de.ixsen.accsaber.database.repositories.model.CategoryRepository;
import de.ixsen.accsaber.database.repositories.model.ScoreDataRepository;
import de.ixsen.accsaber.integration.connector.BeatSaverConnector;
import de.ixsen.accsaber.integration.model.beatsaver.BeatSaverMapDifficulty;
import de.ixsen.accsaber.integration.model.beatsaver.BeatSaverSongInfo;
import de.ixsen.accsaber.integration.model.beatsaver.BeatSaverVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class RankedMapService {

    private final BeatSaverConnector beatSaverConnector;
    private final BeatMapRepository beatMapRepository;
    private final ScoreDataRepository scoreDataRepository;

    private final SongService songService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public RankedMapService(BeatMapRepository beatMapRepository,
                            ScoreDataRepository scoreDataRepository,
                            CategoryRepository categoryRepository,
                            BeatSaverConnector beatSaverConnector,
                            SongService songService) {
        this.beatMapRepository = beatMapRepository;
        this.scoreDataRepository = scoreDataRepository;
        this.categoryRepository = categoryRepository;
        this.beatSaverConnector = beatSaverConnector;
        this.songService = songService;
    }

    public List<BeatMap> getRankedMaps() {
        return this.beatMapRepository.findAll();
    }

    public BeatMap getRankedMap(Long leaderboardId) {
        Optional<BeatMap> optionalRankedMap = this.beatMapRepository.findById(leaderboardId);
        if (optionalRankedMap.isEmpty()) {
            throw new AccsaberOperationException(ExceptionType.RANKED_MAP_NOT_FOUND, "The ranked map with the id could not be found.");
        }
        return optionalRankedMap.get();
    }

    public void addNewRankedMap(String songHash, Long leaderBoardId, String difficulty, Double complexity, String categoryName) {
        Optional<Category> optionalCategory = this.categoryRepository.findByCategoryName(categoryName);
        if (optionalCategory.isEmpty()) {
            throw new AccsaberOperationException(ExceptionType.CATEGORY_NOT_FOUND, String.format("The category [%s] was not found.", categoryName));
        }
        Category category = optionalCategory.get();

        BeatSaverSongInfo beatSaverSongInfo = this.beatSaverConnector.getMapInfoByHash(songHash);

        if (this.beatMapRepository.existsById(leaderBoardId)) {
            throw new AccsaberOperationException(ExceptionType.RANKED_MAP_ALREADY_EXISTS, "The ranked map with the leaderboardId " + leaderBoardId + " already exists");
        }

        BeatSaverVersion beatSaverVersion = beatSaverSongInfo.getVersions().stream()
                .filter(version -> songHash.equalsIgnoreCase(version.getHash()))
                .findAny()
                .orElseThrow(()-> new AccsaberOperationException(ExceptionType.RANKED_MAP_NOT_FOUND, String.format("BeatSaver did not return requested map with hash %s", songHash)));

        Song song = this.songService.getOrCreateSong(beatSaverSongInfo, beatSaverVersion);

        BeatSaverMapDifficulty beatSaverMapDifficulty = beatSaverVersion.getDiffs().stream()
                .filter(diff -> difficulty.equalsIgnoreCase(diff.getDifficulty()) && "Standard".equalsIgnoreCase(diff.getCharacteristic()))
                .findAny().orElseThrow(() -> new AccsaberOperationException(ExceptionType.RANKED_MAP_NOT_FOUND, String.format("Map with hash %s does not have [%s] difficulty as Standard characteristic", songHash, difficulty)));

        BeatMap beatMap = new BeatMap();
        beatMap.setLeaderboardId(leaderBoardId);
        beatMap.setMaxScore(this.calculateMaxScore(beatSaverMapDifficulty.getNotes()));
        beatMap.setSong(song);
        beatMap.setComplexity(complexity);
        beatMap.setDifficulty(difficulty);
        beatMap.setCategory(category);

        song.getBeatMaps().add(beatMap);

        song = this.songService.saveSong(song);

        this.scoreDataRepository.rankScores(leaderBoardId, beatMap.getMaxScore(), complexity);
    }

    public void removeRankedMap(Long leaderboardId) {
        Optional<BeatMap> map = this.beatMapRepository.findById(leaderboardId);
        if (map.isEmpty()) {
            throw new AccsaberOperationException(ExceptionType.RANKED_MAP_NOT_FOUND,
                    "The ranked map [" + leaderboardId + "] does not currently exist.");
        }

        BeatMap beatMap = map.get();
        Song song = beatMap.getSong();
        song.getBeatMaps().remove(beatMap);
        beatMap.setSong(null);
        if (song.getBeatMaps().size() == 0) {
            this.songService.removeSong(song.getSongHash());
        } else {
            this.songService.saveSong(song);
        }

        List<ScoreData> unrankedScores = this.scoreDataRepository.findAllByLeaderboardId(leaderboardId);
        unrankedScores.forEach(score -> score.setRankedScore(false));
        this.scoreDataRepository.saveAll(unrankedScores);
    }

    public byte[] getRankedMapsJson() throws JsonProcessingException {
        Playlist playlist = new Playlist();
        playlist.setPlaylistTitle("AccSaber Ranked Maps");
        playlist.setPlaylistAuthor("AccSaber");
        playlist.setImage(this.getPlaylistImage());

        for (BeatMap beatMap : this.getRankedMaps()) {
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

    // There has to be a better way to do this
    private int calculateMaxScore(int noteCount) {
        int score = 0;
        for (int i = 1; i <= noteCount; i++) {

            if (i <= 1) {
                score += 1;
            } else if (i <= 5) {
                score += 2;
            } else if (i <= 13) {
                score += 4;
            } else {
                break;
            }
        }
        score *= 115;
        if (noteCount > 13) {
            score += (8 * (noteCount - 13)) * 115;
        }

        return score;
    }


}
