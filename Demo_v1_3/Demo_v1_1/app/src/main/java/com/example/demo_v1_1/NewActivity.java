package com.example.demo_v1_1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NewActivity extends AppCompatActivity {

//    Button doctor,doctor1,doctor2,doctor3,doctor4,doctor5,doctor6,doctor7,doctor8,doctor9,doctor10;
    TextView welcome;
    Connection con;
    DBConnector dbConnector;
    String msg = "";
    Boolean isSuccess = false;
    LinearLayout linearLayout;
    private static String name,fullName,Doktor1,Doktor2,Doktorx,columnName1,columnName2,columnName3;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();
        /*linearLayout = (LinearLayout) findViewById(R.id.linear_doktor);
        Button btn = new Button(this); // creating a new object of button type
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)); //setting the width and height of the element*/

//        Button myButton = new Button(this);
        LinearLayout ll = (LinearLayout)findViewById(R.id.linear_doktor);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        myButton.setBackgroundResource(R.drawable.button);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

//        doctor = (Button) findViewById(R.id.buttondoktor);
//        doctor1 = (Button) findViewById(R.id.buttondoktor1);
//        doctor2 = (Button) findViewById(R.id.buttondoktor2);
//        doctor3 = (Button) findViewById(R.id.buttondoktor3);
//        doctor4 = (Button) findViewById(R.id.buttondoktor4);
//        doctor5 = (Button) findViewById(R.id.buttondoktor5);
//        doctor6 = (Button) findViewById(R.id.buttondoktor6);
//        doctor7 = (Button) findViewById(R.id.buttondoktor7);
//        doctor8 = (Button) findViewById(R.id.buttondoktor8);
//        doctor9 = (Button) findViewById(R.id.buttondoktor9);
//        doctor10 = (Button) findViewById(R.id.buttondoktor10);
        welcome = (TextView) findViewById(R.id.textView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(NewActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Intent i2 = new Intent(NewActivity.this,ProfileActivity.class);
                        startActivity(i2);
                        Toast.makeText(NewActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

//        myButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View columnName6) {
//                Intent intent = new Intent(NewActivity.this,PrescriptionActivity.class);
//                intent.putExtra("id",String.valueOf(columnName3));
//                intent.putExtra("doktor",String.valueOf(Doktor1));
//                NewActivity.this.startActivity(intent);
//            }
//        });
        

        try
        {
            con = dbConnector.connectionclass(); // Connect to database
            if (con == null)
            {
                msg = "Check Your Internet Access!";
            }
            else
            {
                // Change below query according to your own database.
                String query = "select * from HASTA where Hasta_TC_Kimlik_No='" + name + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    columnName1 = rs.getString("Hasta_Ad");
                    columnName2 = rs.getString("Hasta_Soyad");
                    columnName3 = rs.getString("Hasta_ID");
                }
                List<Button> buttonlist = new ArrayList<>();
                try
                {
                    con = dbConnector.connectionclass();// Connect to database
                    if (con == null)
                    {
                        msg = "Check Your Internet Access!";
                    }
                    else
                    {
                        // Change below query according to your own database.
                        String query1 = "select DOKTOR.* from DOKTOR left join ILAC on ILAC.Doktor_ID=DOKTOR.Doktor_ID where Hasta_ID='" + columnName3 + "'";
                        Statement stmt1 = con.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(query1);
                        int c = 0;
                        final String[] options = new String[rs1.getMetaData().getColumnCount()];
                        final String[] DoktorID = new String[rs1.getMetaData().getColumnCount()];
                        while (rs1.next()) {
                            DoktorID[c] = rs1.getString("Doktor_ID");
                            options[c] = (rs1.getString("Doktor_Unvan") + rs1.getString("Doktor_Ad") +
                                    rs1.getString("Doktor_Soyad") + "- " + rs1.getString("Doktor_Brans")).replaceAll("\\s+"," ");

                            c++;
                        }
                        Button btn[] = new Button[c];
                        String doktorss[] = new String[c];
                        for (int i = 0; i < c; i++) {
                            btn[i] = new Button(this);
                            buttonlist.add(btn[i]);
                            btn[i].setTextColor(getResources().getColorStateList(R.color.White));
                            btn[i].setBackgroundResource(R.drawable.buttondynamic);
                            btn[i].setGravity(Gravity.CENTER);
                            btn[i].setText(options[i]);
                            ll.addView(btn[i], lp);
                            final int finalI = i;
                            btn[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View columnName6) {
                                    Intent intent = new Intent(NewActivity.this,PrescriptionActivity.class);
                                    intent.putExtra("id",String.valueOf(columnName3));
                                    intent.putExtra("doktor",String.valueOf(options[finalI]));
                                    intent.putExtra("doktor2",String.valueOf(DoktorID[finalI]));
                                    NewActivity.this.startActivity(intent);
                                }
                            });

                        }

//                        ArrayList<String> doktorlar = new ArrayList<String>();

//                        int i =0;
//                        int size =0;
//                        if (rs1 != null)
//                        {
//                            rs1.beforeFirst();
//                            rs1.last();    // moves cursor to the last row
//                            size = rs1.getRow(); // get row id
//                        Button btn[] = new Button[size];
//                        for (int i = 0; i <size ; i++) {
//                                btn[i] = new Button(this);
//                                ll.addView(btn[i], lp);
//
//                            }
//                        }
//                        List<Person> persons = new ArrayList<Person>();
//                        ArrayList<String> doktorlar = new ArrayList<String>();
//                        ArrayList<String> doktorid = new ArrayList<String>();
//                        List<String> list;
//                        while(rs1.next()){
//                            Person person = new Person();
//                            person.setDid(rs1.getString("Doktor_ID"));
//                            person.setId(rs1.getString("Doktor_Unvan"));
//                            person.setAd(rs1.getString("Doktor_Ad")) ;
//                            person.setSoyad(rs1.getString("Doktor_Soyad"));
//                            person.setBrans(rs1.getString("Doktor_Brans")) ;
//                            persons.add(person);
//                            Doktor2 = person.getDid();
////                            Doktor1 = (columnName4+columnName5+columnName6+"- "+columnName7).replaceAll("\\s+"," ");
//                            Doktor1 = (person.getId()+person.getAd()+person.getSoyad()+"- "+person.getBrans()).replaceAll("\\s+"," ");
//                            doktorlar.add(Doktor1);
//                            list = new ArrayList<String>();
//                            list.add(Doktor2);
//
////                            doktorlar.add(Doktor1);
////                            doctor.setText(Doktor1); //doktorlar.get(1)
////                            j++;
//                            //linearLayout.addView(btn);
//
//                        }
//
//
//
////                        Button btn[] = new Button[persons.size()];
//
//
//
//                        for (int i = 0; i < persons.size(); i++) {
//                            Person person = persons.get(i);
//                            int size = persons.size(); // Do with it whatever you want.
//                            btn[i] = new Button(this);
//                            btn[i].setText(doktorlar.get(i));
//                            ll.addView(btn[i], lp);
//                            btn[i].setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View columnName6) {
//                                    Intent intent = new Intent(NewActivity.this,PrescriptionActivity.class);
//                                    intent.putExtra("id",String.valueOf(columnName3));
//                                    intent.putExtra("doktor",String.valueOf(Doktor1));
//                                    intent.putExtra("doktor2",String.valueOf(Doktorx));
//                                    NewActivity.this.startActivity(intent);
//                                }
//                            });
//                        }
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    msg = ex.getMessage();
                }
                //doctor.setText((columnName4+columnName5+columnName6+"- "+columnName7).replaceAll("\\s+"," "));
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            msg = ex.getMessage();
        }
        fullName = columnName1+ " " + columnName2;
        fullName = fullName.replaceAll("\\s+"," ");
        welcome.setText("HOŞGELDİNİZ " + fullName);
        welcome.setGravity(Gravity.CENTER);
//        myButton.setText(Doktor1);
//        ll.addView(myButton, lp);
        
    }
    public static String getVariable() {
        return columnName3;
    }
    public static String getVariable1() {
        return Doktor2;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
