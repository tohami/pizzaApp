package com.etisalat.sampletask.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etisalat.sampletask.R;
import com.etisalat.sampletask.bases.BaseFragment;
import com.etisalat.sampletask.di.Injection;
import com.etisalat.sampletask.model.PizzaResultModel;
import com.etisalat.sampletask.ui.adapter.PizzaAdapter;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class MainFragment extends BaseFragment<MainPresenter> implements MainPresenterListener {

    private PizzaAdapter pizzaAdapter;
    private TextView lastUpdateAt;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbarView = view.findViewById(R.id.my_toolbar) ;
        lastUpdateAt = toolbarView.findViewById(R.id.tvLastUpdateAt) ;
        RecyclerView pizzaRv = view.findViewById(R.id.rvPizza) ;
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout) ;

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarView) ;


        pizzaAdapter = new PizzaAdapter() ;

        pizzaRv.setAdapter(pizzaAdapter);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refreshPizzaList());

        presenter.refreshPizzaList();
    }

    @Override
    protected MainPresenter setupPresenter() {
        return new MainPresenter(this , Injection.provideRepositoryFactory(getActivity()));
    }

    @Override
    public void onDestroy() {
        presenter.deAttach() ;
        super.onDestroy();
    }

    @Override
    public void updatePizzaList(PizzaResultModel pizzaItemList) {
        pizzaAdapter.setData(pizzaItemList.getPizzaItemList());
        long now = Calendar.getInstance().getTimeInMillis() ;
        long diff = now - pizzaItemList.createAt ;
        String updatedAtMsg = "Updated " ;
        if(TimeUnit.MILLISECONDS.toMinutes(diff)<1)
            updatedAtMsg += "now" ;
        else
            updatedAtMsg += TimeUnit.MILLISECONDS.toMinutes(diff) +" minutes ago" ;

        lastUpdateAt.setText(updatedAtMsg);
    }

    @Override
    public void displayError(String error) {
        Log.e("state" , error) ;
        showSnackbar(error , getView());
    }

    @Override
    public void updateProgressState(boolean isRequestInProgress) {
        Log.e("state" , isRequestInProgress + " progress") ;
        swipeRefreshLayout.setRefreshing(isRequestInProgress);
    }
}
