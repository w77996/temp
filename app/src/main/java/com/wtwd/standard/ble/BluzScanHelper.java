package com.wtwd.standard.ble;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 蓝牙设备搜索连接管理类
 * Created by Administrator on 2018/1/23 0023.
 */

public class BluzScanHelper implements IBluzScanHelper, BluetoothLeService.OnConnectionStateChangeListener, BluetoothLeService.OnCharacteristicListener {
    private static final String TAG = "BluzScanHelper";
    private BluetoothLeService mBluetoothLeService;//蓝牙服务类
    private BluetoothAdapter bluetoothAdapter;//蓝牙适配器
    private BluetoothLeScanner mLeScanner = null;//api 5.0版本以上新的低功耗蓝牙搜索类
    private BleScanCallback mBleScanCallback = null;//api 5.0版本以上搜索回调接口

    private static IBluzScanHelper INSTANCE = null;
    private Activity mActivity = null;

    public Activity getActivity() {
        return mActivity;
    }

    private boolean isDiscovery = false;

    private boolean isDiscovery() {
        return isDiscovery;
    }

    private void setDiscovery(boolean discovery) {
        isDiscovery = discovery;
    }

    private static final int MSG_CONNECTED = 1;//连接
    private static final int MSG_DISCONNECTED = 2;//未连接
    /**
     * 10秒后停止查找搜索.
     */
    private static final int SCAN_PERIOD = 10 * 1000;

//    private SharePreferenceUtils preferenceUtils = null;

    /**
     * 使用handler返回主线程,避免UI层直接操作而导致的奔溃
     */
    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CONNECTED://连接
                    BluetoothDevice device = (BluetoothDevice) msg.obj;
                    if (connectionListenerList != null && connectionListenerList.size() > 0 && device != null) {
                        for (int i = 0; i < connectionListenerList.size(); i++) {
                            connectionListenerList.get(i).onConnected(device);
                        }
                    }
                    break;
                case MSG_DISCONNECTED://未连接
                    for (int i = 0; i < connectionListenerList.size(); i++) {
                        connectionListenerList.get(i).onDisconnected(null);
                    }
                    break;
            }
            return false;
        }
    });

    public static IBluzScanHelper getInstance(Activity activity) {
        if (INSTANCE == null) {
            synchronized (BluetoothLeService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BluzScanHelper(activity);
                }
            }
        }
        return INSTANCE;
    }

    private BluzScanHelper(Activity activity) {
        this.mActivity = activity;
        //蓝牙服务
        mBluetoothLeService = BluetoothLeService.getInstance(activity);
        mBluetoothLeService.setOnConnectionStateChangeListener(this);
        mBluetoothLeService.setOnCharacteristicListener(this);
        //蓝牙适配器
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            mBleScanCallback = new BleScanCallback();
        }
        //SharePreference保存蓝牙断开行为是主动断开还是自动断开
