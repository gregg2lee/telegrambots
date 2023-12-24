//Этот класс отвечает за представление заметок. У заметки должны быть "Титульный лист" и сам текст.
//Кроме этого наша заметка должна отображать время создания, чтобы пользователь мог отслеживать это :)
package TelegramBot;

import java.time.LocalDateTime;

public class Note {
    private String titleList;
    private String text;
    private LocalDateTime whenCreated;
    public String getTitleList() {
        return titleList;
    }

    public void setTitleList(String titleList) {
        this.titleList = titleList;
    }

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
