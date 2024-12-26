package com.counterapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CounterModel(val count: Int) : Parcelable
