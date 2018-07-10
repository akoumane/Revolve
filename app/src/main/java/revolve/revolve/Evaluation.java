package revolve.revolve;

/**
 * Players go to this screen once the timer counts down to 0.
 * The final score and accuracy will be displayed here and the player
 * will be given the option to submit there score to a PHP file.
 */

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;

public class Evaluation extends AppCompatActivity {

    String baseURL;
    TextView finalScore, accuracy;
    EditText name;

    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setSystemUiVisibility(flags);
        setContentView(R.layout.activity_evaluation);

        finalScore = findViewById(R.id.scoreView);
        accuracy = findViewById(R.id.accView);
        name = findViewById(R.id.nameBox);

        finalScore.setText(String.valueOf(GameView.score));
        double acc = (double)GameView.hitCount/(double)(GameView.hitCount+GameView.missCount);
        accuracy.setText(String.format("%.2f", acc));

        baseURL = getString(R.string.baseURL);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //Call to the PHP file to upload a score
    public void submitScore(View view)
    {
        String url = null;
        String nameText = name.getText().toString();
        if(nameText.equals(""))
            nameText = "PlayerDefaultName";

        try
        {
            url = baseURL + "?name=" +
                    URLEncoder.encode(nameText, "UTF-8") + "&score=" +
                    URLEncoder.encode(String.valueOf(GameView.score), "UTF-8");
        }
        catch (Exception ex) {}

        Log.d("URL", url);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Score Submitted", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR DESC Rest Call", error.getMessage());
            }
        });

        queue.add(stringRequest);

        onBackPressed();
    }

    //goes back to the main menua when the back button is pressed
    @Override
    public void onBackPressed() {
        GameView.reset();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
