package ch.jmelab.appquest_memory;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MemoryActivity extends AppCompatActivity {
    private static final int SCAN_QR_CODE_REQUEST_CODE = 0;

    private List<MemoryCard> mMemoryCards;
    public int clickedField;

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
        mMemoryCards.add(new MemoryCard());

        mMemoryRecyclerView = (RecyclerView)findViewById(R.id.memory_recycler_view);
        mMemoryRecyclerView.setHasFixedSize(false);
        mMemoryLayoutManager = new GridLayoutManager(this , 2);
        mMemoryRecyclerView.setLayoutManager(mMemoryLayoutManager);
        mMemoryAdapter = new MemoryAdapter(mMemoryCards,getApplicationContext(),this);
        mMemoryRecyclerView.setAdapter(mMemoryAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add("Log");
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    log(mMemoryCards);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            mMemoryCards.get(clickedField).setCode(code);
            mMemoryCards.get(clickedField).setPath(path);
            if(clickedField % 2 == 0 && clickedField == mMemoryCards.size() -1) {
                mMemoryCards.add(new MemoryCard());
                mMemoryCards.add(new MemoryCard());
            }

            mMemoryAdapter.notifyDataSetChanged();


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

    private void log(List<MemoryCard> memoryCards) throws JSONException {
        Intent intent = new Intent("ch.appquest.intent.LOG");

        if (getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).isEmpty()) {
            Toast.makeText(this, "Logbook App not installed", Toast.LENGTH_LONG).show();
            return;
        }
        JSONArray jsonArrayMain = new JSONArray();
        for (int i = 0; i < memoryCards.size();i+=2) {
            if (i+1 <= mMemoryCards.size() -1) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(memoryCards.get(i).getCode());
                jsonArray.put(memoryCards.get(i + 1).getCode());
                jsonArrayMain.put(jsonArray);
            }
        }
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("task", "Memory");
        jsonMessage.put("solution", jsonArrayMain);
        String message = jsonMessage.toString();

        intent.putExtra("ch.appquest.logmessage", message);

        startActivity(intent);
    }
}
