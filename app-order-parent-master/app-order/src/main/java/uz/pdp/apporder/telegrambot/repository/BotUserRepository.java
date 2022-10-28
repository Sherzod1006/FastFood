package uz.pdp.apporder.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apporder.telegrambot.entity.BotUser;
import uz.pdp.apporder.telegrambot.entity.ChatPageStatus;

public interface BotUserRepository extends JpaRepository<BotUser, Long> {

    BotUser findBotUserByChatId(Long chatId);

}
