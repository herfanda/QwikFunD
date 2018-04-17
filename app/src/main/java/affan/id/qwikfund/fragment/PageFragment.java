package affan.id.qwikfund.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import affan.id.qwikfund.R;

/**
 * Created by yunus on 9/5/17.
 */

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        int page = getArguments().getInt(ARG_PAGE);
        TextView textView = (TextView) view;
        textView.setText(String.format(getString(R.string.text), page));

        return view;
    }
}
