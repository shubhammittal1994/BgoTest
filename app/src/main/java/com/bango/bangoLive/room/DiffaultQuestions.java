package com.bango.bangoLive.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class DiffaultQuestions {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "message")
    String message;
    @ColumnInfo(name = "sender")
    String sender;
    @ColumnInfo(name = "question_id")
    String question_id;


    public DiffaultQuestions(String message, String sender, String question_id) {
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.question_id = question_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

}
