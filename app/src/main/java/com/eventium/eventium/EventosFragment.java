package com.eventium.eventium;

import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.eventium.eventium.TabFragments.TabOneFragment;
import com.eventium.eventium.TabFragments.TabTwoFragment;
import com.eventium.eventium.TabFragments.TabThreeFragment;


public class EventosFragment extends Fragment {

    private Toolbar mytoolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public EventosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_eventos, container, false);
        if (savedInstanceState == null) {
            tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
            viewPager = (ViewPager) view.findViewById(R.id.viewpager);
            poblarViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
        return view;
    }

    private void poblarViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new TabOneFragment(), "DESTACADOS");
        adapter.addFragment(new TabTwoFragment(), "RECOMENDADOS");
        adapter.addFragment(new TabThreeFragment(), "TODOS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
