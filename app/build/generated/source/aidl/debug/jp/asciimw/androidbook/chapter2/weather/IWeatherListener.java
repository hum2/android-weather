/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/shuhei/AndroidStudioProjects/Weather/app/src/main/aidl/jp/asciimw/androidbook/chapter2/weather/IWeatherListener.aidl
 */
package jp.asciimw.androidbook.chapter2.weather;
public interface IWeatherListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements jp.asciimw.androidbook.chapter2.weather.IWeatherListener
{
private static final java.lang.String DESCRIPTOR = "jp.asciimw.androidbook.chapter2.weather.IWeatherListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an jp.asciimw.androidbook.chapter2.weather.IWeatherListener interface,
 * generating a proxy if needed.
 */
public static jp.asciimw.androidbook.chapter2.weather.IWeatherListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof jp.asciimw.androidbook.chapter2.weather.IWeatherListener))) {
return ((jp.asciimw.androidbook.chapter2.weather.IWeatherListener)iin);
}
return new jp.asciimw.androidbook.chapter2.weather.IWeatherListener.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_onWeatherReceived:
{
data.enforceInterface(DESCRIPTOR);
jp.asciimw.androidbook.chapter2.weather.Weather _arg0;
if ((0!=data.readInt())) {
_arg0 = jp.asciimw.androidbook.chapter2.weather.Weather.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.onWeatherReceived(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements jp.asciimw.androidbook.chapter2.weather.IWeatherListener
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void onWeatherReceived(jp.asciimw.androidbook.chapter2.weather.Weather weather) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((weather!=null)) {
_data.writeInt(1);
weather.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onWeatherReceived, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onWeatherReceived = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void onWeatherReceived(jp.asciimw.androidbook.chapter2.weather.Weather weather) throws android.os.RemoteException;
}
