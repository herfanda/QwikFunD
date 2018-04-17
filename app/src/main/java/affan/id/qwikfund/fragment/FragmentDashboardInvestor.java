package affan.id.qwikfund.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import affan.id.qwikfund.R;

/**
 * Created by yunus on 10/2/17.
 */

public class FragmentDashboardInvestor extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_dashboard_investor,container,false);
        }

        return view;
    }
}
