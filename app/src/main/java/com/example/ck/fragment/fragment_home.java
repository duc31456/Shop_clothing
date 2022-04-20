package com.example.ck.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ck.R;
import com.example.ck.adapter.home_adapter;
import com.example.ck.item_class.class_home;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class fragment_home extends Fragment {
    private RecyclerView recyclerView;
    private home_adapter adapter;
    ViewFlipper flipper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        flipper = view.findViewById(R.id.view_flipper);

        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);


        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new home_adapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter.setdata(getList());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private ArrayList<class_home> getList()
    {
        ArrayList<class_home> list = new ArrayList<>();
        list.add(new class_home(R.drawable.ao_den, "Áo đen nhập khẩu"));
        list.add(new class_home(R.drawable.ao_thun_den, "Áo thun đen nhập khẩu"));
        list.add(new class_home(R.drawable.ao_thun_nam_3, "Áo thun nam nhập khẩu"));
        list.add(new class_home(R.drawable.ao_thun_nam_4, "Áo thun nam xuất khẩu"));
        list.add(new class_home(R.drawable.ao_thun_trang, "Áo thun trăng nhập khẩu"));
        list.add(new class_home(R.drawable.ao_trang, "Áo trang xuất khẩu"));

        return list;
    }
}
