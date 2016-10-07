package ch.jmelab.appquest_memory;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoryActivity extends AppCompatActivity {
    private static final int SCAN_QR_CODE_REQUEST_CODE = 0;

    //private List<MemoryCardPair> mMemoryCardPairs;
    private List<MemoryCard> mMemoryCards;
    private int mActiveFieldY;
    private int mActiveFieldX;

    private RecyclerView mMemoryRecyclerView;
    private RecyclerView.Adapter mMemoryAdapter;
    private RecyclerView.LayoutManager mMemoryLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMemoryCards = new ArrayList<>();

        //TODO buttons add pair delete pair

        mMemoryRecyclerView = (RecyclerView)findViewById(R.id.memory_recycler_view);
        mMemoryLayoutManager = new GridLayoutManager(this , 2);
        mMemoryRecyclerView.setLayoutManager(mMemoryLayoutManager);
        mMemoryAdapter = new MemoryAdapter(mMemoryCards);
        mMemoryRecyclerView.setAdapter(mMemoryAdapter);

        setButtonClickEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add("Log");
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               //TODO
                //log(mMemoryCards)
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void takeQrCodePicture() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(MyCaptureActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setOrientationLocked(false);
        integrator.addExtra(Intents.Scan.BARCODE_IMAGE_ENABLED, true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MemoryActivity", "onActivityResult: ");
        if (requestCode == IntentIntegrator.REQUEST_CODE
                && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            String path = extras.getString(
                    Intents.Scan.RESULT_BARCODE_IMAGE_PATH);
            String code = extras.getString(
                    Intents.Scan.RESULT);

            MemoryCard memoryCard = new MemoryCard(path, code);
            mMemoryCards.add(memoryCard);
            mMemoryRecyclerView.forceLayout();

            Bitmap bmp = BitmapFactory.decodeFile(path);

            FileOutputStream outputStream = null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
            Date currentDateTime = new Date();
            String filename = "Memory" + simpleDateFormat.format(currentDateTime);
            try {
                outputStream = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);

                bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void log(ArrayList<MemoryCard> memoryCards) throws JSONException {
        Intent intent = new Intent("ch.appquest.intent.LOG");

        if (getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).isEmpty()) {
            Toast.makeText(this, "Logbook App not installed", Toast.LENGTH_LONG).show();
            return;
        }

        String solution = "[";
        for (int i = 0; i < memoryCards.size();i+=2) {
            solution += "[\"";
            solution += memoryCards.get(i).getCode();
            solution += "\", \"";
            solution += memoryCards.get(i+1).getCode();
            solution += "\"]";
            if (i < memoryCards.size()) {
                solution += ", ";
            }
        }
        solution += "]";

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("task", "Memory");
        jsonMessage.put("solution", solution);
        String message = jsonMessage.toString();

        intent.putExtra("ch.appquest.logmessage", message);

        startActivity(intent);
    }

    private void setButtonClickEvent() {
        ((Button)findViewById(R.id.scanmemory)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeQrCodePicture();
            }
        });
    }
}
