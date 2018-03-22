package com.wtwd.standard.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 * 设备管理类
 * Created by Administrator on 2018/1/23 0023.
 */

public class BluzManager implements BluetoothLeService.OnCharacteristicListener {
    private static final String TAG = "BluzManager";
    private static BluzManager INSTANCE;
    private Context mContext;
    /**
     * 蓝牙管理类对象
     */
    private BluetoothLeService mBluetoothLeService;
    /**
     * 设备信号强度监听
     */
    private OnReadRemoteRssiListener mOnReadRemoteRssiListener;


    /**
     * 缓存连接设备的蓝牙特征值和uuid
     */
    private HashMap<UUID, BluetoothGattCharacteristic> mWriteHashMap = new HashMap<>();

    private Context getContext() {
        return mContext;
    }

    /**
     * 蓝牙信号强度回调接口
     */

    public interface OnReadRemoteRssiListener {
        void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status);
    }

    public void setOnReadRemoteRssiListener(OnReadRemoteRssiListener mOnReadRemoteRssiListener) {
        this.mOnReadRemoteRssiListener = mOnReadRemoteRssiListener;
    }

    private BluzManager(Context context) {
        mContext = context;
        initialize();
    }

    /**
     * 单列模式，确保唯一性
     *
     * @param context 上下文
     * @return BluzManager蓝牙管理类
     */
    public static BluzManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BluzManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BluzManager(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化相关数据
     */
    private void initialize() {
        //初始化蓝牙服务类
        mBluetoothLeService = BluetoothLeService.getInstance(getContext());
        mBluetoothLeService.setOnCharacteristicListener(this);
    }

    /**
     * 获取蓝牙信号强度,只能单次获取
     *
     * @return boolean
     */
    public boolean getRssiValue() {
        return mBluetoothLeService.getRssiValue();
    }

    /**
     * 清楚所有缓存数据
     */
    private void clearHashMap() {
        mWriteHashMap.clear();
    }

    /**
     * 蓝牙服务发现回调
     */
    @Override
    public void onServicesDiscovered() {
        clearHashMap();
        List<BluetoothGattService> list = mBluetoothLeService.getSupportedGattServices();
        Log.e(TAG, "service size : " + list.size());
    }

    /**
     * 蓝牙读取数据回调
     */
    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.d(TAG, "蓝牙数据读取回调 status=" + status);
    }

    /**
     * 蓝牙设备通知数据变化回调
     */
    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        BluetoothDevice device = mBluetoothLeService.getDevice();//当前连接的蓝牙对象
        if (characteristic == null || characteristic.getValue() == null || device == null) return;
        Log.e(TAG, "read changed : " + Arrays.toString(characteristic.getValue()));
    }

    /**
     * 向设备写入数据回调
     */
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
        if (mOnReadRemoteRssiListener != null)
            mOnReadRemoteRssiListener.onReadRemoteRssi(gatt, rssi, status);
    }

    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        mBluetoothLeService.readCharacteristic(characteristic);
    }

    public List<BluetoothGattService> getBluetoothGattService() {
        Log.e(TAG, "get bluetooth gatt service");
        return mBluetoothLeService.getSupportedGattServices();
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enable) {
        mBluetoothLeService.setCharacteristicNotification(characteristic, enable);
    }

    public void writeDescriptor(BluetoothGattDescriptor descriptor) {
        mBluetoothLeService.writeDescriptor(descriptor);
    }


}
