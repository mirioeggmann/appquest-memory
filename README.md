# AppQuest Memory

![PostFinance AG](https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/PostFinance_Logo.svg/langfr-330px-PostFinance_Logo.svg.png)
![HSR](http://appquest.hsr.ch/images/fho.png)

## About

AppQuest Memory is the 2. application for the [App Quest 2016](http://appquest.hsr.ch/) Treasure Hunt. The application must be able to take pictures, scan the QR-codes and store them in pairs. While the Treasure Hunt the goal will be to find as many pairs as possible. Finally it must be possible to upload all the bundled QR-code strings to the AppQuest LogBook.

### General
|   |  |
|---|---|
| Application Requirements | http://appquest.hsr.ch/2016/memory |
| Minimum API Level | [API level 23 (Marshmallow)](https://developer.android.com/about/versions/marshmallow/android-6.0.html) |
| Development Environment | [Android Studio](https://developer.android.com/studio/index.html) |

### Example
![AppQuest Memory App](http://appquest.hsr.ch/2016/wp-content/uploads/Screenshot_20160513-111219-254x300.png)
![Android Bot 1 QR Code](http://appquest.hsr.ch/2016/wp-content/uploads/IMG_20160811_141119-300x224.jpg)
![Android Bot 2 QR Code](http://appquest.hsr.ch/2016/wp-content/uploads/IMG_20160811_141145-300x224.jpg)

### Given code snippets
[build.gradle](https://gist.github.com/misto/9401c90cd3b499493708e481e14808d9#file-build-gradle)
```
dependencies {
    ...
    compile 'com.journeyapps:zxing-android-embedded:3.3.0'
}
```

---

[MyActivity.java](https://gist.github.com/misto/fe6e36338b5a29ec7e9f1765a20ac41e#file-myactivity-java)
```java
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
    if (requestCode == IntentIntegrator.REQUEST_CODE 
        && resultCode == RESULT_OK) {

        Bundle extras = data.getExtras();
        String path = extras.getString(
            Intents.Scan.RESULT_BARCODE_IMAGE_PATH);
        
        // Ein Bitmap zur Darstellung erhalten wir so:
        // Bitmap bmp = BitmapFactory.decodeFile(path)
        
        String code = extras.getString(
            Intents.Scan.RESULT);
    }
}
```

---

[MyCaptureActivity.java](https://gist.github.com/misto/f2bb63ef256eea26c10b0a4925b74a54#file-mycaptureactivity-java)
```java
import com.journeyapps.barcodescanner.CaptureActivity;

public class MyCaptureActivity extends CaptureActivity { }
```

---

RecyclerView with 2 columns
```java
RecyclerView rv = (RecyclerView)findViewById(R.id.recyclerView);
GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
rv.setLayoutManager(gridLayoutManager);
```

---

AppQuest Logbuch format
```
{
  "task": "Memory",
  "solution": [["$Wort1","$Wort2"], ["$Wort3", "$Wort4"], ...]
}
```

## App Quest 2016

### General

|   |  |
|---|---|
| Team | MeIsTeam |
| Team members | [Mirio Eggmann](https://github.com/luvirx), [Timon Borter](https://github.com/bbortt) |
| Employer | [PostFinance AG](https://www.postfinance.ch/) |
| Coach | [Rafael Krucker](mailto:rkrucker@hsr.ch) |
| Organizer | [Mirko Stocker](https://github.com/misto) |
| Operating System | Android |
| Required Apps | [AppQuest Logbuch](http://appquest.hsr.ch/logbuch.apk), [Barcode Scanner](https://play.google.com/store/apps/details?id=com.google.zxing.client.android)|
| Logbuch Test Account | user: HSR-TEST pw: 482ae9 |

### Apps
- [x] 1. App [Metal Detector](https://github.com/luvirx/appquest-metal-detector) (deadline: none)
- [x] 2. App [Memory](https://github.com/luvirx/appquest-memory) (deadline: 8.10.2016)
- [ ] 3. App [Treasure Map]() (deadline: 29.10.2016)
- [ ] 4. App [Pedometer]() (deadline: 19.11.2016)
- [ ] 5. App [Pixel Painter]() (deadline: none)

### Given code snippets

[ZXing Barcode Scanner](https://gist.github.com/misto/3938337#file-gistfile1-java)
```java
private static final int SCAN_QR_CODE_REQUEST_CODE = 0;

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	MenuItem menuItem = menu.add("Log");
	menuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, SCAN_QR_CODE_REQUEST_CODE);
			return false;
		}
	});

	return super.onCreateOptionsMenu(menu);
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	if (requestCode == SCAN_QR_CODE_REQUEST_CODE) {
		if (resultCode == RESULT_OK) {
			String logMsg = intent.getStringExtra("SCAN_RESULT");
			// Weiterverarbeitung..
		}
	}
}
```

---

[Logbuch](https://gist.github.com/misto/3938488#file-gistfile1-java)
```java
private void log(String qrCode) {
	Intent intent = new Intent("ch.appquest.intent.LOG");

	if (getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).isEmpty()) {
		Toast.makeText(this, "Logbook App not Installed", Toast.LENGTH_LONG).show();
		return;
	}

	// Achtung, je nach App wird etwas anderes eingetragen
	String logmessage = ...
	intent.putExtra("ch.appquest.logmessage", logmessage);

	startActivity(intent);
}
```

## License
[MIT License](https://github.com/luvirx/appquest-memory/blob/master/LICENSE)
