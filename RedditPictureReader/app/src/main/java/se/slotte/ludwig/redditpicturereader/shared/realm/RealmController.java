package se.slotte.ludwig.redditpicturereader.shared.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import io.realm.Realm;
import io.realm.RealmResults;
import se.slotte.ludwig.redditpicturereader.shared.realm.model.RealmSavedFavouritePicture;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }


    //find all objects in the DataInChildren.class
    public RealmResults<RealmSavedFavouritePicture> getDataInChildrens() {

        return realm.where(RealmSavedFavouritePicture.class).findAll();
    }

    //query a single item with the given id
    public RealmSavedFavouritePicture getDataInChildren(String id) {

        return realm.where(RealmSavedFavouritePicture.class).equalTo("id", id).findFirst();
    }


    //query example
    public RealmResults<RealmSavedFavouritePicture> queryedDataInChildrens() {

        return realm.where(RealmSavedFavouritePicture.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}