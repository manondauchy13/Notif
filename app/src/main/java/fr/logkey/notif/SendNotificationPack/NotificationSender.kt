package fr.logkey.notif.SendNotificationPack

class NotificationSender(val data: Data?, val to:String){
    constructor():this(null,""){}
}