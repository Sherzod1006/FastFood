package uz.pdp.apporder.telegrambot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.apporder.telegrambot.bot.CommandsBot;
import uz.pdp.appproduct.util.RestConstants;

@RestController
@RequiredArgsConstructor
public class WebhookController {
    private final CommandsBot commandsBot;

    @PostMapping(RestConstants.SERVICE_BASE_PATH+"telegram")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return commandsBot.onWebhookUpdateReceived(update);
    }
}