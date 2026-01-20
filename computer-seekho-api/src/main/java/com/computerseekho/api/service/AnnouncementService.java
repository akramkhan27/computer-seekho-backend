package com.computerseekho.api.service;

import org.springframework.stereotype.Service;

import java.util.List;

public interface AnnouncementService {

    List<String> getActiveAnnouncementTexts();
}
