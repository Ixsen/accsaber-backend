package de.ixsen.accsaber.database.repositories.model;

import de.ixsen.accsaber.database.model.players.PlayerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerDataRepository extends JpaRepository<PlayerData, Long> {

    @Query("select player.playerId from PlayerData player")
    List<Long> findAllPlayerIds();

    @Procedure(procedureName = "recalc_all_ap")
    void recalculateAllAp();

    @Procedure(procedureName = "recalc_player_category_stat")
    void recalculatePlayerCategoryStat(Long playerId, Long categoryId);

    @Procedure(procedureName = "recalc_player_category_stats")
    void recalculatePlayerCategoryStats(Long playerId);

    @Procedure(procedureName = "recalc_player_ap")
    void recalcPlayerAp(Long playerId);
}
