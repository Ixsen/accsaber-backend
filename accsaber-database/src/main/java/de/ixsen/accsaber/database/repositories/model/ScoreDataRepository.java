package de.ixsen.accsaber.database.repositories.model;

import de.ixsen.accsaber.database.model.players.PlayerData;
import de.ixsen.accsaber.database.model.players.ScoreData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreDataRepository extends JpaRepository<ScoreData, Long> {
    List<ScoreData> findAllByLeaderboardId(Long leaderboardId);

    @Query("from ScoreData scoreData join fetch BeatMap beatMap on scoreData.beatMap = beatMap")
    List<ScoreData> findAllRankedScores();

    Optional<ScoreData> findByPlayerAndLeaderboardId(PlayerData player, long leaderboardId);

    @Procedure(procedureName = "rank_scores")
    void rankScores(long leaderboard_id, long maxScore, double complexity);
}
