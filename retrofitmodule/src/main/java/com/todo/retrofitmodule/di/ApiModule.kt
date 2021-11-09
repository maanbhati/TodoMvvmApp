package com.todo.retrofitmodule.di

import com.todo.retrofitmodule.api.Api
import com.todo.retrofitmodule.api.Api.Companion.BASE_URL
import com.todo.retrofitmodule.api.GzipRequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    /*  @Provides
      @Singleton
      fun provideLoggingInterceptor(): HttpLoggingInterceptor {
          return HttpLoggingInterceptor { message ->
              Log.d("OkHttp3", message)
          }.apply {
              level = HttpLoggingInterceptor.Level.BODY
          }
      }*/

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(GzipRequestInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}