package com.example.mvm.services;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.Category;
import com.example.mvm.model.Image;
import com.example.mvm.model.Offer;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class OfferService {

    public static List<Offer> findAll() {
        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/offer")
                .build();
        Response response = null;
        List<Offer> offers = new ArrayList<>();
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if (response.code() == 200) {
                List<Object> list = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(), Object[].class));
                for (Object o : list) {
                    Offer offer = new Offer();
                    LinkedTreeMap ltm = (LinkedTreeMap) o;
                    offer.setName(ltm.get("name").toString());
                    offer.setUsername(ltm.get("username").toString());
                    offer.setPrice(ltm.get("price").toString());

                    System.out.println(ltm.get("name").toString()+ltm.get("price").toString());

                    offers.add(offer);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offers;
    }
}
