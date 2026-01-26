package com.computerseekho.api.controller;

import com.computerseekho.api.dto.response.AlbumResponse;
import com.computerseekho.api.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    /**
     * GET /api/albums/campus-life
     */
    @GetMapping("/campus-life")
    public ResponseEntity<List<AlbumResponse>> getCampusLifeAlbums() {
        return ResponseEntity.ok(albumService.getCampusLifeAlbums());
    }
}

