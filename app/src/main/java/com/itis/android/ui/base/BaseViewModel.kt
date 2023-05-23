package com.itis.android.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Action, Event>(
    initialState: State
) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<State>
        get() = _state.asStateFlow()

    protected val _action = MutableSharedFlow<Action?>()
    val action: SharedFlow<Action?>
        get() = _action.asSharedFlow()

    protected fun state(change: State.() -> State) {
        _state.value = _state.value.change()
    }

    protected fun action(action: State.() -> Action) {
        viewModelScope.launch {
            _action.emit(_state.value.action())
        }
    }

    abstract fun event(event: Event)
}
