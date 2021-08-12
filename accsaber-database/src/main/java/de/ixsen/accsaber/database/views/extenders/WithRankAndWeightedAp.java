package de.ixsen.accsaber.database.views.extenders;

import org.hibernate.annotations.Immutable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Immutable
public abstract class WithRankAndWeightedAp extends WithRank {

    private Double weightedAp;

    public Double getWeightedAp() {
        return this.weightedAp;
    }
}
