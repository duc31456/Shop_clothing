package com.example.ck.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.example.ck.adapter.doBongDa_adapter;
import com.example.ck.adapter.home_adapter;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_dobongda extends Fragment {
    private RecyclerView recycler_bongda;
    private doBongDa_adapter adapter;

    ArrayList<class_product> products_dobongda;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dobongda, container, false);

        recycler_bongda = view.findViewById(R.id.recycle_bongda);
        adapter = new doBongDa_adapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recycler_bongda.setLayoutManager(gridLayoutManager);

        CallApiProductDoBongDa();
        return view;
    }

    public void CallApiProductDoBongDa()
    {
        CallApiUser.callApi.getApiProductDoBongDa().enqueue(new Callback<ArrayList<class_product>>() {
            @Override
            public void onResponse(Call<ArrayList<class_product>> call, Response<ArrayList<class_product>> response) {
                if (response.isSuccessful())
                {
                    products_dobongda = new ArrayList<>();
                    products_dobongda = response.body();
                    adapter.setdata(products_dobongda);
                    recycler_bongda.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<class_product>> call, Throwable t) {
                Log.d("BBB",""+t);
            }
        });
    }
}
