package de.ixsen.accsaber.api.mapping;

import de.ixsen.accsaber.api.dtos.PlayerScoreDto;
import de.ixsen.accsaber.database.views.AccSaberScore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ScoreMapper {
    ScoreMapper INSTANCE = Mappers.getMapper(ScoreMapper.class);

    @Mapping(target = "songHash", source = "beatMap.song.songHash")
    @Mapping(target = "complexity", source = "beatMap.complexity")
    @Mapping(target = "songName", source = "beatMap.song.songName")
    @Mapping(target = "songAuthorName", source = "beatMap.song.songAuthorName")
    @Mapping(target = "rank", source = "ranking")
    @Mapping(target = "levelAuthorName", source = "beatMap.song.levelAuthorName")
    @Mapping(target = "difficulty", source = "beatMap.difficulty")
    @Mapping(target = "beatsaverKey", source = "beatMap.song.beatSaverKey")
    @Mapping(target = "categoryDisplayName", source = "beatMap.category.categoryDisplayName")
    PlayerScoreDto rankedScoreToPlayerScore(AccSaberScore accSaberScore);

    ArrayList<PlayerScoreDto> rankedScoresToPlayerScores(List<AccSaberScore> accSaberScores);
}
