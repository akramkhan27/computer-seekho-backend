package com.computerseekho.api.controller;
import com.computerseekho.api.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements") // React URL
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/active")
    public List<String> getActiveAnnouncements() {
        return announcementService.getActiveAnnouncementTexts();
    }
}
