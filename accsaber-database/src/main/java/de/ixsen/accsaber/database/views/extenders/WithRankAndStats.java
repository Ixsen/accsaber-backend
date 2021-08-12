package de.ixsen.accsaber.database.views.extenders;

import org.hibernate.annotations.Immutable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Immutable
public abstract class WithRankAndStats extends WithRank {

    private Double averageAcc;
    private Double averageApPerMap;
    private int rankedPlays;

    public Double getAverageApPerMap() {
        return this.averageApPerMap;
    }

    public int getRankedPlays() {
        return this.rankedPlays;
    }

    public Double getAverageAcc() {
        return this.averageAcc;
    }
}
