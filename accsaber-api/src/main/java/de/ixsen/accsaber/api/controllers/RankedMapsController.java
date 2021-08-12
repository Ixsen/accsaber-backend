package de.ixsen.accsaber.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.ixsen.accsaber.api.dtos.CreateRankedMapDto;
import de.ixsen.accsaber.api.dtos.RankedMapDto;
import de.ixsen.accsaber.api.dtos.RankedMapsStatisticsDto;
import de.ixsen.accsaber.api.mapping.MappingComponent;
import de.ixsen.accsaber.business.PlayerService;
import de.ixsen.accsaber.business.RankedMapService;
import de.ixsen.accsaber.database.model.maps.BeatMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ranked-maps")
public class RankedMapsController {

    private final RankedMapService rankedMapService;
    private final PlayerService playerService;
    private final MappingComponent mappingComponent;

    @Autowired
    public RankedMapsController(RankedMapService rankedMapService, PlayerService playerService, MappingComponent mappingComponent) {
        this.rankedMapService = rankedMapService;
        this.playerService = playerService;

        this.mappingComponent = mappingComponent;
    }

    @PostMapping
    public ResponseEntity<?> addNewRankedMap(@RequestBody CreateRankedMapDto rankedMapDto) {
        this.rankedMapService.addNewRankedMap(rankedMapDto.getSongHash(), rankedMapDto.getLeaderboardId(), rankedMapDto.getDifficulty(), rankedMapDto.getComplexity(), rankedMapDto.getCategoryName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RankedMapDto>> getAllRankedMaps() {
        List<RankedMapDto> rankedMapDtos = this.mappingComponent
                .getRankedMapMapper().rankedMapsToDtos(this.rankedMapService.getRankedMaps());
        return ResponseEntity.ok(rankedMapDtos);
    }

    @DeleteMapping("/{leaderboardId}")
    public ResponseEntity<?> removeRankedMap(@PathVariable Long leaderboardId) {
        this.rankedMapService.removeRankedMap(leaderboardId);
        this.playerService.recalculateApForAllPlayers();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<RankedMapsStatisticsDto> getRankedStat() {
        List<BeatMap> beatMaps = this.rankedMapService.getRankedMaps();
        long trueAccCount = beatMaps.stream().filter(r -> r.getComplexity() <= 4).count();
        long techAccCount = beatMaps.stream().filter(r -> r.getComplexity() >= 10).count();
        RankedMapsStatisticsDto rankedMapsStatisticsDto = new RankedMapsStatisticsDto();
        rankedMapsStatisticsDto.setMapCount(beatMaps.size());
        rankedMapsStatisticsDto.setTechAccMapCount(techAccCount);
        rankedMapsStatisticsDto.setTrueAccMapCount(trueAccCount);
        rankedMapsStatisticsDto.setStandardAccMapCount(beatMaps.size() - trueAccCount - techAccCount);
        Map<Double, Long> complexityToMapCount = beatMaps
                .stream()
                .collect(Collectors.groupingBy(BeatMap::getComplexity, Collectors.counting()));
        rankedMapsStatisticsDto.setComplexityToMapCount(complexityToMapCount);

        return ResponseEntity.ok(rankedMapsStatisticsDto);
    }

    @GetMapping("/{leaderboardId}")
    public ResponseEntity<RankedMapDto> getRankedMapDetails(@PathVariable Long leaderboardId) {
        BeatMap beatMap = this.rankedMapService.getRankedMap(leaderboardId);
        RankedMapDto rankedMapDto = this.mappingComponent.getRankedMapMapper().rankedMapToDto(beatMap);
        return ResponseEntity.ok(rankedMapDto);
    }

    @GetMapping("/playlist")
    public ResponseEntity<byte[]> getRankedMapPlaylist() throws JsonProcessingException {
        byte[] playlistJson = this.rankedMapService.getRankedMapsJson();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=accsaber-rankedmaps.json")
                .body(playlistJson);
    }
}