//        preferenceUtils = SharePreferenceUtils.getInstance(activity);
    }

    @Override
    public void registBroadcast(Context context) {
        Log.e(TAG, "注册监听系统蓝牙状态变化广播 ");
        IntentFilter filter = new IntentFilter();
        //蓝牙状态改变action
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        //蓝牙断开action
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        context.registerReceiver(receiver, filter);
    }

    @Override
    public void unregistBroadcast(Context context) {
        try {
            if (receiver != null) context.unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEnabled() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    @Override
    public boolean disable() {
        return bluetoothAdapter != null && bluetoothAdapter.disable();
    }

    @Override
    public boolean enable() {
        return bluetoothAdapter != null && bluetoothAdapter.enable();
    }

    @Override
    public void openBluetooth() {
        //打开蓝牙提示框
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        getActivity().startActivityForResult(enableBtIntent, 100);
    }

    @Override
    public void startDiscovery() {
        if (bluetoothAdapter == null) {
            Log.e(TAG, "开始搜索蓝牙失败，蓝牙适配器为null");
            return;
        }
        Log.e(TAG, "开始搜索蓝牙 isDiscovering=" + bluetoothAdapter.isDiscovering());
        //正在查找中，不做处理
        if (isDiscovery()) return;
        setDiscovery(true);
        //判断版本号,如果api版本号大于5.0则使用最新的方法搜素
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            scanLeDevice(true);
        } else {
            if (!bluetoothAdapter.isEnabled()) return;
            mLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopLeScan();
                }
            }, SCAN_PERIOD);
            mLeScanner.startScan(mBleScanCallback);
        }
    }

    @Override
    public void cancelDiscovery() {
        if (bluetoothAdapter == null) {
            Log.e(TAG, "取消搜索蓝牙失败，蓝牙适配器为null");
            return;
        }
//        if (!isDiscovery()) {
//            Logger.e("取消搜索蓝牙失败，当前状态为取消状态！");
//            return;
//        }
        setDiscovery(false);//复位正在查找标志位
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            scanLeDevice(false);
        } else {
            stopLeScan();
        }
        Log.e(TAG, "取消搜索蓝牙 isDiscovering=" + bluetoothAdapter.isDiscovering());
    }

    /**
     * 低功耗蓝牙开始查找蓝牙
     *
     * @param enable 是否开始查找
     */
    private void scanLeDevice(boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopLeDevice();
                }
            }, SCAN_PERIOD);
            bluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            stopLeDevice();

        }
    }

    /**
     * 低功耗蓝牙停止查找
     */
    private void stopLeDevice() {
        setDiscovery(false);
        bluetoothAdapter.stopLeScan(mLeScanCallback);
        for (int i = 0; i < discoveryListenerList.size(); i++) {
            discoveryListenerList.get(i).onDiscoveryFinished();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void stopLeScan() {
        setDiscovery(false);
        if (!bluetoothAdapter.isEnabled()) return;
        mLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        mLeScanner.stopScan(mBleScanCallback);
        for (int i = 0; i < discoveryListenerList.size(); i++) {
            discoveryListenerList.get(i).onDiscoveryFinished();
        }
    }

    @Override
    public boolean connect(BluetoothDevice device) {
        if (device == null || mBluetoothLeService == null) return false;
        cancelDiscovery();//取消搜索
        return mBluetoothLeService.connect(device.getAddress());
    }

    @Override
    public boolean connect(String address) {
        if (TextUtils.isEmpty(address) || mBluetoothLeService == null) {
            return false;
        }
        cancelDiscovery();
        return mBluetoothLeService.connect(address);
    }

    @Override
    public void disconnect() {
        if (mBluetoothLeService != null) {
            mBluetoothLeService.disconnect();
//            preferenceUtils.save(SharePreferenceUtils.SHARE_REFRESH_IS_MANUAL, true);
        }
    }

    @Override
    public void connected(BluetoothDevice device) {
        Message message = new Message();
        message.obj = device;
        message.what = MSG_CONNECTED;
        mHandler.sendMessage(message);
//        preferenceUtils.save(SharePreferenceUtils.SHARE_REFRESH_IS_MANUAL, false);
        cancelDiscovery();//连接成功后取消搜索
    }

    @Override
    public void disconnected() {
        mHandler.sendEmptyMessage(MSG_DISCONNECTED);
    }

    @Override
    public void release() {
        if (mBluetoothLeService != null) {
            mBluetoothLeService.disconnect();
            mBluetoothLeService.close();
        }
    }

    @Override
    public BluetoothDevice getConnectedDevice() {
        return mBluetoothLeService.getDevice();
    }

    private List<OnDiscoveryListener> discoveryListenerList = new ArrayList<>();//查找蓝牙回调接口集合
    private List<OnConnectionListener> connectionListenerList = new ArrayList<>();//连接蓝牙回调接口集合

    @Override
    public void addOnDiscoveryListener(OnDiscoveryListener listener) {
        if (!discoveryListenerList.contains(listener))
            discoveryListenerList.add(listener);
    }

    @Override
    public void removeOnDiscoveryListener(OnDiscoveryListener listener) {
        discoveryListenerList.remove(listener);
    }

    @Override
    public void addOnConnectionListener(OnConnectionListener listener) {
        connectionListenerList.add(listener);
    }

    @Override
    public void removeOnConnectionListener(OnConnectionListener listener) {
        connectionListenerList.remove(listener);
    }

    /**
     * 查找低功耗蓝牙接口回调
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            //注意在此方法中不要过多的操作
            if (device != null && discoveryListenerList != null) {
                for (int i = 0; i < discoveryListenerList.size(); i++) {
                    discoveryListenerList.get(i).onFound(device);
                }
            }
        }
    };

    @Override
    public void onServicesDiscovered() {
        Log.e(TAG, "bluz scan helper onservice discovered");
        for (int i = 0; i < connectionListenerList.size(); i++) {
            connectionListenerList.get(i).onServiceDiscovery();
        }
//        List<BluetoothGattService> list = mBluetoothLeService.getSupportedGattServices();
//        Log.e(TAG, "service size : " + list.size());
//        for (BluetoothGattService bluetoothGattService : list) {
//            Log.e(TAG, "service uuid : " + bluetoothGattService.getUuid());
//            List<BluetoothGattCharacteristic> cList = bluetoothGattService.getCharacteristics();
//            for (int i = 0; i < cList.size(); i++) {
//                Log.e(TAG, "characteristic uuid : " + cList.get(i).getUuid());
//            }
//        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.d(TAG, "蓝牙数据读取回调 status=" + status);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        BluetoothDevice device = mBluetoothLeService.getDevice();//当前连接的蓝牙对象
        if (characteristic == null || characteristic.getValue() == null || device == null) return;
        Log.e(TAG, "read changed : " + Arrays.toString(characteristic.getValue()));
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.d(TAG, "蓝牙数据写入回调 status=" + status);
        if (status == BluetoothGatt.GATT_SUCCESS) {//数据写入成功，写入下一条指令
//            mWriteThread.threadNotify(true);
        } else {
//            mWriteThread.threadNotify(false);
        }
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
//        if (listener != null) listener.onReadRemoteRssi(gatt, rssi, status);
    }

    /**
     * api21+低功耗蓝牙接口回调，以下回调的方法可以根据需求去做相应的操作
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class BleScanCallback extends ScanCallback {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (result == null) return;
            BluetoothDevice device = result.getDevice();
            if (device != null && discoveryListenerList != null) {
                for (int i = 0; i < discoveryListenerList.size(); i++) {
                    discoveryListenerList.get(i).onFound(device);
                }
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    }

    /**
     * 查找蓝牙广播回调
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "蓝牙广播回调 action=" + action);
            if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_OFF) {//关闭系统蓝牙
                setDiscovery(false);
                Log.e(TAG, "系统蓝牙断开！！");
                boolean isEnable = enable();
                if (!isEnable) openBluetooth();
            } else if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_ON) {//系统蓝牙打开
                setDiscovery(false);
                Log.e(TAG, "系统蓝牙打开！！");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startDiscovery();
                    }
                }, 500);
            }
        }
    };
}
