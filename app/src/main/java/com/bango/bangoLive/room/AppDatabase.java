package com.bango.bangoLive.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.io.Serializable;


@Database(entities = {MusicTable.class,CustomerChatTable.class,DiffaultQuestions.class},exportSchema = false,version = 1)
public abstract class AppDatabase extends RoomDatabase implements Serializable {

//    static final Migration MIGRATION_1_2 = new Migration(1,2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//
//        }
//    };
//
//    static final Migration MIGRATION_2_3 = new Migration(2,3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE 'user' ADD COLUMN 'question_id' TEXT");
//        }
//    };

    public abstract RoomDAO userDao();

    private static AppDatabase instance=null;

    public static synchronized AppDatabase getInstance(Context context) {

        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                            ,AppDatabase.class,"music.db")
//                    .addMigrations(MIGRATION_2_3)
                    .allowMainThreadQueries().build();
        }
        return instance;

    }
}
