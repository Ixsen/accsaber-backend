package de.ixsen.accsaber.business;

import de.ixsen.accsaber.database.model.players.PlayerData;
import de.ixsen.accsaber.database.model.players.ScoreData;
import de.ixsen.accsaber.database.repositories.model.BeatMapRepository;
import de.ixsen.accsaber.database.repositories.view.AccSaberScoreRepository;
import de.ixsen.accsaber.database.views.AccSaberScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ScoreService implements HasLogger {

    private final AccSaberScoreRepository accSaberScoreRepository;

    @Autowired
    public ScoreService(AccSaberScoreRepository accSaberScoreRepository, BeatMapRepository beatMapRepository) {
        this.accSaberScoreRepository = accSaberScoreRepository;
    }

    public List<AccSaberScore> getScoresForLeaderboardId(Long leaderboardId) {
        return this.accSaberScoreRepository.findAllByLeaderboardId(leaderboardId);
    }

    public List<AccSaberScore> getScoresForPlayer(PlayerData player) {
        return this.accSaberScoreRepository.findAllByPlayerOrderByApDesc(player);
    }

    public List<ScoreData> getScoreHistoryForPlayer(PlayerData player, long leaderboardId) {
        // FIXME
        return Collections.emptyList();
//        return this.scoreDataRepository
//                .findByPlayerAndLeaderboardId(player, leaderboardId)
//                .map(score -> this.scoreDataRepository.findRevisions(score.getScoreId())
//                        .get()
//                        .map(Revision::getEntity)
//                        .collect(Collectors.toList()))
//                .orElse(Collections.emptyList());
    }
}
