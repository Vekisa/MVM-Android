package com.example.mvm.authentication;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AuthInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = null;

        if(AppProperties.getInstance().token != null) {
            System.out.println("TOKEN "+AppProperties.getInstance().token);
            request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + AppProperties.getInstance().token).build();
            System.out.println(request.url());
        }else {
            request = chain.request().newBuilder().build();
    }
        return chain.proceed(request);
    }
}
