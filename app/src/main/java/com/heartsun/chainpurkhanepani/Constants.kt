package com.heartsun.chainpurkhanepani

const val StandardDateFormat = "yyyy-MM-dd"
const val StandardTimeFormat = "HH:mm:ss"
const val StandardDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss"

object UserType {
    const val Distribution = "Distribution"
    const val Rider = "Rider"
}

object PlaceHolder {
    const val ProfilePlaceHolder =
        "https://www.pngitem.com/pimgs/m/522-5220445_anonymous-profile-grey-person-sticker-glitch-empty-profile.png"
}

object ShipmentsStatus {
    const val NewOrders = "neworders"
    const val Delivered = "deliveredtowarehouse"
    const val PickupList = "pickuplist"

    const val Accepted = "accepted"
    const val Rejected = "rejected"
    const val PickupConfirm = "pickupconfirm"
    const val PickupRejected = "pickuprejected"
}

object DropoffStatus {
    const val DropoffOrders = "dropofforders"
    const val DropoffList = "dropofflist"
    const val DropoffHistory = "dropoffhistory"
}

object TabLayoutLabels {
    val labelOne = listOf("Pickup", "Dropoff")
}


object OtpValidationType {
    const val phone = "Phone"
    const val email = "Email"
}