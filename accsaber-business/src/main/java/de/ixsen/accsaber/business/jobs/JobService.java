package de.ixsen.accsaber.business.jobs;

import de.ixsen.accsaber.business.HasLogger;
import de.ixsen.accsaber.business.PlayerService;
import de.ixsen.accsaber.business.SongService;
import de.ixsen.accsaber.database.model.maps.BeatMap;
import de.ixsen.accsaber.database.repositories.model.BeatMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@EnableScheduling
public class JobService implements HasLogger {

    private final PlayerService playerService;
    private final boolean disableScoreFetching;
    private final boolean disableAvatarFetching;
    private final boolean recalculateApOnStartup;
    private final boolean reloadSongCoversOnStartup;
    private final BeatMapRepository beatMapRepository;
    private final SongService songService;


    @Autowired
    public JobService(PlayerService playerService,
                      SongService songService,
                      BeatMapRepository beatMapRepository,
                      @Value("${accsaber.disable-score-fetching}") boolean disableScoreFetching,
                      @Value("${accsaber.disable-avatar-fetching}") boolean disableAvatarFetching,
                      @Value("${accsaber.recalculate-ap-on-startup}") boolean recalculateApOnStartup,
                      @Value("${accsaber.reload-song-covers-on-startup}") boolean reloadSongCoversOnStartup) {
        this.playerService = playerService;
        this.songService = songService;
        this.beatMapRepository = beatMapRepository;
        this.disableScoreFetching = disableScoreFetching;
        this.disableAvatarFetching = disableAvatarFetching;
        this.recalculateApOnStartup = recalculateApOnStartup;
        this.reloadSongCoversOnStartup = reloadSongCoversOnStartup;
    }

    @PostConstruct
    public void onStartUpDone() {
        if (this.recalculateApOnStartup) {
            this.getLogger().info("Recalculating all AP values...");
            this.playerService.recalculateApForAllPlayers();
        }
        if (this.reloadSongCoversOnStartup) {
            this.songService.reloadAllCovers();
        }
    }

    @Scheduled(fixedDelayString = "${accsaber.score-fetch-intervall-millis}")
    public void loadPlayerScores() {
        if (this.disableScoreFetching) {
            this.getLogger().warn("Score fetching is disabled.");
        } else {
            List<Long> allPlayerIds = this.playerService.loadAllPlayerIds();

            Instant start = Instant.now();
            this.getLogger().info("Loading scores for " + allPlayerIds.size() + " players.");
            List<BeatMap> allRankedMaps = this.beatMapRepository.findAll();

            for (Long playerId : allPlayerIds) {
                this.playerService.handlePlayer(allRankedMaps, playerId);
            }
            this.getLogger().info("Loading scores finished in {} seconds.", Duration.between(start, Instant.now()).getSeconds());
        }
    }

    @Scheduled(fixedDelayString = "${accsaber.avatar-fetch-intervall-millis}")
    public void loadAvatars() {
        if (this.disableAvatarFetching) {
            this.getLogger().warn("Avatar fetching is disabled.");
        } else {
            this.playerService.loadAvatars();
        }
    }
}
