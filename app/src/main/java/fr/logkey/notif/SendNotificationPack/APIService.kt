package fr.logkey.notif.SendNotificationPack

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

public interface APIService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=\tAAAALxcCCDY:APA91bGWH0ELkmbFG-qYFaUcUCf_w6oYWt9HgMJ0Qn49v1ZN5jMzJ_HqcNvEM5kVbGQbzmjwyLjETwU6sit0S1EsqtlmLZMPmhZ6FrBo6BMMWP18dFu6RDOxIG-TieSwEeby4qZaJcSv" // Your server key refer to video for finding your server key
    )
    @POST("fcm/send")
    open fun sendNotifcation(@Body body: NotificationSender?): Call<MyResponse?>?
}