package com.conectors.innova.recyclerviewpagination;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.conectors.innova.recyclerviewpagination.adapter.PaginationAdapter;
import com.conectors.innova.recyclerviewpagination.databinding.ActivityMainBinding;
import com.conectors.innova.recyclerviewpagination.listener.PaginationScrollListener;
import com.conectors.innova.recyclerviewpagination.model.Item;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding mBinding;

    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(MainActivity.this,
                R.layout.activity_main);

        //rv = (RecyclerView) findViewById(R.id.main_recycler);
        //progressBar = (ProgressBar) findViewById(R.id.main_progress);

        adapter = new PaginationAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mBinding.contentView.setLayoutManager(linearLayoutManager);

        mBinding.contentView.setItemAnimator(new DefaultItemAnimator());

        mBinding.contentView.setAdapter(adapter);

        mBinding.contentView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        // mocking network delay for API call
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
            }
        }, 1000);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void loadFirstPage() {
        Log.e(TAG, "loadFirstPage: ");
        List<Item> items = Item.createItems(adapter.getItemCount());
        mBinding.mainProgress.setVisibility(View.GONE);
        adapter.addAll(items);

        if (currentPage <= TOTAL_PAGES) {
            adapter.addLoadingFooter();
        } else {
            isLastPage = true;
        }
    }

    private void loadNextPage() {

        List<Item> items = Item.createItems(adapter.getItemCount());
        TOTAL_PAGES++;
        adapter.removeLoadingFooter();
        isLoading = false;

        adapter.addAll(items);

        if (currentPage != TOTAL_PAGES) {
            adapter.addLoadingFooter();
        } else {
            isLastPage = true;
        }
    }
}
