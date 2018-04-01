package snehal.com.stocktracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Form extends AppCompatActivity {
    AutoCompleteTextView papersize, companyname, papertype, papercolor, papergsm;
    Button buttonDone;
    EditText paperqty;
    Context c;
    String psize, cname, ptype,pgsm, pcolor;
    int  qty;
    Toast toast;
    String buttonName = "";

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        c = getApplicationContext();
        buttonDone = (Button) (findViewById(R.id.buttonDone));
        papersize = (AutoCompleteTextView) (findViewById(R.id.papersize));
        companyname = (AutoCompleteTextView) (findViewById(R.id.companyname));
        papertype = (AutoCompleteTextView) (findViewById(R.id.papertype));
        papercolor = (AutoCompleteTextView) (findViewById(R.id.papercolor));
        papergsm = (AutoCompleteTextView) (findViewById(R.id.papergsm));
        paperqty = (EditText) (findViewById(R.id.paperqty));

        db = openOrCreateDatabase("StockDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS stock(code VARCHAR PRIMARY KEY,qty NUMBER);");
        Log.i("+++", "table created");

        Intent i = getIntent();
        buttonName = i.getStringExtra("Button");


        if (buttonName.equalsIgnoreCase("AddNew")) {
            toast = Toast.makeText(c, "AddNew Pressed", Toast.LENGTH_SHORT);
            toast.show();
        } else if (buttonName.equalsIgnoreCase("DecrementQuantity")) {
            toast = Toast.makeText(c, "DecrementQuantity Pressed", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast = Toast.makeText(c, "AddQuantity Pressed", Toast.LENGTH_SHORT);
            toast.show();
        }
        String[] size = {"18 x 23", "23 x 36", "25 x 36", "17 x 27", "22 x 28", "20 x 30", "30 x 40", "24 x 34", "A4", "A3", "F/S"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, size);
        papersize.setThreshold(2);
        papersize.setAdapter(adapter1);

        String[] company = {"Wesco", "TNPL", "Built", "Andhra", "Agarwal", "JK", "Copygold", "B2B", "Expert", "ExcelPro", "Kartik", "VPM", "GVG"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, company);
        companyname.setThreshold(2);
        companyname.setAdapter(adapter2);

        String[] type = {"Art paper", "Maplitho", "Colour", "Card Sheet", "Card Board", "CoverPaper", "Bond", "Xerox Paper"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, type);
        papertype.setThreshold(2);
        papertype.setAdapter(adapter3);

        String[] color = {"White", "Yellow", "Pink", "Blue", "Green"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, color);
        papercolor.setThreshold(2);
        papercolor.setAdapter(adapter4);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast = Toast.makeText(c, "Done Pressed", Toast.LENGTH_SHORT);
                //toast.show();
                String pcode = generateCode();
                qty = Integer.parseInt(paperqty.getText().toString());

                if (buttonName.equalsIgnoreCase("AddNew"))
                    addInDatabase(pcode, qty);
                else if (buttonName.equalsIgnoreCase("DecrementQuantity"))
                    decrementFromDatabase(pcode, qty);
                else if (buttonName.equalsIgnoreCase("AddQuantity"))
                    incrementFromDatabase(pcode,qty);

            }
        });
    }

    public String generateCode() {
        String code = "";
        psize = papersize.getText().toString();

        cname = companyname.getText().toString();
        ptype = papertype.getText().toString();
        pgsm = papergsm.getText().toString();
        pcolor = papercolor.getText().toString();

        code = psize + cname + ptype + pgsm + pcolor;
        Log.i("+++", "" + code);
        return code;

    }


    void addInDatabase(String pcode, int qty) {
        db.execSQL("INSERT INTO stock VALUES('" + pcode + "','" + qty + "');");

        Log.i("+++", "Inserted finally");
        clearText();
    }

    void decrementFromDatabase(String pcode, int qty) {
        Cursor cursor = db.rawQuery("SELECT qty from stock WHERE code='" + pcode + "';", null);
        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst())
            {

                int q = cursor.getInt(0);
                qty = q-qty;
                db.execSQL("UPDATE stock SET qty="+qty+" WHERE code='"+pcode+"';");
                toast = Toast.makeText(c, "Updation Done", Toast.LENGTH_SHORT);
                toast.show();
                Log.i("+++","updated");
            }
            else
            {
                Toast toast = Toast.makeText(c.getApplicationContext(), "Item Not Found", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    void incrementFromDatabase(String pcode,int qty) {
        Cursor cursor = db.rawQuery("SELECT qty from stock WHERE code='" + pcode + "'", null);
        if(cursor!=null && cursor.getCount() > 0){
            if (cursor.moveToFirst())
        {
            int q = cursor.getInt(0);
            qty = qty+q;
            db.execSQL("UPDATE stock SET qty="+qty+" WHERE code='"+pcode+"';");
            toast = Toast.makeText(c, "Updation Done", Toast.LENGTH_SHORT);
            toast.show();
            Log.i("+++","updated");
        }
        else
        {
            Toast toast = Toast.makeText(c.getApplicationContext(), "Item Not Found", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    }

    public void clearText()
    {
        papersize.setText("");
        companyname.setText("");
        papertype.setText("");
        papercolor.setText("");
        papergsm.setText("");
        paperqty.setText("");
    }
}
