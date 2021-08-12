package de.ixsen.accsaber.api.dtos;

public class MapLeaderboardPlayerDto {
    private int rank;

    private String playerId;

    private String playerName;
    private String avatarUrl;

    private boolean isAccChamp;

    private double accuracy;
    private int score;
    private double ap;

    private String timeSet;

    private String categoryName;

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isAccChamp() {
        return this.isAccChamp;
    }

    public void setAccChamp(boolean accChamp) {
        this.isAccChamp = accChamp;
    }

    public double getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getAp() {
        return this.ap;
    }

    public void setAp(double ap) {
        this.ap = ap;
    }

    public String getTimeSet() {
        return this.timeSet;
    }

    public void setTimeSet(String timeSet) {
        this.timeSet = timeSet;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
