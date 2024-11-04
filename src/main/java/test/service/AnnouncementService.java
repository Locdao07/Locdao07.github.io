package test.service;


import java.util.List;

import test.entity.AnnouncementDto;

public interface AnnouncementService {
	  List<AnnouncementDto> getAllAnnouncements();
	    void saveAnnouncement(AnnouncementDto announcementDto);
	    void deleteAnnouncement(Long id);
	    AnnouncementDto getAnnouncementById(Long id);
}
