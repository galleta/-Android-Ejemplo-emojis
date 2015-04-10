package pruebaemoji.pruebaemoji;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

public class MainActivity extends ActionBarActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener
{
    private static EmojiconEditText mEditEmojicon;
    private static EmojiconTextView mTxtEmojicon;
    private static CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ***** Obtengo los recursos de la aplicación *****
        mEditEmojicon = (EmojiconEditText) findViewById(R.id.editEmojicon);
        mTxtEmojicon = (EmojiconTextView) findViewById(R.id.txtEmojicon);
        mCheckBox = (CheckBox) findViewById(R.id.use_system_default);
        setEmojiconFragment(Boolean.FALSE);
        // *************************************************

        mEditEmojicon.addTextChangedListener(new TextWatcherAdapter()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mTxtEmojicon.setText(s);
            }
        });

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                mEditEmojicon.setUseSystemDefault(b);
                mTxtEmojicon.setUseSystemDefault(b);
                setEmojiconFragment(b);
            }
        });
    }

    /**
     * Obtiene el EmojiconsFragment
     * @param useSystemDefault Indica si se usarán los emojis del sistema o no
     */
    private void setEmojiconFragment(boolean useSystemDefault)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
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

    // ***** Métodos de la interfaz EmojiconGridFragment.OnEmojiconClickedListener *****

    @Override
    /**
     * Este método se ejecuta cuando se hace click en el fragment de los emojicons
     * @param emojicon Emojicon en el que se hace click
     */
    public void onEmojiconClicked(Emojicon emojicon)
    {
        // Ponemos el emojicon en el que hemos hecho click en el EmojiconEditText mEditEmojicon
        EmojiconsFragment.input(mEditEmojicon, emojicon);
    }

    // *********************************************************************************

    // ***** Métodos de la interfaz EmojiconsFragment.OnEmojiconBackspaceClickedListener *****

    @Override
    /**
     * Este método se ejecuta cuando se pulsa el backspace del fragment de los emojicons
     */
    public void onEmojiconBackspaceClicked(View v)
    {
        EmojiconsFragment.backspace(mEditEmojicon);
    }

    // ***************************************************************************************
}
