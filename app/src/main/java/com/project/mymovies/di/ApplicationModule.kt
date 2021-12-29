package com.project.mymovies.di

import android.app.Application
import androidx.room.Room
import com.project.mymovies.local.MoviesDataBase
import com.project.mymovies.network.APIInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /*    @Provides
        fun provideGson(): Gson = GsonBuilder().create()
    */
    @Provides
    @Singleton
    fun provideAPIInterface(retrofit: Retrofit): APIInterface =
        retrofit.create(APIInterface::class.java)


    @Provides
    @Singleton
    fun provideDatabase(app: Application): MoviesDataBase =
        Room.databaseBuilder(app, MoviesDataBase::class.java, "movies_database")
            .build()

/*    @Provides
    @Singleton
    fun provideDataRepository(apiService: APIInterface , db: MoviesDataBase): HomeRepository {
        return HomeRepository(apiService , db)
    }
*/
}