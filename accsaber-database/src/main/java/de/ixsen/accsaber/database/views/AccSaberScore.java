package de.ixsen.accsaber.database.views;


import de.ixsen.accsaber.database.model.maps.BeatMap;
import de.ixsen.accsaber.database.model.players.PlayerData;
import de.ixsen.accsaber.database.views.extenders.WithRankAndWeightedAp;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import java.time.Instant;

@Entity
@Immutable
public class AccSaberScore extends WithRankAndWeightedAp {

    @Id
    private Long scoreId;
    private int rankWhenScoresSet;
    private int score;
    private int unmodififiedScore;
    private Double accuracy;

    @OrderBy
    private Instant timeSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false, name = "map_leaderboard_id")
    private BeatMap beatMap;

    @Column(name = "map_leaderboard_id")
    private Long leaderboardId;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerData player;

    public Long getScoreId() {
        return this.scoreId;
    }

    public int getRankWhenScoresSet() {
        return this.rankWhenScoresSet;
    }

    public int getScore() {
        return this.score;
    }

    public int getUnmodififiedScore() {
        return this.unmodififiedScore;
    }

    public Double getAccuracy() {
        return this.accuracy;
    }

    public Instant getTimeSet() {
        return this.timeSet;
    }

    public BeatMap getBeatMap() {
        return this.beatMap;
    }

    public Long getLeaderboardId() {
        return this.leaderboardId;
    }

    public PlayerData getPlayer() {
        return this.player;
    }
}
