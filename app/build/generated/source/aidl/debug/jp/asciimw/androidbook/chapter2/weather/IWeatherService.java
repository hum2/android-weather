/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/shuhei/AndroidStudioProjects/Weather/app/src/main/aidl/jp/asciimw/androidbook/chapter2/weather/IWeatherService.aidl
 */
package jp.asciimw.androidbook.chapter2.weather;
public interface IWeatherService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements jp.asciimw.androidbook.chapter2.weather.IWeatherService
{
private static final java.lang.String DESCRIPTOR = "jp.asciimw.androidbook.chapter2.weather.IWeatherService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an jp.asciimw.androidbook.chapter2.weather.IWeatherService interface,
 * generating a proxy if needed.
 */
public static jp.asciimw.androidbook.chapter2.weather.IWeatherService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof jp.asciimw.androidbook.chapter2.weather.IWeatherService))) {
return ((jp.asciimw.androidbook.chapter2.weather.IWeatherService)iin);
}
return new jp.asciimw.androidbook.chapter2.weather.IWeatherService.Stub.Proxy(obj);
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
case TRANSACTION_addWeatherListener:
{
data.enforceInterface(DESCRIPTOR);
jp.asciimw.androidbook.chapter2.weather.IWeatherListener _arg0;
_arg0 = jp.asciimw.androidbook.chapter2.weather.IWeatherListener.Stub.asInterface(data.readStrongBinder());
this.addWeatherListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeWeatherListener:
{
data.enforceInterface(DESCRIPTOR);
jp.asciimw.androidbook.chapter2.weather.IWeatherListener _arg0;
_arg0 = jp.asciimw.androidbook.chapter2.weather.IWeatherListener.Stub.asInterface(data.readStrongBinder());
this.removeWeatherListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_execute:
{
data.enforceInterface(DESCRIPTOR);
this.execute();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements jp.asciimw.androidbook.chapter2.weather.IWeatherService
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
@Override public void addWeatherListener(jp.asciimw.androidbook.chapter2.weather.IWeatherListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addWeatherListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeWeatherListener(jp.asciimw.androidbook.chapter2.weather.IWeatherListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeWeatherListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void execute() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_execute, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_addWeatherListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_removeWeatherListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_execute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void addWeatherListener(jp.asciimw.androidbook.chapter2.weather.IWeatherListener listener) throws android.os.RemoteException;
public void removeWeatherListener(jp.asciimw.androidbook.chapter2.weather.IWeatherListener listener) throws android.os.RemoteException;
public void execute() throws android.os.RemoteException;
}
