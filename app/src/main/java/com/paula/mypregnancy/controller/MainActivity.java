package com.paula.mypregnancy.controller;

/*
 * MainActivity
 *
 * An implementation of a pregnancy tracker app.
 * Development of mobile applications
 * Umeå Universitet, summer course 2019
 *
 * Paula D'Cruz
 *
 * This is one of the controller classes. It is the main activity, and it controls which fragment is visible to the user,
 * and handles all the changes based on the user interaction.
 *
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.DueDate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DueDate mDueDate;
    private Fragment mNewPregnancyFragment, mTrackPregnancyFragment, mMummyFragment, mBabyFragment;
    private String mFile = "myFile";
    private FragmentManager fm = this.getSupportFragmentManager();
    protected final static int CAPTURE_IMAGE_REQUEST_CODE = 1;
    private Uri mUri;
    private File mCameraFile;
    private final static String TAG = "Main Activity ";

    /**
     * onCreate
     *
     * Lifecycle method that loads the correct fragment into the fragment container
     *
     * @param savedInstanceState saved state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment;
        restoreDueDate();

        if (savedInstanceState != null) {
            fragment = fm.getFragment(savedInstanceState, String.valueOf(R.id.fragment_container));
        } else {
            fragment = fm.findFragmentById(R.id.fragment_container);
            if (mDueDate != null){
                trackPregnancy();
            } else {
                newPregnancy();
            }
        }

        if (fragment == null) {
            if (mDueDate != null){
                trackPregnancy();
            } else {
                newPregnancy();
            }
        }
    }

    /**
     * onSaveInstanceState
     *
     * Stores transient bundle data from a fragment
     *
     * @param outState bundle where the saved state is stored
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        fm.putFragment(outState, String.valueOf(R.id.fragment_container), fragment);
    }

    /**
     * restoreDueDate
     *
     * this method will collect the due date object from internal memory, if one is saved. otherwise due date is set to null,
     * and the new pregnancy fragment will be called from onCreate
     *
     */

    private void restoreDueDate(){
        File[] files = getApplicationContext().getFilesDir().listFiles();
        if (files != null) {
            try {
                FileInputStream fis = openFileInput(mFile);
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                long milliSeconds = Long.parseLong(line);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(milliSeconds);
                c.add(Calendar.DATE, -280);
                mDueDate = new DueDate(c);
                Log.d(TAG, "" + mDueDate.toString());
                inputStreamReader.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, " file not found exception ");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(TAG, " IO exception ");
                e.printStackTrace();
            }
        }
    }

    /**
     * newPregnancy
     *
     * this method initialises the new pregnancy fragment, where the user can start tracking a new pregnancy by calculating
     * a due date
     *
     */

    public void newPregnancy() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mNewPregnancyFragment = new NewPregnancyFragment();
        transaction.replace(R.id.fragment_container, mNewPregnancyFragment).addToBackStack("NEW");
        transaction.commit();
    }

    /**
     * trackPregnancy
     *
     * this method initialises the track pregnancy fragment, and will be the first to be called if there is already a due
     * date in the internal memory. it passes the due date as a parcelable object in a bundle to the fragment.
     *
     */

    public void trackPregnancy() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mTrackPregnancyFragment = new TrackPregnancyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("dueDate", mDueDate);
        mTrackPregnancyFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, mTrackPregnancyFragment).addToBackStack("TRACK");
        transaction.commit();
    }

    /**
     * openMummyFragment
     *
     * this method initialises the mummy fragment that can be called from a button in the track pregnancy fragment.
     * it passes the due date as a parcelable object in a bundle to the fragment.
     *
     */

    public void openMummyFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mMummyFragment = new MummyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("dueDate", mDueDate);
        mMummyFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, mMummyFragment).addToBackStack("GALLERY");
        transaction.commit();
    }

    /**
     * openBabyFragment
     *
     * this method initialises the baby fragment that can be called from a button in the track pregnancy fragment.
     * it passes the due date as a parcelable object in a bundle to the fragment.
     *
     */

    public void openBabyFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mBabyFragment = new BabyFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("dueDate", mDueDate);
        mBabyFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, mBabyFragment).addToBackStack("BABY_INFO");
        transaction.commit();
    }

    /**
     * abortPregnancy
     *
     * this method cancels an ongoing tracking of a pregnancy. it removes the due date from internal memory, and starts
     * the new pregnancy fragment.
     *
     */

    public void abortPregnancy() {
        popBackStack();
        File[] files = getApplicationContext().getFilesDir().listFiles();
        if (files != null)
            for (File file : files) {
                file.delete();
            }
        newPregnancy();
    }

    /**
     * setDueDate
     *
     * this method is called from the new pregnancy fragment, when the user has chosen to proceed and start tracking
     * a pregnancy. it calls on the saveDueDate-method and later the trackPregnancy-method to open the track pregnancy fragment.
     *
     * @param dueDate takes in the due date that has been calculated from the new pregnancy fragment
     */

    public void setDueDate(DueDate dueDate) {
        mDueDate = dueDate;
        saveDueDate();
        trackPregnancy();
    }

    /**
     * saveDueDate
     *
     * this method writes due due date to the internal memory, so the application remembers the due date even when closed down
     * by the user.
     *
     */

    private void saveDueDate() {
        Log.d(TAG, " store file ");
        String fileContents = Long.toString(mDueDate.getDueDateInMillis());
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(mFile, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.d(TAG, " exception ");
            e.printStackTrace();
        }
    }

    /**
     * popBackStack
     *
     * this method pops every item in the backstack
     *
     */

    private void popBackStack() {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    /**
     * onBackPressed
     *
     * removes one item from the backstack, when the back button is pressed. if it was the last item in the backstack, the application
     * will finish and close down.
     *
     */

    @Override
    public void onBackPressed() {
        fm.popBackStack();
        Log.d(TAG, " back pressed ");
        int count = 0;
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            count++;
        }
        if (count == 1) {
            Log.d(TAG, "finishing ");
            finish();
        }
    }

    /**
     * startCamera
     *
     * this method is triggered by pressing the camera icon in the toolbar. it opens the camera of the phone and allows the user
     * to take a picture
     *
     */

    public void startCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCameraFile = new File(getFilesDir(),"mypic.jpg");
        Log.d(TAG, "mCameraFile " + mCameraFile);
        mUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName()+".fileprovider", mCameraFile);
        i.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(i, CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * onActivityResult
     *
     * called when the user has taken action in the camera intent.
     * shows a toast to the user that tells them a picture has been added to the mummy fragment, if they chose to save it.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Picture added to mummy page", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // No picture was taken
            } else {
                mCameraFile = null; // Error
            }
        }
    }

}
