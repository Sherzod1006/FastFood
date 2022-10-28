package uz.pdp.apporder.telegrambot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.pdp.apporder.telegrambot.repository.ChatPageStatusRepository;

@AllArgsConstructor
public class NeededUtils {
    @Getter
    private static ChatPageStatusRepository chatRepo;
}
