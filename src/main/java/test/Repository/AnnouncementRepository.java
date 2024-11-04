package test.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import test.entity.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
	  @Query("SELECT a FROM Announcement a ORDER BY a.date DESC")
	    List<Announcement> findAllOrderByDateDesc();
}
