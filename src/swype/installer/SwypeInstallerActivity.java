package swype.installer;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SwypeInstallerActivity extends Activity {
	//private final String SWIPEDICT = "/data/data/com.swype.android.inputmethod/swypedata/udb/UserData.udb";
    //private final String SWIPEDICT_BACKUP = "/sdcard/Android/Backup/Swype/UserData.udb";
    private final String DIRNAME = "/Android/backup/SwypeInstaller";
	private static final String TAG = "SwypeInstaller"; 
	
    @Override
     protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.main);
        
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
                    String root = Environment.getExternalStorageDirectory().toString();            		
            		new File(root + DIRNAME).mkdirs();
            		}
            	try {
                	Process copy = Runtime.getRuntime().exec(new String[] { "su", "-c", "cp -f /data/data/com.swype.android.inputmethod/swypedata/udb/UserData.udb /sdcard/Android/backup/SwypeInstaller"});
                	copy.waitFor();
                	Toast.makeText(getApplicationContext(), R.string.Backup, Toast.LENGTH_SHORT).show();
            		}  catch (Exception ex) {
            			Log.i(TAG, "Could not Backup UserData File", ex);
            			}
            	
            }
        });
        
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
                	Process copy = Runtime.getRuntime().exec(new String[] { "su", "-c", "cp -f /sdcard/Android/backup/SwypeInstaller/UserData.udb /data/data/com.swype.android.inputmethod/swypedata/udb"});
                	copy.waitFor();
                	Process chmod = Runtime.getRuntime().exec(new String[] { "su", "-c", "chmod 666 /data/data/com.swype.android.inputmethod/swypedata/udb/UserData.udb"});
                	chmod.waitFor();	
                	Toast.makeText(getApplicationContext(), R.string.Restore, Toast.LENGTH_SHORT).show();	
            		}  catch (Exception ex) {
            			Log.i(TAG, "Could not Restore UserData File", ex);
            			}
            	
            }
        });
    }
    
    
    			
    
    //для вывода списка бекапов
    //new File(Environment.getExternalStorageDirectory(), "Android/backup/SwypeInstaller").listFiles()
}