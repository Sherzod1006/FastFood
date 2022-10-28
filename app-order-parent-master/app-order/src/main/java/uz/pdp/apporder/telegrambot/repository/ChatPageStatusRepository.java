package uz.pdp.apporder.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apporder.telegrambot.entity.ChatPageStatus;

public interface ChatPageStatusRepository extends JpaRepository<ChatPageStatus, Long> {

}
