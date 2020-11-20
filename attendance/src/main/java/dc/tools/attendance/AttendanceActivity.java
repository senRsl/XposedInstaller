package dc.tools.attendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import dc.android.common.activity.BaseActivity;

/**
 * @author senrsl
 * @ClassName: AttendanceActivity
 * @Package: dc.tools.attendance
 * @CreateTime: 2020/11/20 5:20 PM
 */
public class AttendanceActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
    }
}
