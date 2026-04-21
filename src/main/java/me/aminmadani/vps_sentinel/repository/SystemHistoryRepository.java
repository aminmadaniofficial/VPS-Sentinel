package me.aminmadani.vps_sentinel.repository;

import me.aminmadani.vps_sentinel.model.SystemHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemHistoryRepository extends JpaRepository<SystemHistory, Long> {
    // متد برای گرفتن آخرین رکوردها جهت نمایش در نمودار
    java.util.List<SystemHistory> findTop10ByOrderByTimestampDesc();
}