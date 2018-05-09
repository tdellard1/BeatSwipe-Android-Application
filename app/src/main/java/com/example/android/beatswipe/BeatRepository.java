package com.example.android.beatswipe;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class BeatRepository {

    private BeatDao mBeatDao;
    private LiveData<List<Beat>> mAllBeats;

    BeatRepository(Application application) {
        BeatRoomDatabase db = BeatRoomDatabase.getDatabase(application);
        mBeatDao = db.beatDao();
        mAllBeats = mBeatDao.getAllBeats();
    }

    LiveData<List<Beat>> getAllBeats() { return mAllBeats; }

    public void insert(Beat beat) { new insertAsyncTask(mBeatDao).execute(beat); }

    private static class insertAsyncTask extends AsyncTask<Beat, Void, Void> {

        private BeatDao mAsyncTaskDao;

        public insertAsyncTask(BeatDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Beat... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }
}