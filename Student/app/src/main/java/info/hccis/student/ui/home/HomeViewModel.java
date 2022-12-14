package info.hccis.student.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Home ViewModel
 *
 * @author mariannahollanda
 * @since 20220303
 */
public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Home Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}