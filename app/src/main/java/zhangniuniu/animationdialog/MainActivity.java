package zhangniuniu.animationdialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private AnimationDialog mDialog;
    private ImageView targetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetView = findViewById(R.id.target_view);

        initDialog(this);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();

            }
        });
    }

    private void initDialog(Context context) {

        View alertView = View.inflate(context, R.layout.common_dialog_layout, null);
        mDialog = new AnimationDialog(context, R.style.alertDialog);
        mDialog.setContentView(alertView);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setTargetView(targetView);

    }
}
