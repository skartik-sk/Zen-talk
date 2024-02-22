package com.example.zen_talk.notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAV7sk9Po:APA91bH8tL1-nMXv1w25ZOpMogkVNYg2B38RMR33elqtRBACOeOrWZ0P1tB8U0-8vBdn-EWeH4f1KNdIKw0_PYuIXwbGDDSidDTzvf_mtBfZmRGYUQFWtWSWMnN38f0Z9jKHfoPC0Xsw"
    })
    @POST("fcm/send")
    Call<MyResponce> sendNotification(@Body Sender body);

}
