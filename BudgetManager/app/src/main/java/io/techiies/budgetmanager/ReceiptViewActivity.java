package io.techiies.budgetmanager;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.io.File;

public class ReceiptViewActivity  extends AppCompatActivity
{
    private static final String TAG = "Budget Manager";

    private String receiptFilename = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.receipt_view_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final Bundle b = getIntent().getExtras();
        receiptFilename = b.getString("receipt");

        final WebView receiptView = (WebView) findViewById(R.id.imageView);
        receiptView.getSettings().setBuiltInZoomControls(true);
        receiptView.getSettings().setAllowFileAccess(true);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        String data = "<html><body>" +
                "<img width=\"" + size.x + "\" " +
                "src=\"file://" + receiptFilename + "\"/>" +
                "</body></html>";

        receiptView.loadDataWithBaseURL("", data, "text/html", "utf-8", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.share_menu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        // Fetch ShareActionProvider
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (shareActionProvider == null)
        {
            Log.w(TAG, "Failed to find share action provider");
            return false;
        }

        if(receiptFilename == null)
        {
            Log.w(TAG, "No receipt to share");
            return false;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        // Determine mimetype of image
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(receiptFilename, opt);
        shareIntent.setType(opt.outMimeType);

        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(receiptFilename)));
        shareActionProvider.setShareIntent(shareIntent);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
