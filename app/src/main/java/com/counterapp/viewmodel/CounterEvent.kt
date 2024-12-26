package com.counterapp.viewmodel

sealed class CounterEvent {
    data object Increment : CounterEvent()
    data object Decrement : CounterEvent()
    data object Reset : CounterEvent()
}