# AppQuest Memory

![PostFinance AG](https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/PostFinance_Logo.svg/langfr-330px-PostFinance_Logo.svg.png)
![HSR](http://appquest.hsr.ch/images/fho.png)

## About

AppQuest Memory is the 2. application for the [App Quest 2016](http://appquest.hsr.ch/) Treasure Hunt. The application must be able to take pictures, scan the QR-codes and store them in pairs. While the Treasure Hunt the goal will be to find as many pairs as possible. Finally it must be possible to upload all the bundled pair QR-codes strings to the AppQuest LogBook.

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
GridLayoutManager gridLayoutManager = new GridLayoutManager(this /* the activity */, 2);
rv.setLayoutManager(gridLayoutManager);
```

---

Logbook format
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

### Apps
- [x] 1. App [Metal Detector](https://github.com/luvirx/appquest-metal-detector) Deadline
- [x] 2. App [Memory](https://github.com/luvirx/appquest-memory)
- [ ] 3. App [Treasure Map]()
- [ ] 4. App [Pedometer]()
- [ ] 5. App [Pixel Painter]()

## License
MIT License

Copyright (c) 2016 Mirio Eggmann, Timon Borter

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
