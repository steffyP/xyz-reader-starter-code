package com.example.xyzreader.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 */
public class ArticleDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Cursor mCursor;
    private long mStartId;


    public static final String EXTRA_IMAGE_URL = "extra_image_url";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_BY_DETAILS = "extra_author";
    public static final String EXTRA_PUBLISHED = "extra_published";
    public static final String EXTRA_ITEM_ID = "extra_item_id";

    private long mSelectedItemId;
    private int mSelectedItemUpButtonFloor = Integer.MAX_VALUE;
    private int mTopInset;

    private boolean isToolbarVisible;
    private Toolbar toolbar;
    private ImageView imageView;
    private TextView titleView;
    private TextView bylineView;
    private TextView bodyView;
    private static final int LOADER_DETAILS_ID = 113344;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_article_detail);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isToolbarVisible = false;

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        //todo appBarLayout.addOnOffsetChangedListener(this);


        imageView = findViewById(R.id.photo);
        titleView = findViewById(R.id.article_title);
        // todo
        bylineView = findViewById(R.id.article_byline);
        bodyView = findViewById(R.id.article_body);

        // todo
        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().getData() != null) {
                mStartId = ItemsContract.Items.getItemId(getIntent().getData());
                mSelectedItemId = mStartId;
            }
        }
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mSelectedItemId = b.getLong(EXTRA_ITEM_ID);

            String imageUrl = b.getString(EXTRA_IMAGE_URL, "");
            if (!imageUrl.isEmpty()) loadImage(imageUrl);

            String title = b.getString(EXTRA_TITLE, "");
            titleView.setText(title);
            getSupportActionBar().setTitle(title);

            CharSequence details = b.getCharSequence(EXTRA_BY_DETAILS, "");
            bylineView.setMovementMethod(new LinkMovementMethod());

            bylineView.setText(details);
            getLoaderManager().initLoader(LOADER_DETAILS_ID, null, this);


        }
    }

    private void loadImage(String url) {
        ImageLoaderHelper.getInstance(this).getImageLoader()
                .get(url, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                        imageView.setImageBitmap(imageContainer.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return ArticleLoader.newInstanceForItemId(this, mSelectedItemId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            mCursor = data;
            bodyView.setText(Html.fromHtml(mCursor.getString(ArticleLoader.Query.BODY).replaceAll("(\r\n|\n)", "<br />")));
            // todo make links clickable
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
