package de.ixsen.accsaber.database.views.extenders;

import org.hibernate.annotations.Immutable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Immutable
public abstract class WithRank {
    private Long ranking;
    private Double ap;

    public Long getRanking() {
        return this.ranking;
    }

    public Double getAp() {
        return this.ap;
    }
}
