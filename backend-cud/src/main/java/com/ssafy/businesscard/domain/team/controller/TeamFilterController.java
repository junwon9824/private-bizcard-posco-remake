package com.ssafy.businesscard.domain.team.controller;

import com.ssafy.businesscard.domain.card.dto.request.FilterRequest;
import com.ssafy.businesscard.domain.team.service.TeamFilterService;
import com.ssafy.businesscard.global.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamFilterController {

    private final TeamFilterService teamFilterService;

    // 필터 생성
    @PostMapping("/{teamId}/filter")
    public ResponseEntity<MessageUtils> create(@PathVariable("teamId") Long teamAlbumId,
                                               @RequestBody FilterRequest request) {
        teamFilterService.create(teamAlbumId, request);
        log.info("[Create Filter] : {}", request.filterName());
        return ResponseEntity.ok().body(MessageUtils.success("필터가 생성되었습니다."));
    }

    // 필터 이름 편집
    @PatchMapping("/{teamId}/filter/{filterId}")
    public ResponseEntity<MessageUtils> update(@PathVariable("teamId") Long teamAlbumId,
                                               @PathVariable("filterId") Long filterId,
                                               @RequestBody FilterRequest request) {
        teamFilterService.update(teamAlbumId, filterId, request);
        log.info("[Update Filter] : {}", request);
        return ResponseEntity.ok().body(MessageUtils.success("필터가 수정되었습니다."));
    }

    // 필터 삭제
    @DeleteMapping("/{teamId}/filter/{filterId}")
    public ResponseEntity<MessageUtils> delete(@PathVariable("teamId") Long teamAlbumId,
                                               @PathVariable("filterId") Long filterId) {
        teamFilterService.delete(teamAlbumId, filterId);
        log.info("[Delete Filter] : {}",filterId);
        return ResponseEntity.ok().body(MessageUtils.success("필터가 삭제되었습니다."));
    }

}
