package nhannt.foody.screen.eat_where;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nhannt.foody.R;
import nhannt.foody.screen.BaseFragment;

/**
 * Created by nhannt on 09/11/2017.
 */

public class WhereToEatFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_where_to_eat, container, false);
        return view;
    }
}
