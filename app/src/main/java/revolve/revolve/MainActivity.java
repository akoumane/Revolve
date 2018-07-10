package revolve.revolve;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    //Object to represent a "pop" noise whenver one the targets is tapped on the main menu
    MediaPlayer blop;

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

        blop = MediaPlayer.create(this, R.raw.blop);

        setContentView(R.layout.activity_main);
    }

    //This method activates by tapping the "Start" target
    public void startGame(View view)
    {
        //Log.d("click", "start game");
        blop.start();
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
        finish();
    }

    //This method executes by tapping the "How to play" target
    public void howToPlay(View view)
    {
        blop.start();
        Intent intent = new Intent(this, HowToPlay.class);
        startActivity(intent);
        finish();
    }

    /*  This method executes by tapping the "view high scores" button.
        The default browser for the device opens up to the link below.
     */
    public void viewHighScores(View view)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://cs.csub.edu/~akoumane/3390/project/HighScores.php"));
        startActivity(browserIntent);
    }

    //An attempt to kill the game by pressing the back button. Not necessarily working yet...
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GameView.exit = true;
        finish();
    }
}
