package caldroidsample.com.myapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment implements View.OnClickListener{
    private Button fragment_btn_1;
    private Button fragment_btn_2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);
        fragment_btn_1 = (Button) view.findViewById(R.id.fragment_button_1);
        fragment_btn_2 = (Button) view.findViewById(R.id.fragment_button_2);
        fragment_btn_1.setOnClickListener(this);
        fragment_btn_2.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_button_1:
                Fragment fragment1 = new Fragment1();
                moveToFragment(fragment1);
                break;
            case R.id.fragment_button_2:
                Fragment fragment2 = new Fragment2();
                moveToFragment(fragment2);
                break;
        }
    }

    private void moveToFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }
}