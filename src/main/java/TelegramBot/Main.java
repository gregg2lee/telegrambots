package TelegramBot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try{
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class); //продублирую. Чтобы получить токен необходим Телеграмм бот "BotFather".
            TelegramBot bot = new TelegramBot("6894159021:AAFZ4XphXSAJ2jo4fxkldX6O0IpD5i3d_co");
            botsApi.registerBot(bot);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //Для запуска нужен провайдер SLF4J. Он лежит в папке provider. Его нужно занести в структуру проекта ->
    //библиотека и там добавить
}