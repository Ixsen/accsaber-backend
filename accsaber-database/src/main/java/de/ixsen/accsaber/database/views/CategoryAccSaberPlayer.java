package de.ixsen.accsaber.database.views;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;

@Entity
@Immutable
public class CategoryAccSaberPlayer extends AccSaberPlayer {

    private String categoryName;

    public String getCategoryName() {
        return this.categoryName;
    }
}
