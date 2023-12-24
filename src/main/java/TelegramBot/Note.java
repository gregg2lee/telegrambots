//Этот класс отвечает за представление заметок. У заметки должен быть текст.
//Кроме этого наша заметка должна отображать время создания, чтобы пользователь мог отслеживать это :)
package TelegramBot;

import java.time.LocalDateTime;

public class Note {
    private String text;
    private LocalDateTime whenCreated;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(LocalDateTime whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Note(){
        whenCreated = LocalDateTime.now(); //Этот метод возвращает дату и время, когда была внесена заметка
    }
}
