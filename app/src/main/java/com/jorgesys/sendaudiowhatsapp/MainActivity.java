package com.jorgesys.sendaudiowhatsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnSendAudio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWhatsAppAudio();
            }
        });

    }

    private void sendWhatsAppAudio(){
        try {
            //Copy file to external ExternalStorage.
            String mediaPath = copyFiletoExternalStorage(R.raw.jorgesys_sound, "jorgesys_sound.mp3");

            Intent shareMedia = new Intent(Intent.ACTION_SEND);
            //set WhatsApp application.
            shareMedia.setPackage("com.whatsapp");
            shareMedia.setType("audio/*");
            //set path of media file in ExternalStorage.
            shareMedia.putExtra(Intent.EXTRA_STREAM, Uri.parse(mediaPath));
            startActivity(Intent.createChooser(shareMedia, "Compartiendo archivo."));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Whatsapp no se encuentra instalado", Toast.LENGTH_LONG).show();
        }
    }

    private String copyFiletoExternalStorage(int resourceId, String resourceName){
        String pathSDCard = Environment.getExternalStorageDirectory() + "/Android/data/" + resourceName;
        try{
            InputStream in = getResources().openRawResource(resourceId);
            FileOutputStream out = null;
            out = new FileOutputStream(pathSDCard);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  pathSDCard;
    }
    
}
