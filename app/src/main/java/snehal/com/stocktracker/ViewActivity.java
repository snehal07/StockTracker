package snehal.com.stocktracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

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

public class ViewActivity extends AppCompatActivity {
    Button buttonDone;
    AutoCompleteTextView psize,companyname,papertype,papercolor,papergsm;
    SQLiteDatabase db;
    Context c;

    String size,cname,ptype,pcolor,pcode,pgsm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        c = getApplicationContext();
        psize = (AutoCompleteTextView)(findViewById(R.id.papersize));
        companyname = (AutoCompleteTextView)(findViewById(R.id.companyname));
        papertype = (AutoCompleteTextView)(findViewById(R.id.papertype));
        papercolor = (AutoCompleteTextView)(findViewById(R.id.papercolor));
        papergsm = (AutoCompleteTextView)(findViewById(R.id.papergsm));

        buttonDone = (Button)(findViewById(R.id.buttonDone));

        db = openOrCreateDatabase("StockDB", Context.MODE_PRIVATE, null);

        pcode = generateCode();

        String[] size = {"18 x 23", "23 x 36", "25 x 36", "17 x 27", "22 x 28", "20 x 30", "30 x 40", "24 x 34", "A4", "A3", "F/S"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, size);
        psize.setThreshold(2);
        psize.setAdapter(adapter1);

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

        Cursor cur = db.rawQuery("SELECT * FROM stock",null);
        StringBuffer buffer=new StringBuffer();
        while(cur.moveToNext())
        {
            buffer.append("CODE: "+cur.getString(0)+"\n");
            buffer.append("QTY: "+cur.getString(1)+"\n");

        }
        Log.i("+++",""+buffer);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcode = generateCode();
                Cursor cursor = db.rawQuery("SELECT qty FROM stock WHERE code='" + pcode + "'", null);
                Log.i("+++",""+pcode);
                if(cursor!=null && cursor.getCount() > 0)
                {

                    if (cursor.moveToFirst())
                {
                    Log.i("+++","inside block");
                    AlertDialog.Builder builder=new AlertDialog.Builder(ViewActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Current Stock");
                    builder.setMessage(""+cursor.getInt(0));
                    builder.show();
                    Log.i("+++",""+cursor.getInt(0));

                }
                else
                {
                    Toast toast = Toast.makeText(c, "Item Not Found", Toast.LENGTH_SHORT);
                    toast.show();
                }
                }
                else
                    Log.i("+++","inside else");
            }
        });
    }

    public String generateCode() {
        String code = "";
        size = psize.getText().toString();
        cname = companyname.getText().toString();
        ptype = papertype.getText().toString();
        pgsm = papergsm.getText().toString();
        pcolor = papercolor.getText().toString();

        code = size + cname + ptype + pgsm + pcolor;
        Log.i("----", "" + code);
        return code;

    }

}
