package affan.id.qwikfund.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import affan.id.qwikfund.R;

/**
 * Created by Herfanda on 10/2/2017 AD.
 */

public class FragmentDashboardBorrower extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.fragment_dashboard_borrower,container,false);
        }

        return view;
    }


}
