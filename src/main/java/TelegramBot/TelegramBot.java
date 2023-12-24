//Пожалуй основная часть проекта. Посмотрев фреймворки которые стоит использовать для ТелеграммБота, я успел много
//раз потеряться. Итого из сторонних фреймворков у меня четыре штуки. Использую Maven для создания зависимости
//В файлике pom.xml прописана зависимость к самой последней версии фреймворка telegrambots

package TelegramBot;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TelegramBot extends TelegramLongPollingBot{ //Имплементировать onUpdateReceived и getBotUsername обязательно иначе жалуется на код
    private final String botToken; //Токен нашего бота, необходимый для аутентификации когда мы обращаемся к API Telegram
    private final Map<Long, List<Note>> notes; //добавим дженерик, отображающий идентификаторы чата на списки заметок пользователей в этом чате.

    public TelegramBot(String botToken) {
        this.botToken = botToken;
        this.notes = new HashMap<>();
    }

    @Override
    public void onUpdateReceived(Update update) { //вызов этого метода означает то, что бот получает обновление
        //допустим мы получаем сообщение
        if (update.hasMessage()){
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            if (!notes.containsKey(chatId)){
                notes.put(chatId, new ArrayList<>());
            }
            //Получим текст и проверим, начинается ли он с "/newnote "
            String text = message.getText();
            if (text.startsWith("/newnote ")){
                String noteText = text.substring(9); //beginindex = 9, так как у нас 9 символов включая пробел
                Note note = new Note();
                note.setText(noteText);
                notes.get(chatId).add(note);
                sendMessage (chatId, "Заметка добавлена!"); //В этом случае заметка добавляется в список

                //теперь нужно вывести заметки на экран
            } else if (text.equals("/getnotes")){
                List<Note> userNotes = notes.get(chatId);
                StringBuilder response = new StringBuilder("Ваши заметки:\n");
                for (Note note : userNotes){
                    response.append(note.getText()).append("-").append(note.getWhenCreated()).append("\n");
                }
                sendMessage(chatId, response.toString());

                //теперь нужно удалить заметку
            } else if (text.startsWith("/deletenote ")){
                int nIndex = Integer.parseInt(text.substring(12));
                deleteNote(chatId, nIndex);
            }
                //теперь нужно изменить заметку
            else if (text.startsWith("/changenote")){
                String[] commandSplit = text.split(" ", 3);
                int nIndex = Integer.parseInt(commandSplit[1]);
                String newNoteText = commandSplit[2];
                updateNote(chatId, nIndex, newNoteText);
            }
        }
    }

    private void sendMessage(Long chatId, String text) { //метод для отправки заметки
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace(); //добавляем эксепшен на случай ошибки отправки
        }
    }

    private void deleteNote(long chatId, int nIndex) { //метод для удаления заметки. кроме всего прочего допускается
        //вариант, что пользователь неправильно указал индекс
        if (notes.containsKey(chatId)) {
            List<Note> noteList = notes.get(chatId);
            if (nIndex >= 0 && nIndex < noteList.size()) {
                noteList.remove(nIndex);
                sendMessage(chatId, "Заметка удалена.");
            } else {
                sendMessage(chatId, "Неверный индекс заметки.");
            }
        }
    }

    private void updateNote(long chatId, int nIndex, String newNoteText) { //метод для изменения заметки, так же допускается вариант
        //что пользователь неправильно указал мндекс.
        //кроме этого с обновлением содержания заметки, обновляется и дата и время
        if (notes.containsKey(chatId)) {
            List<Note> noteList = notes.get(chatId);
            if (nIndex >= 0 && nIndex < noteList.size()) {
                Note note = noteList.get(nIndex);
                note.setWhenCreated(LocalDateTime.now()); //обновляем время создания заметки
                LocalDateTime updatedTime = note.getWhenCreated();
                note.setText(newNoteText);
                sendMessage(chatId, "Заметка обновлена. Время обновления: " + updatedTime.toString());
            } else {
                sendMessage(chatId, "Неверный индекс заметки.");
            }
        }
    }

    @Override
    public String getBotUsername() { // Возвращает имя нашего бота. Чтобы получить имя необходим бот "BotFather" в телеграмме
        return "https://t.me/NoteIFST21_bot";
    }

    @Override
    public String getBotToken() { // Возвращает токен нашего бота. Чтобы получить токен так женеобходим Телеграмм бот "BotFather".
        return botToken;
    }
}
