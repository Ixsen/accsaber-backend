package de.ixsen.accsaber.database.model.players;

import de.ixsen.accsaber.database.model.maps.BeatMap;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import java.time.Instant;

@Entity
public class ScoreData {

    @Id
    private Long scoreId;

    private int rankWhenScoresSet;

    private int score;

    private int unmodififiedScore;

    private Double accuracy;

    private Double ap;

    private Double weightedAp;

    @OrderBy
    private Instant timeSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false, name = "map_leaderboard_id", foreignKey = @ForeignKey(name = "none"))
    private BeatMap beatMap;

    @Column(name = "map_leaderboard_id")
    private Long leaderboardId;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerData player;

    @ColumnDefault("false")
    private boolean isRankedScore;

    public Long getScoreId() {
        return this.scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public int getRankWhenScoresSet() {
        return this.rankWhenScoresSet;
    }

    public void setRankWhenScoresSet(int rankWhenScoresSet) {
        this.rankWhenScoresSet = rankWhenScoresSet;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUnmodififiedScore() {
        return this.unmodififiedScore;
    }

    public void setUnmodififiedScore(int unmodififiedScore) {
        this.unmodififiedScore = unmodififiedScore;
    }

    public Double getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Instant getTimeSet() {
        return this.timeSet;
    }

    public void setTimeSet(Instant timeSet) {
        this.timeSet = timeSet;
    }

    public BeatMap getBeatMap() {
        return this.beatMap;
    }

    public void setBeatMap(BeatMap beatMap) {
        this.beatMap = beatMap;
    }

    public Long getLeaderboardId() {
        return this.leaderboardId;
    }

    public void setLeaderboardId(Long leaderboardId) {
        this.leaderboardId = leaderboardId;
    }

    public Double getAp() {
        return this.ap;
    }

    public void setAp(Double ap) {
        this.ap = ap;
    }

    public PlayerData getPlayer() {
        return this.player;
    }

    public void setPlayer(PlayerData player) {
        this.player = player;
    }

    public boolean isRankedScore() {
        return this.isRankedScore;
    }

    public void setRankedScore(boolean rankedScore) {
        this.isRankedScore = rankedScore;
    }

    public Double getWeightedAp() {
        return this.weightedAp;
    }

    public void setWeightedAp(Double weightedAp) {
        this.weightedAp = weightedAp;
    }


}
