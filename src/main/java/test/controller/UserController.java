package test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import test.entity.AnnouncementDto;
import test.entity.AppUser;
import test.entity.MessageDTO;
import test.entity.PasswordChangeDTO;
import test.entity.Registration;
import test.entity.Student;
import test.entity.Studentdto;
import test.entity.Subject;
import test.service.AnnouncementService;
import test.service.MessageService;
import test.service.RegistrationService;
import test.service.StudentService;
import test.service.SubjectService;
import test.service.UserService;

@Controller
@RequestMapping("/userInfo")
public class UserController {
	@Autowired
    private SubjectService subjectService;
	
	 @Autowired
	 private StudentService studentService;
	 @Autowired
	    private UserService userService;
	 @Autowired
	    private MessageService messageService;

    @Autowired
    private AnnouncementService announcementService;
    private RegistrationService registrationService;

    @Autowired
    public UserController(SubjectService subjectService, RegistrationService registrationService,AnnouncementService announcementService,UserService userService) {
        this.subjectService = subjectService;
        this.registrationService = registrationService;
        this.announcementService = announcementService;
        this.userService = userService;
    }

    @GetMapping("/announcements")
    public String viewAnnouncements(Model model) {
        model.addAttribute("announcements", announcementService.getAllAnnouncements());
        return "user/announcements";
    }
    @GetMapping("/registrations/{subjectId}")
    public String viewRegistrationsForSubject(@PathVariable Long subjectId, Model model) {
        model.addAttribute("subject", subjectService.getSubjectById(subjectId));
        model.addAttribute("registrations", registrationService.getRegistrationsBySubject(subjectId));
        return "user/registrations-by-subject";
    }
    @GetMapping("/subject-list")
    public String viewSubjects(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "user/subject-list";
    }

    @GetMapping("/registered-subjects")
    public String viewRegisteredSubjects(Model model) {
        String email = getCurrentUserEmail();
        List<Registration> registrations = studentService.getRegistrationsByStudentEmail(email);
        model.addAttribute("registrations", registrations);
        return "user/registered-subjects";
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    @GetMapping("/announcements/{id}")
    public String viewAnnouncement(@PathVariable Long id, Model model) {
        AnnouncementDto announcement = announcementService.getAnnouncementById(id);
        model.addAttribute("announcement", announcement);
        return "user/announcement-detail";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("passwordChangeDTO", new PasswordChangeDTO());
        return "user/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute PasswordChangeDTO passwordChangeDTO, Authentication authentication, Model model) {
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);

        if (userService.changePassword(user.getUserId(), passwordChangeDTO)) {
            model.addAttribute("message", "Password changed successfully. Please log in with your new password.");
            return "redirect:/login?passwordChanged=true";
        } else {
            model.addAttribute("error", "Old password is incorrect.");
            return "user/change-password";
        }
    }

    @GetMapping("/messages")
    public String viewMessages(Model model) {
        String email = getCurrentUserEmail();
        Studentdto student = studentService.getStudentByEmail(email);
        List<MessageDTO> messages = messageService.getMessagesForStudent(student.getId());
        List<MessageDTO> receivedMessages = messageService.getReceivedMessagesByStudent(student.getId());
        List<MessageDTO> sentMessages = messageService.getSentMessagesByStudent(student.getId());
        List<MessageDTO> deletedMessages = messageService.getDeletedMessagesByStudent(student.getId());

        model.addAttribute("messages", messages);
        model.addAttribute("receivedMessages", receivedMessages);
        model.addAttribute("sentMessages", sentMessages);
        model.addAttribute("deletedMessages", deletedMessages);
        model.addAttribute("newMessage", new MessageDTO());
       // model.addAttribute("unreadMessagesCount", unreadMessagesCount);

        return "user/messages";
    }


    @PostMapping("/messages/send")
    public String sendMessage(@Valid @ModelAttribute("newMessage") MessageDTO messageDTO, BindingResult bindingResult, Model model) {
        String email = getCurrentUserEmail();
        Studentdto sender = studentService.getStudentByEmail(email);

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Content cannot be blank");
            return "user/messages";
        }

        if (sender == null) {
            model.addAttribute("error", "Sender not found.");
            return "user/messages";
        }

        String[] receiverEmails = messageDTO.getReceiverEmail().split(",");
        for (String receiverEmail : receiverEmails) {
            Studentdto receiver = studentService.getStudentByEmail(receiverEmail.trim());

            if (receiver == null) {
                model.addAttribute("error", "Receiver not found: " + receiverEmail);
                return "user/messages";
            }

            MessageDTO newMessage = new MessageDTO();
            newMessage.setSenderId(sender.getId());
            newMessage.setSenderName(sender.getFirstName() + " " + sender.getLastName());
            newMessage.setReceiverId(receiver.getId());
            newMessage.setReceiverName(receiver.getFirstName() + " " + receiver.getLastName());
            newMessage.setReceiverEmail(receiverEmail.trim());
            newMessage.setContent(messageDTO.getContent());

            messageService.sendMessage(newMessage);
        }

        return "redirect:/userInfo/messages";
    }
    @GetMapping("/detail/{id}")
    public String getMessageDetail(@PathVariable Long id, Model model) {
        MessageDTO messageDTO = messageService.getMessageById(id);
        model.addAttribute("message", messageDTO);
        return "user/message_detail";
    }

    @PostMapping("/messages/delete/{id}")
    public String deleteMessage(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = messageService.deleteMessage(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Tin nhắn đã được xóa thành công.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa tin nhắn.");
        }
        return "redirect:/userInfo/messages";
    }

    
}
