package de.ixsen.accsaber.api.mapping;


import de.ixsen.accsaber.api.dtos.MapLeaderboardPlayerDto;
import de.ixsen.accsaber.database.views.AccSaberScore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MapLeaderboardPlayerMapper {
    MapLeaderboardPlayerMapper INSTANCE = Mappers.getMapper(MapLeaderboardPlayerMapper.class);

    @Mapping(target = "categoryName", source = "beatMap.category.categoryName")
    @Mapping(target = "rank", source = "ranking")
    @Mapping(target = "playerName", source = "player.playerName")
    @Mapping(target = "playerId", source = "player.playerId")
    @Mapping(target = "avatarUrl", source = "player.avatarUrl")
    @Mapping(target = "accChamp", source = "player.accChamp")
    MapLeaderboardPlayerDto rankedScoreToMapLeaderboardDto(AccSaberScore score);

    List<MapLeaderboardPlayerDto> rankedScoresToMapLeaderboardDtos(List<AccSaberScore> score);
}
