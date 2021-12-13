package com.vkochenkov.composeexample.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vkochenkov.composeexample.data.Repository
import com.vkochenkov.composeexample.data.entity.NoteEntity
import com.vkochenkov.composeexample.di.App
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivityViewModel(
    application: Application
) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: Repository

    init {
        (application as App).getAppComponent().inject(this)
    }

    private var regularScreenStateData = initRegularScreenStateData()
    private var _screenState: MutableLiveData<MainScreenState> = MutableLiveData(regularScreenStateData)
    var screenState: LiveData<MainScreenState> = _screenState

    fun getAllNotes() {
        repository.getAllNotes()
            .subscribe(
                object : MaybeObserver<List<NoteEntity>> {
                    override fun onSubscribe(d: Disposable) {
                        val previousState = _screenState.value
                        _screenState.postValue(MainScreenState.Loading(previousState))
                    }

                    override fun onSuccess(list: List<NoteEntity>) {
                        regularScreenStateData = regularScreenStateData.copy(notesList = list)
                        _screenState.postValue(regularScreenStateData)
                    }

                    override fun onError(e: Throwable) {
                        _screenState.postValue(MainScreenState.Error(e.toString()))
                    }

                    override fun onComplete() {
                        //NOP
                    }
                }
            )
    }

    fun insertNote(noteEntity: NoteEntity) {
        repository.insertNote(noteEntity)
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    //NOP
                }

                override fun onComplete() {
                    //todo add string to resources
                    regularScreenStateData = regularScreenStateData.copy(
                        message = "${noteEntity.title} has ben successfully saved"
                    )
                    _screenState.postValue(regularScreenStateData)
                    getAllNotes()
                }

                override fun onError(e: Throwable) {
                    _screenState.postValue(MainScreenState.Error(e.toString()))
                }
            })
    }

    fun deleteNote(noteEntity: NoteEntity) {
        repository.deleteNote(noteEntity)
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    //NOP
                }

                override fun onComplete() {
                    //todo add string to resources
                    regularScreenStateData = regularScreenStateData.copy(
                        message = "${noteEntity.title} has ben successfully deleted"
                    )
                    _screenState.postValue(regularScreenStateData)
                    getAllNotes()
                }

                override fun onError(e: Throwable) {
                    _screenState.postValue(MainScreenState.Error(e.toString()))
                }
            })
    }

    private fun initRegularScreenStateData(): MainScreenState.Regular {
        return MainScreenState.Regular(
            addNoteBtnAction = { note -> insertNote(note) },
            noteItemBtnAction = { note -> deleteNote(note) }
        )
    }
}