package info.hccis.student.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import info.hccis.student.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    int[] sampleImages = {R.drawable.carousel_one, R.drawable.carousel_two, R.drawable.carousel_three, R.drawable.carousel_four};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final CarouselView carouselView = view.findViewById(R.id.carousel_View);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        return view;
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
}
