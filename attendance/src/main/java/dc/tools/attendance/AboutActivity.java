package dc.tools.attendance;

import android.content.Intent;
import android.os.Bundle;
import dc.android.common.activity.BaseAboutActivity;

/**
 * @author senrsl
 * @ClassName: AboutActivity
 * @Package: dc.tools.attendance
 * @CreateTime: 2020/11/20 5:30 PM
 */
public class AboutActivity extends BaseAboutActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void jump() {
        startActivity(new Intent(AboutActivity.this, AttendanceActivity.class));
        finish();
    }

}
