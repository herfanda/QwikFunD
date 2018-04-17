package affan.id.qwikfund.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import affan.id.qwikfund.R;

/**
 * Created by Herfanda on 10/8/2017 AD.
 */

public class FragmentAccountBorrower extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.borrower_account_layout,container,false);
        }

        return view;
    }
}
