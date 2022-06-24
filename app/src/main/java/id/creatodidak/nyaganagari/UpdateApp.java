package id.creatodidak.nyaganagari;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * Created by BRIPDA ANGGI PERIANTO on 23,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/
public class UpdateApp extends AsyncTask<String,Void,Void> {
    private Context context;
    public void setContext(Context contextf){
        context = contextf;
    }

    @Override
    protected Void doInBackground(String... arg0) {
        try {
            URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            String PATH = Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsolutePath();
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "Appupdate.apk");
            if(outputFile.exists()){
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();
            outputFile.setReadable(true, false);

            //Intent intent = new Intent(Intent.ACTION_VIEW);
            Install();
        } catch (Exception e) {
            Log.e("UpdateAPP", "Update error! " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void Install() {
        try {
            String PATH = Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsolutePath();
            File file = new File(PATH + "/Appupdate.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) {

            Uri downloaded_apk = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
            intent.setDataAndType(downloaded_apk, "application/vnd.android.package-archive");
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                context.grantUriPermission(context.getApplicationContext().getPackageName() + ".provider", downloaded_apk, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);}

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
