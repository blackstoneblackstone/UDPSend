package udp.gcks.com.cn.udpsend;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends ActionBarActivity {
    public static Handler handler;
    public static boolean flag=true;
    public static String  ip="10.8.122.48";
    public static int    port=8888;
    public static String   type="1";
    TextView ipView;
    TextView portView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        ipView = (TextView) this.findViewById(R.id.ip);

        portView = (TextView) this.findViewById(R.id.port);

        final Spinner typeSpinner = (Spinner) this.findViewById(R.id.type);

        Button startButton = (Button) this.findViewById(R.id.statButton);

        Button stopButton = (Button) this.findViewById(R.id.stopButton);

        final TextView logsText = (TextView) this.findViewById(R.id.logsText);
        handler = new Handler() {
            public void handleMessage(Message msg) {
                logsText.setText(msg.getData().get("content").toString());
                super.handleMessage(msg);
            }
        };
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ip=ipView.getText().toString();
                port=Integer.valueOf(portView.getText().toString());
                String content = (typeSpinner.getSelectedItem()).toString();
                MainActivity.type=content;
                flag=false;
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flag=true;
               new Thread(new BroadCastUdp()).start();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flag=false;
                logsText.setText("停止发送");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
