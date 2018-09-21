package com.ivara.aravi.echo.ShoutOut;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivara.aravi.echo.R;

/**
 * Created by Aravi on 23-08-2017.
 */

public class tab3Events extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shouts_viewer_tab3 , container, false);
        return rootView;
    }

}
