package com.example.album.ui.albums

import com.example.album.datamodel.AlbumData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.stream.Collectors.toList

class AlbumListPresenter(
    private val view: AlbumListViewPresenterContract.ViewInterface,
    private val model: AlbumModel
) : AlbumListViewPresenterContract.PresenterInterface {
    private val compositeDisposable = CompositeDisposable()

   /* private lateinit var albumList: List<AlbumData>
    private lateinit var photosList: List<PhotoData>*/

    override fun getAlbumList(userId: Int) {
       compositeDisposable.add(model.getAlbumsFromProvider(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgress() }
            .subscribe({
                view.hideProgress()
                view.displayAlbumList(it);
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
                view.displayAlbumThumbnails(it);
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