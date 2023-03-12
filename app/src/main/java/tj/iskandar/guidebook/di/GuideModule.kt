package tj.iskandarbek.omdb.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import tj.iskandar.guidebook.db.AppDatabase
import tj.iskandar.guidebook.db.dao.GuideDao
import tj.iskandarbek.omdb.data.remote.GuideApi
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object GuideModule {
    @Provides
    fun providesOmdbApi(
        @Named("appRetrofit") retrofit: Retrofit
    ): GuideApi = retrofit.create(GuideApi::class.java)

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my-db"
        ).build()
    }

    @Provides
    fun provideGuideDao(appDatabase: AppDatabase): GuideDao {
        return appDatabase.postDao()
    }

}