package de.ixsen.accsaber.api.dtos;

public class PlayerScoreDto {
    private long rank;
    private double ap;
    private double weightedAp;
    private long score;
    private double accuracy;

    private String songName;
    private String songAuthorName;
    private String levelAuthorName;
    private double complexity;
    private String songHash;

    private String difficulty;
    private String leaderboardId;
    private String beatsaverKey;

    private String timeSet;

    private String categoryDisplayName;

    public long getRank() {
        return this.rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public double getAp() {
        return this.ap;
    }

    public void setAp(double ap) {
        this.ap = ap;
    }

    public double getWeightedAp() {
        return this.weightedAp;
    }

    public void setWeightedAp(double weightedAp) {
        this.weightedAp = weightedAp;
    }

    public long getScore() {
        return this.score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public double getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongAuthorName() {
        return this.songAuthorName;
    }

    public void setSongAuthorName(String songAuthorName) {
        this.songAuthorName = songAuthorName;
    }

    public String getLevelAuthorName() {
        return this.levelAuthorName;
    }

    public void setLevelAuthorName(String levelAuthorName) {
        this.levelAuthorName = levelAuthorName;
    }

    public double getComplexity() {
        return this.complexity;
    }

    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getLeaderboardId() {
        return this.leaderboardId;
    }

    public void setLeaderboardId(String leaderboardId) {
        this.leaderboardId = leaderboardId;
    }

    public String getBeatsaverKey() {
        return this.beatsaverKey;
    }

    public void setBeatsaverKey(String beatsaverKey) {
        this.beatsaverKey = beatsaverKey;
    }

    public String getTimeSet() {
        return this.timeSet;
    }

    public void setTimeSet(String timeSet) {
        this.timeSet = timeSet;
    }

    public String getSongHash() {
        return this.songHash;
    }

    public void setSongHash(String songHash) {
        this.songHash = songHash;
    }

    public String getCategoryDisplayName() {
        return this.categoryDisplayName;
    }

    public void setCategoryDisplayName(String categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
    }
}
