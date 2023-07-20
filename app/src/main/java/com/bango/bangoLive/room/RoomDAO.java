package com.bango.bangoLive.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface RoomDAO {

    @Insert
    void insert(MusicTable model);

    // below method is use to update
    // the data in our database.
    @Update
    void update(MusicTable model);

    // below line is use to delete a
    // specific course in our database.
    @Query("delete  from music where id =:musicId")
     void deleteById(int musicId);

    // on below line we are making query to
    // delete all courses from our database.
    @Query("DELETE FROM music")
    void deleteAllCourses();


    @Query("SELECT * FROM music")
    List<MusicTable> getAllSongs();

    @Query("SELECT EXISTS(SELECT * FROM music WHERE id = :id)")
     Boolean isExist(int id);



    //for customer chat
    @Insert
    void insert(CustomerChatTable customerChatTable);

    @Query("SELECT * FROM chat")
    List<CustomerChatTable> getChat();

    @Query("delete  from chat where id =:id")
    void deleteChatMsg(int id);

    @Query("delete from chat")  //for delete a table
    void chatTableDrop();

    @Query("SELECT EXISTS(SELECT * FROM chat WHERE id = :id)")
    boolean chatTableIdExits(int id);


    //for diffault Questins chat
    @Insert
    void insert(DiffaultQuestions diffaultQuestions);

    @Query("SELECT * FROM questions")
    List<DiffaultQuestions> getDiffaultQuestions();

    @Query("SELECT EXISTS(SELECT * FROM questions WHERE sender = :sender)")
    boolean chatTableSenderExits(String  sender);

    @Query("delete from questions")  //for delete a table
    void deleteQuestionTable();

    @Query("SELECT EXISTS(SELECT * FROM questions WHERE id = :id)")
    boolean questionTableExits(int id);

    @Query("SELECT EXISTS(SELECT * FROM questions WHERE question_id = :question_id)")
    boolean question_idExits(String question_id);

}
