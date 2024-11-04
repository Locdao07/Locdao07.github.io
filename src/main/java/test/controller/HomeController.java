package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import test.entity.AnnouncementDto;
import test.service.AnnouncementService;

import java.util.Date;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("announcements", announcementService.getAllAnnouncements());
        return "home";
    }

    @GetMapping("/announcements/new")
    public String newAnnouncement(Model model) {
        model.addAttribute("announcement", new AnnouncementDto());
        return "announcement-form";
    }

    @PostMapping("/announcements")
    public String saveAnnouncement(@ModelAttribute("announcement") AnnouncementDto announcementDto) {
        announcementDto.setDate(new Date()); // Set the current date
        announcementService.saveAnnouncement(announcementDto);
        return "redirect:/";
    }

    @GetMapping("/announcements/delete/{id}")
    public String deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return "redirect:/";
    }
    @GetMapping("/announcements/{id}")
    public String viewAnnouncement(@PathVariable Long id, Model model) {
        AnnouncementDto announcement = announcementService.getAnnouncementById(id);
        model.addAttribute("announcement", announcement);
        return "announcement-detail";
    }
}
