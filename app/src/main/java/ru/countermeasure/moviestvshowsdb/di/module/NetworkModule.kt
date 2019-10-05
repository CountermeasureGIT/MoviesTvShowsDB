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
import ru.countermeasure.moviestvshowsdb.model.network.MoviesDBApi
import ru.countermeasure.moviestvshowsdb.util.temp.LiveDataCallAdapterFactory

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

    bind<LiveDataCallAdapterFactory>() with singleton {
        LiveDataCallAdapterFactory()
    }

    bind<Interceptor>() with singleton {
        Interceptor {
            val original = it.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", "1a680bee1c010ab832b442cf27840c79")
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
            .addCallAdapterFactory(instance())
            .client(instance())
            .build()
    }

    bind<MoviesDBApi>() with singleton {
        instance<Retrofit>().create(MoviesDBApi::class.java)
    }
}