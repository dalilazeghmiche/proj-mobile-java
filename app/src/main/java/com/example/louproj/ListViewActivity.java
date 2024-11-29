package com.example.louproj;


import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.content.IntentFilter;
        import android.content.Intent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private HeadsetPlugReceiver headsetPlugReceiver;
    //Récepteur pour les événements de casque
    private DBHelper dbHelper;
    private ArrayList<String> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        headsetPlugReceiver = new HeadsetPlugReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetPlugReceiver, filter);

        listView = findViewById(R.id.listview);
        dbHelper = new DBHelper(this);

        // Fetch data from the database
        userList = getUsersFromDatabase();
        // Utilisation d'un adaptateur personnalisé pour lier les données à la ListView

        ArrayAdapter<String> adapter = new UserListAdapter(userList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver when activity is destroyed
        if (headsetPlugReceiver != null) {
            unregisterReceiver(headsetPlugReceiver);
        }
    }

    private ArrayList<String> getUsersFromDatabase() {
        ArrayList<String> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DBHelper.NAME_COL}; // Assuming NAME_COL is the column for usernames
        Cursor cursor = db.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        // Vérifie si le curseur est valide et contient des données

        if (cursor != null && cursor.moveToFirst()) { //

            int columnIndex = cursor.getColumnIndex(DBHelper.NAME_COL);
            do {
                String username = cursor.getString(columnIndex);
                userList.add(username);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return userList;
    }


    // Custom adapter to bind data to list item views
    private class UserListAdapter extends ArrayAdapter<String> {

        public UserListAdapter(ArrayList<String> userList) {
            super(ListViewActivity.this, R.layout.list_item, userList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {

                //prend un fichier de mise en page XML
                //le convertit en un objet View.
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            // Get the current user's username
            String username = userList.get(position);

            // Bind the username to the TextView in the list item layout
            TextView usernameTextView = listItemView.findViewById(R.id.text_view_username);
            usernameTextView.setText(username);

            return listItemView;
        }
    }
    public void back (View view){
        Intent a = new Intent(this, MainActivity.class);
        startActivity(a);
    }
}