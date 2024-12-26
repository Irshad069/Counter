package com.counterapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.counterapp.model.CounterModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {

    private val _counterState = MutableStateFlow(CounterModel(0))
    val counterFlow = _counterState.asStateFlow()

    private var counter = 0

    fun onEvent(event: CounterEvent) {
        when (event) {
            is CounterEvent.Increment -> if (counter < 100) counter++
            is CounterEvent.Decrement -> if (counter > 0) counter--
            is CounterEvent.Reset -> counter = 0
        }
        sendCounterUpdate()
    }

    fun getCurrentCounter(): Int = counter

    private fun sendCounterUpdate() {
        viewModelScope.launch {
            _counterState.emit(CounterModel(counter))
        }
    }
}

