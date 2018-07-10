package com.example.w450d867.androidrobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.*;
import java.io.*;
import java.util.Set;
import android.util.Log;
import android.os.ParcelUuid;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    OutputStream outStream;
    InputStream inStream;
    BluetoothAdapter blueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                /*Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

                if(bondedDevices.size() > 0) {
                    Object[] devices = (Object []) bondedDevices.toArray();
                    BluetoothDevice ourDevice = (BluetoothDevice) devices[0];
                    for (Object device : devices) {
                        Log.e("name:", ((BluetoothDevice)device).getName());
                        BluetoothDevice temp = (BluetoothDevice) device;
                        if ( temp.getName().equals("Test") ){
                            ourDevice = temp;
                        }
                    }
                    ParcelUuid[] uuids = ourDevice.getUuids();*/

                    BluetoothDevice ourDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice("EF:2A:B9:D2:A5:37");
                    ParcelUuid[] uuids = ourDevice.getUuids();
                    Log.e("name:", ourDevice.getName());

                    BluetoothSocket socket;
                    try{
                        socket = ourDevice.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                        socket.connect();
                        outStream = socket.getOutputStream();
                        inStream = socket.getInputStream();
                    }catch(IOException ie) {
                        ie.printStackTrace();
                    }
               // }
                Log.e("error", "No appropriate paired devices.");
            } else {
                Log.e("error", "Bluetooth is disabled.");
            }
        }

        try{
            bluetoothWrite("Test message");
        }catch(IOException ie){
            ie.printStackTrace();
        }

        SeekBar seekBarLeft = (SeekBar)findViewById(R.id.seekBarLeft);
        SeekBar seekBarRight = (SeekBar)findViewById(R.id.seekBarRight);

    }

    public void bluetoothWrite(String s) throws IOException {
        if (outStream==null){
            Log.e("error","Output stream null");
        }else {
            outStream.write(s.getBytes());
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        try {
            if (seekBar.getId() == R.id.seekBarLeft) {
                bluetoothWrite("left:" + progress);
            } else if (seekBar.getId() == R.id.seekBarLeft) {
                bluetoothWrite("right:" + progress);
            }
        }catch(IOException ie){
            ie.printStackTrace();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}