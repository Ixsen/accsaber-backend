package de.ixsen.accsaber.database.views;

import de.ixsen.accsaber.database.views.extenders.WithRankAndStats;
import org.hibernate.annotations.Immutable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Immutable
public abstract class AccSaberPlayer extends WithRankAndStats {

    @Id
    private String playerId;
    private String playerName;
    private String avatarUrl;
    private Boolean isAccChamp;
    private String hmd;

    public String getPlayerId() {
        return this.playerId;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public Boolean getAccChamp() {
        return this.isAccChamp;
    }

    public String getHmd() {
        return this.hmd;
    }
}
