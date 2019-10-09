package ru.countermeasure.moviestvshowsdb.di.module

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.kodein.di.generic.with
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.countermeasure.moviestvshowsdb.BuildConfig
import ru.countermeasure.moviestvshowsdb.data.network.MoviesService

val networkModule = Kodein.Module(name = "networkModule") {
    constant("baseUrl") with BuildConfig.BASE_API_URL

    bind<Gson>() with singleton {
        Gson()
    }

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
            .addInterceptor(instance())
            .build()
    }

    bind<Interceptor>() with singleton {
        Interceptor {
            val original = it.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(BuildConfig.API_KEY_QUERY_NAME, BuildConfig.API_KEY)
                .build()
            val requestBuilder = original.newBuilder()
                .url(url)
            val request = requestBuilder.build()
            return@Interceptor it.proceed(request)
        }
    }

    bind<Retrofit>() with singleton {
        Retrofit.Builder()
            .baseUrl(instance<String>("baseUrl"))
            .addConverterFactory(GsonConverterFactory.create(instance()))
            .client(instance())
            .build()
    }

    bind<MoviesService>() with singleton {
        instance<Retrofit>().create(MoviesService::class.java)
    }
}