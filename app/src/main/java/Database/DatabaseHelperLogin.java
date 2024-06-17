package Database;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.quanlynhatro.HopDong;
import com.example.quanlynhatro.HopDongCLass;
import com.example.quanlynhatro.KhachThue;
import com.example.quanlynhatro.Room;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperLogin extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "login.db";
    private static final int DATABASE_VERSION = 5;

    // Users table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Rooms table
    public static final String TABLE_ROOMS = "rooms";
    public static final String COLUMN_ROOM_ID = "id";
    public static final String COLUMN_ROOM_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_ACREAGE = "acreage";
    public static final String COLUMN_STATUS = "status";

    // KhachThue table
    public static final String TABLE_KHACH_THUE = "khachthue";
    public static final String COLUMN_KHACH_THUE_ID = "idkhachthue";
    public static final String COLUMN_KHACH_THUE_NAME = "name";
    public static final String COLUMN_KHACH_THUE_GENDER = "gender";
    public static final String COLUMN_KHACH_THUE_PHONE = "phone";
    public static final String COLUMN_KHACH_THUE_CMND = "cmnd";
    public static final String COLUMN_KHACH_THUE_ROOM_ID = "idroom";

    public static final String TABLE_HOPDONG = "hopdong";
    public static final String COLUMN_HOPDONG_ID = "idhopdong";
    public static final String COLUMN_HOPDONG_ROOM_NAME = "room_name";
    public static final String COLUMN_HOPDONG_RENT_NAME = "rent_name";
    public static final String COLUMN_HOPDONG_PRICE = "price";
    public static final String COLUMN_HOPDONG_START_DATE = "start_date";
    public static final String COLUMN_HOPDONG_END_DATE = "end_date";

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL)";

    private static final String CREATE_TABLE_ROOMS = "CREATE TABLE " + TABLE_ROOMS + " ("
            + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ROOM_NAME + " TEXT NOT NULL, "
            + COLUMN_PRICE + " TEXT NOT NULL, "
            + COLUMN_QUANTITY + " TEXT NOT NULL, "
            + COLUMN_ACREAGE + " TEXT NOT NULL, "
            + COLUMN_STATUS + " TEXT NOT NULL)";

    private static final String CREATE_TABLE_KHACH_THUE = "CREATE TABLE " + TABLE_KHACH_THUE + " ("
            + COLUMN_KHACH_THUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_KHACH_THUE_NAME + " TEXT NOT NULL, "
            + COLUMN_KHACH_THUE_GENDER + " TEXT NOT NULL, "
            + COLUMN_KHACH_THUE_PHONE + " TEXT NOT NULL, "
            + COLUMN_KHACH_THUE_CMND + " TEXT NOT NULL, "
            + COLUMN_KHACH_THUE_ROOM_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + COLUMN_KHACH_THUE_ROOM_ID + ") REFERENCES " + TABLE_ROOMS + "(" + COLUMN_ROOM_ID + "))";

    private static final String CREATE_TABLE_HOPDONG = "CREATE TABLE " + TABLE_HOPDONG + " ("
            + COLUMN_HOPDONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_HOPDONG_ROOM_NAME + " TEXT NOT NULL, "
            + COLUMN_HOPDONG_RENT_NAME + " TEXT NOT NULL, "
            + COLUMN_HOPDONG_PRICE + " TEXT NOT NULL, "
            + COLUMN_HOPDONG_START_DATE + " TEXT NOT NULL, "
            + COLUMN_HOPDONG_END_DATE + " TEXT NOT NULL)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ROOMS);
        db.execSQL(CREATE_TABLE_KHACH_THUE);
        db.execSQL(CREATE_TABLE_HOPDONG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KHACH_THUE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOPDONG);
        onCreate(db);
    }

    public DatabaseHelperLogin(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public long addRoom(String name, String price, String quantity, String acreage, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_ACREAGE, acreage);
        values.put(COLUMN_STATUS, status);
        long id = db.insert(TABLE_ROOMS, null, values);
        db.close();
        return id;
    }

    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ROOMS, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Room room = new Room(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ACREAGE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_STATUS))
                );
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;
    }

    public Room getRoomById(int roomId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Room room = null;

        String query = "SELECT * FROM " + TABLE_ROOMS +
                " WHERE " + COLUMN_ROOM_ID + " = " + roomId;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_NAME));
            @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
            @SuppressLint("Range") String area = cursor.getString(cursor.getColumnIndex(COLUMN_ACREAGE));
            @SuppressLint("Range") String quantity = cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY));
            @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));

            room = new Room(id, name, price, area, quantity, status);
        }

        cursor.close();
        db.close();

        return room;
    }

    public String getRoomNameById(int roomId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String roomName = null;

        String query = "SELECT " + COLUMN_ROOM_NAME + " FROM " + TABLE_ROOMS + " WHERE " + COLUMN_ROOM_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(roomId)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_NAME));
            roomName = name;
        }

        cursor.close();
        db.close();

        return roomName;
    }

    public long addKhachThue(String name, String gender, String phone, String cmnd, int roomId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KHACH_THUE_NAME, name);
        values.put(COLUMN_KHACH_THUE_GENDER, gender);
        values.put(COLUMN_KHACH_THUE_PHONE, phone);
        values.put(COLUMN_KHACH_THUE_CMND, cmnd);
        values.put(COLUMN_KHACH_THUE_ROOM_ID, roomId);
        long id = db.insert(TABLE_KHACH_THUE, null, values);
        db.close();
        return id;
    }

    public ArrayList<KhachThue> getAllKhachThueForRoom(int roomId) {
        ArrayList<KhachThue> khachThueList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_KHACH_THUE + " WHERE " + COLUMN_KHACH_THUE_ROOM_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(roomId)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_KHACH_THUE_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_KHACH_THUE_NAME));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(COLUMN_KHACH_THUE_GENDER));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(COLUMN_KHACH_THUE_PHONE));
                @SuppressLint("Range") String cmnd = cursor.getString(cursor.getColumnIndex(COLUMN_KHACH_THUE_CMND));

                KhachThue khachThue = new KhachThue(id, name, gender, phone, cmnd, roomId);
                khachThueList.add(khachThue);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return khachThueList;
    }

    public void updateRoomStatus(int roomId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, newStatus);
        db.update(TABLE_ROOMS, values, COLUMN_ROOM_ID + " = ?", new String[]{String.valueOf(roomId)});
        db.close();
    }

    public boolean deleteRoom(int roomId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_ROOMS, COLUMN_ROOM_ID + " = ?", new String[]{String.valueOf(roomId)});
        db.close();
        return rowsDeleted > 0;
    }

    public boolean checkRoom(int roomId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_KHACH_THUE + " WHERE " + COLUMN_KHACH_THUE_ROOM_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(roomId)});

        boolean isOccupied = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isOccupied = (count > 0);
        }
        cursor.close();
        db.close();

        return isOccupied;
    }

    public boolean checkRoomName(String roomName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_ROOMS + " WHERE " + COLUMN_ROOM_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{roomName});

        boolean exists = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            exists = (count > 0);
        }
        cursor.close();
        db.close();

        return exists;
    }

    public boolean updateRoom(int roomId, String name, String price, String quantity, String area, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_ACREAGE, area);
        values.put(COLUMN_STATUS, status);

        int rowsAffected = db.update(TABLE_ROOMS, values, COLUMN_ROOM_ID + " = ?", new String[]{String.valueOf(roomId)});
        db.close();

        return rowsAffected > 0;
    }

    public boolean deleteKhachThue(int khachThueId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_KHACH_THUE, COLUMN_KHACH_THUE_ID + " = ?", new String[]{String.valueOf(khachThueId)});
        db.close();
        return rowsDeleted > 0;
    }

    public boolean updateKhachThue(int khachThueId, String name, String gender, String phone, String cmnd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KHACH_THUE_NAME, name);
        values.put(COLUMN_KHACH_THUE_GENDER, gender);
        values.put(COLUMN_KHACH_THUE_PHONE, phone);
        values.put(COLUMN_KHACH_THUE_CMND, cmnd);

        int rowsAffected = db.update(TABLE_KHACH_THUE, values, COLUMN_KHACH_THUE_ID + " = ?", new String[]{String.valueOf(khachThueId)});
        db.close();

        return rowsAffected > 0;
    }

    public long addHopDong(String roomName, String rentName, String price, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOPDONG_ROOM_NAME, roomName);
        values.put(COLUMN_HOPDONG_RENT_NAME, rentName);
        values.put(COLUMN_HOPDONG_PRICE, price);
        values.put(COLUMN_HOPDONG_START_DATE, startDate);
        values.put(COLUMN_HOPDONG_END_DATE, endDate);

        long id = db.insert(TABLE_HOPDONG, null, values);
        db.close();

        return id;
    }

    @SuppressLint("Range")
    public String getRentNameByRoomId(int roomId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rentName = null;

        String query = "SELECT " + COLUMN_KHACH_THUE_NAME +
                " FROM " + TABLE_KHACH_THUE +
                " WHERE " + COLUMN_KHACH_THUE_ROOM_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(roomId)});

        if (cursor.moveToFirst()) {
            rentName = cursor.getString(cursor.getColumnIndex(COLUMN_KHACH_THUE_NAME));
        }

        cursor.close();
        db.close();

        return rentName;
    }

    public boolean hasContractForRoom(int roomId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean result = false;

        try {
            db = this.getReadableDatabase();

            String[] projection = {COLUMN_HOPDONG_ID};
            String selection = COLUMN_HOPDONG_ID + " = ? AND " + COLUMN_HOPDONG_END_DATE + " >= ?";
            String[] selectionArgs = {String.valueOf(roomId), String.valueOf(System.currentTimeMillis())}; // Lấy ngày hiện tại

            cursor = db.query(TABLE_HOPDONG, projection, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                result = true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi kiểm tra hợp đồng", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return result;
    }
    public List<HopDongCLass> getAllHopDong() {
        List<HopDongCLass> hopDongList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_HOPDONG, null);

            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_HOPDONG_ID));
                    @SuppressLint("Range") String roomName = cursor.getString(cursor.getColumnIndex(COLUMN_HOPDONG_ROOM_NAME));
                    @SuppressLint("Range") String rentName = cursor.getString(cursor.getColumnIndex(COLUMN_HOPDONG_RENT_NAME));
                    @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(COLUMN_HOPDONG_PRICE));
                    @SuppressLint("Range") String startDate = cursor.getString(cursor.getColumnIndex(COLUMN_HOPDONG_START_DATE));
                    @SuppressLint("Range") String endDate = cursor.getString(cursor.getColumnIndex(COLUMN_HOPDONG_END_DATE));

                    HopDongCLass hopDong = new HopDongCLass(id,roomName,rentName,price,startDate,endDate);
                    hopDongList.add(hopDong);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching contracts", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return hopDongList;
    }

    public List<KhachThue> getAllKhachThue() {
        List<KhachThue> khachThueList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_KHACH_THUE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_KHACH_THUE_ID));
                @SuppressLint("Range")String name = cursor.getString(cursor.getColumnIndex(COLUMN_KHACH_THUE_NAME));
                @SuppressLint("Range")String gender = cursor.getString(cursor.getColumnIndex(COLUMN_KHACH_THUE_GENDER));
                @SuppressLint("Range")String phone = cursor.getString(cursor.getColumnIndex(COLUMN_KHACH_THUE_PHONE));
                @SuppressLint("Range")String cmnd = cursor.getString(cursor.getColumnIndex(COLUMN_KHACH_THUE_CMND));
                @SuppressLint("Range")int roomId = cursor.getInt(cursor.getColumnIndex(COLUMN_KHACH_THUE_ROOM_ID));

                KhachThue khachThue = new KhachThue(id, name, gender, phone, cmnd, roomId);
                khachThueList.add(khachThue);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return khachThueList;
    }
}
