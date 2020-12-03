package dc.tools.attendance;

import java.io.IOException;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import dc.android.common.activity.BaseActivity;
import dc.android.common.utils.SharePreferencesUtils;
import dc.common.Logger;
import dc.common.utils.FileUtils;
import dc.common.utils.StringUtils;

import static dc.android.common.CoreContext.SPLIT_COMMA;
import static dc.common.Global.EMPTY;
import static dc.tools.attendance.AttendanceContext.KEY_LAT;
import static dc.tools.attendance.AttendanceContext.KEY_LNG;
import static dc.tools.attendance.AttendanceContext.KEY_PATH;
import static dc.tools.attendance.AttendanceContext.KEY_TARGET;

/**
 * @author senrsl
 * @ClassName: AttendanceActivity
 * @Package: dc.tools.attendance
 * @CreateTime: 2020/11/20 5:20 PM
 */
public class AttendanceActivity extends BaseActivity {

    EditText etLat;
    EditText etLng;
    EditText etPath;
    EditText etTarget;

    SharePreferencesUtils sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        etLat = findViewById(R.id.et_lat);
        etLng = findViewById(R.id.et_lng);
        etPath = findViewById(R.id.et_pic);
        etTarget = findViewById(R.id.et_target);

        sp = new SharePreferencesUtils(this);

        etLat.setText(sp.getSharedPreferencesValue(KEY_LAT, EMPTY));
        etLng.setText(sp.getSharedPreferencesValue(KEY_LNG, EMPTY));
        etPath.setText(sp.getSharedPreferencesValue(KEY_PATH, EMPTY));
        etTarget.setText(sp.getSharedPreferencesValue(KEY_TARGET, EMPTY));
    }


    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                save();
                break;
        }
    }

    private void save() {
        Logger.w(getClass().getSimpleName(), etLat, etLng, etPath, etTarget);

        sp.saveSharedPreferencesValue(KEY_LAT, etLat.getText().toString());
        sp.saveSharedPreferencesValue(KEY_LNG, etLng.getText().toString());
        sp.saveSharedPreferencesValue(KEY_PATH, etPath.getText().toString());
        sp.saveSharedPreferencesValue(KEY_TARGET, etTarget.getText().toString());


        String str = StringUtils.sub(
                etLat.getText().toString(), SPLIT_COMMA,
                etLng.getText().toString(), SPLIT_COMMA,
                etPath.getText().toString(), SPLIT_COMMA,
                etTarget.getText().toString(), SPLIT_COMMA
        );

        try {
            FileUtils.str2File(AttendanceContext.getSharedConfig(), str);
            //FileUtils.is2File(new ByteArrayInputStream(str.getBytes()),AttendanceContext.getSharedConfig());

            Logger.w(this, sp.getSharedPreferencesValue(KEY_LAT, EMPTY),
                    sp.getSharedPreferencesValue(KEY_LNG, EMPTY),
                    sp.getSharedPreferencesValue(KEY_PATH, EMPTY),
                    sp.getSharedPreferencesValue(KEY_TARGET, EMPTY)
            );
        } catch (IOException e) {
            e.printStackTrace();
            Logger.w(this, e.getMessage());
        }


    }

}
