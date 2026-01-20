package com.computerseekho.api.service.impl;

import com.computerseekho.api.repository.AnnouncementRepository;
import com.computerseekho.api.service.AnnouncementService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementServiceImp implements AnnouncementService
{
    private final AnnouncementRepository announcementRepository;

    public AnnouncementServiceImp(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }


    @Override
    public List<String> getActiveAnnouncementTexts() {
        return announcementRepository.findActiveAnnouncementTexts(LocalDateTime.now());

    }
}
