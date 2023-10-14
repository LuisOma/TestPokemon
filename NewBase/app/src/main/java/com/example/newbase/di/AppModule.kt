package com.example.newbase.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.example.newbase.BuildConfig.BASE_URL
import com.example.newbase.data.dataSource.remote.PokemonRemoteDataSource
import com.example.newbase.data.dataSource.remote.PokemonService
import com.example.newbase.data.repo.PokemonRepository
import com.example.newbase.domain.useCase.GetPokemonUseCase
import com.google.gson.GsonBuilder

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url()
            .newBuilder()
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun providePokemonService(retrofit: Retrofit): PokemonService = retrofit.create(PokemonService::class.java)

    @Singleton
    @Provides
    fun providePokemonRemoteDataSource(pokemonService: PokemonService) = PokemonRemoteDataSource(pokemonService)

    @Singleton
    @Provides
    fun providePokemonRepository(remoteDataSource: PokemonRemoteDataSource) =
        PokemonRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideGetPokemonUseCase(pokemonRepository: PokemonRepository) =
        GetPokemonUseCase(pokemonRepository)

}