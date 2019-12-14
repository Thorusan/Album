package com.example.album.ui.users

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class UsersListPresenter(
    private val view: UsersListViewPresenterContract.ViewInterface,
    private val model: UsersModel
) :
    UsersListViewPresenterContract.PresenterInterface {
    private val compositeDisposable = CompositeDisposable()

    override fun getUsersList() {
        compositeDisposable.add(model.getUsersFromProvider()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgress() }
            .subscribe({
                view.hideProgress()
                view.displayUsersList(it);
            }, {
                view.hideProgress()
            })
        )
    }

    /**
     * Unsubscribe from network handles (call dispose)
     */
    fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun onItemClick(userId: Int) {
        view.onItemClick(userId)
    }
}