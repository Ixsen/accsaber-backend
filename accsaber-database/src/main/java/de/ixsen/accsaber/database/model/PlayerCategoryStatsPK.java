package de.ixsen.accsaber.database.model;

import java.io.Serializable;
import java.util.Objects;

public class PlayerCategoryStatsPK implements Serializable {

    protected Long player;
    protected Long category;

    public PlayerCategoryStatsPK() {
    }

    public PlayerCategoryStatsPK(Long player, Long category) {
        this.player = player;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        PlayerCategoryStatsPK that = (PlayerCategoryStatsPK) o;
        return this.player.equals(that.player) && this.category.equals(that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.player, this.category);
    }
}
