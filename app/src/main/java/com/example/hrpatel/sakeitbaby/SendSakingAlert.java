package com.example.hrpatel.sakeitbaby;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import android.provider.ContactsContract;

public class SendSakingAlert extends Activity {

    Button button;
    EditText phoneNumber;
    EditText message;
    ListView listView;
    ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_saking_alert);

        button = (Button) findViewById(R.id.button);
        phoneNumber = (EditText) findViewById(R.id.editText2);
        message = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber.getText().toString(), null, message.getText().toString(), null, null);
                Toast.makeText(getApplicationContext(), "sent", Toast.LENGTH_LONG).show();
            }
        });

        listView =(ListView) findViewById(R.id.list);
        Button show =(Button) findViewById(R.id.button1);
        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                displayContacts(listView);
            }
        });
    }

    private void displayContacts(final ListView listView) {

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int numberOfRecords = cur.getCount();
        if (numberOfRecords > 0) {
            final String[] values = new String[numberOfRecords];

            int i = 0;
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);



                    while (pCur.moveToNext()){
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        values[i++] = name + " - " + phoneNo;
                        Toast.makeText(getApplicationContext(), "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                    };
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, values);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            // ListView Clicked item index
                            int itemPosition     = position;

                            // ListView Clicked item value
                            String  itemValue    = (String) listView.getItemAtPosition(position);

                            // Show Alert
                            Toast.makeText(getApplicationContext(),
                                    "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                                    .show();

                            listView1 =(ListView) findViewById(R.id.list1);
                            String[] values1 = new String[listView1.getCount() + 1];
                            for(int j = 0; j < listView1.getCount(); j++)
                            {
                                values1[j] = (String) listView1.getItemAtPosition(j);
                            }
                            values1[listView1.getCount()] = itemValue;

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_list_item_1, android.R.id.text1, values1);
                            listView1.setAdapter(adapter);
                        }

                    });

                    pCur.close();
                    }
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_saking_alert, menu);
        return true;
    }

    public boolean onSendMessage(String message) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ContactsFragment() {

    }


}


