package affan.id.qwikfund.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import affan.id.qwikfund.R;

/**
 * Created by yunus on 9/5/17.
 */

public class FragmentRequestForm extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.fragment_request_form,container,false);
        }

        return view;
    }
}
