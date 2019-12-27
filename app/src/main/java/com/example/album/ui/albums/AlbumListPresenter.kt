package com.example.album.ui.albums

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class AlbumListPresenter(
    private val view: AlbumListViewPresenterContract.ViewInterface,
    private val model: AlbumModel
) : AlbumListViewPresenterContract.PresenterInterface {
    private val compositeDisposable = CompositeDisposable()

    override fun getAlbumList(userId: Int) {
        compositeDisposable.add(model.getAlbumsFromProvider(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgress() }
            .subscribe({
                view.hideProgress()
                view.displayAlbumList(it);

                for (albumData in it) {
                    getPhotoList(albumData.id)
                }
            }, {
                view.hideProgress()
            })
        )
    }

    override fun getPhotoList(albumId: Int) {
        compositeDisposable.add(model.getPhotosFromProvider(albumId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgress() }
            .subscribe({
                view.hideProgress()
                view.displayAlbumThumbnails(albumId, it);
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

    fun onItemClick(albumId: Int) {
        view.onItemClick(albumId)
    }

}