# AppQuest Memory

![HSR](http://appquest.hsr.ch/images/fho.png)

## About

AppQuest Memory is the 2. application for the [App Quest 2016](http://appquest.hsr.ch/) Treasure Hunt. The application must be able to take pictures, scan the QR-codes and store them in pairs. While the Treasure Hunt the goal will be to find as many pairs as possible. Finally it must be possible to upload all the bundled QR-code strings to the AppQuest LogBook.

### General
|   |  |
|---|---|
| AppQuest Repository | [AppQuest 2016](https://github.com/mirioeggmann/appquest) |
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

## License
[MIT License](https://github.com/mirioeggmann/appquest-memory/blob/master/LICENSE)
