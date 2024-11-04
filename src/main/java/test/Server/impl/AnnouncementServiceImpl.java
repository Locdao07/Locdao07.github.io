package test.Server.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.Repository.AnnouncementRepository;
import test.entity.Announcement;
import test.entity.AnnouncementDto;
import test.service.AnnouncementService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public List<AnnouncementDto> getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAllOrderByDateDesc();
        return announcements.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void saveAnnouncement(AnnouncementDto announcementDto) {
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDto.getTitle());
        announcement.setContent(announcementDto.getContent());
        announcement.setDate(announcementDto.getDate());
        announcementRepository.save(announcement);
    }
    @Override
    public AnnouncementDto getAnnouncementById(Long id) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);
        if (announcement != null) {
            return convertToDto(announcement);
        }
        return null;
    }

    @Override
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    private AnnouncementDto convertToDto(Announcement announcement) {
        AnnouncementDto announcementDto = new AnnouncementDto();
        announcementDto.setId(announcement.getId());
        announcementDto.setTitle(announcement.getTitle());
        announcementDto.setContent(announcement.getContent());
        announcementDto.setDate(announcement.getDate());
        return announcementDto;
    }
}
