package snehal.com.stocktracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UpdateActivity extends AppCompatActivity {
    Button buttonAddNew, buttonDecrementQTY,buttonAddQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        buttonAddNew = (Button)(findViewById(R.id.buttonAdd));
        buttonDecrementQTY = (Button)(findViewById(R.id.buttonDelete));
        buttonAddQuantity = (Button)(findViewById(R.id.buttonAddQuantity));

        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateActivity.this,Form.class);
                i.putExtra("Button","AddNew");
                startActivity(i);
            }
        });

        buttonAddQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateActivity.this,Form.class);
                i.putExtra("Button","AddQuantity");
                startActivity(i);
            }
        });
        buttonDecrementQTY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateActivity.this,Form.class);
                i.putExtra("Button","DecrementQuantity");
                startActivity(i);

            }
        });
    }
}
