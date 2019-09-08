package com.paula.mypregnancy.controller;

/*
 * MummyFragment
 *
 * An implementation of a pregnancy tracker app.
 * Development of mobile applications
 * Umeå Universitet, summer course 2019
 *
 * Paula D'Cruz
 *
 * This is one of the controller classes. It controls the view where the user can see information
 * relevant to the pregnant person.
 *
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.DueDate;
import java.io.File;

public class MummyFragment extends Fragment {

    private Toolbar mToolbar;
    private Button mTrackPregnancyButton;
    private TextView mMummyInformation, mWeek;
    private File mCameraFile;
    private DueDate mDueDate;
    private ImageView mBellyPic;
    private static final String DUE_DATE_PARCEL = "com.paula.mypregnancy.model.DueDate";
    private final static String TAG = "MummyFragment ";
    private Context mContext;

    /**
     * onAttach
     *
     * called once the fragment is associated with its activity.
     *
     * @param context the context that the fragment can be found in.
     */

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext = context;
    }

    /**
     * onCreate
     *
     * Handles restoring state by receiving parcelable
     * data and extras when available.
     *
     * @param savedInstanceState Bundle: saved state
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mDueDate = savedInstanceState.getParcelable(DUE_DATE_PARCEL);
        } else {

        }
        mCameraFile = new File(mContext.getFilesDir(), "mypic.jpg");
    }

    /**
     * onCreateView
     *
     * this method sets up the entire mummy view. it constructs all UI artifacts such as ImageView, a TextView with
     * information about changes the mother might experience, as well as a Button to take the user back to the
     * Track pregnancy view.
     *
     * @param inflater the layoutInflator object that is used to inflate view to the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_mummy, container, false);
        if (getArguments() != null) {
            mDueDate = getArguments().getParcelable("dueDate");
        }
        mToolbar = view.findViewById(R.id.action_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);

        mBellyPic = view.findViewById(R.id.weekly_belly);
        mMummyInformation = view.findViewById(R.id.mummyInformation);
        mWeek = view.findViewById(R.id.week);
        mTrackPregnancyButton = view.findViewById(R.id.track);
        mTrackPregnancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) getActivity();
                ma.trackPregnancy();
            }
        });
        updateView();
        return view;
    }

    /**
     * onSaveInstanceState
     *
     * saves the due date object to transient storage in a bundle on configuration change such as rotation
     *
     * @param outState bundle in which to place saved state.
     */

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(DUE_DATE_PARCEL, mDueDate);
    }

    /**
     * onCreateOptionsMenu
     *
     * creates the menu in the toolbar
     *
     * @param menu the menu that is available in the toolbar
     * @param inflater inflates the menu
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu, menu);
    }

    /**
     * onOptionsItemSelected
     *
     * this method calls whatever method is triggered based on the user's choice
     *
     * @param item the item selected in the menu
     * @return selected item
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        MainActivity ma = (MainActivity) getActivity();
        switch(item.getItemId()){
            case R.id.abort:
                ma.abortPregnancy();
                return true;
            case R.id.camera:
                ma.startCamera();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * updateView
     *
     * this method is called to set all relevant information in the view. if there is a picture saved to internal storage,
     * it will be converted into a bitmap to show in the imageView. if not, a stock photo is shown. the method also calls on
     * another method to decide which information is relevant to show in the TextView, based on the user's current pregnancy
     * week.
     *
     */

    private void updateView(){
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 2;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCameraFile.getAbsolutePath(), bmOptions);
        Log.d(TAG, "bitmap " + bitmap);
        if (bitmap != null){
            mBellyPic.setImageBitmap(bitmap);
        } else {
            mBellyPic.setImageResource(R.drawable.pregnant);
        }
        mWeek.setText("Week " + Integer.toString(mDueDate.getPregnancyWeek()));
        mMummyInformation.setText(getInformation());
    }

    /**
     * getInformation
     *
     * a long switch case that will return a hardcoded String with relevant information the the user, based on how many
     * weeks pregnant they are.
     *
     * @return a String with information
     */

    private String getInformation() {
        switch (mDueDate.getPregnancyWeek()) {
            case 0:
                return "If you're trying to conceive (or think you might already be pregnant), start making health changes to ensure that your body is in the best shape for carrying and nurturing your baby. In addition to taking prenatal vitamins (with folic acid), these are the biggies: cut out alcohol, cigarettes, and any illegal or recreational drugs. You may also need to switch or stop taking some prescription and over-the-counter medications.";
            case 1:
                return "If you're trying to conceive (or think you might already be pregnant), start making health changes to ensure that your body is in the best shape for carrying and nurturing your baby. In addition to taking prenatal vitamins (with folic acid), these are the biggies: cut out alcohol, cigarettes, and any illegal or recreational drugs. You may also need to switch or stop taking some prescription and over-the-counter medications.";
            case 2:
                return "Is it official yet? Not quite—but be assured that your body's got those baby-making mechanics well under way. After fertilization, your ovaries start ramping up the production of progesterone, a hormone that prepares your uterus to host the newly fertilized egg, or zygote, that will live there for the next 38 weeks or so.";
            case 3:
                return "You may experience some light vaginal bleeding, or spotting, when your egg implants, which many women mistake for a period. In fact, spotting that's lighter than your usual menstrual flow is one of the early signs of pregnancy.";
            case 4:
                return "You just missed your period and the test came back positive—that's right, you're pregnant! Can't bear to fasten your bra in the morning? For most women, breast tenderness is the first physical sign of pregnancy—even before telltale morning sickness strikes.";
            case 5:
                return "Feeling overjoyed one minute and equally stressed out the next? It's all part of the normal mood swings that come along with pregnancy. You might feel elated, depressed, angry, sentimental, powerful, and insecure—sometimes all in the same hour. Your hormones are flaring, so it's only natural for emotions to do the same, especially when a major life change is on the way.";
            case 6:
                return "Can you believe that just a few short weeks ago you were wondering if you were really pregnant? Chances are, by now your body is letting you know loud and clear (and if not now, soon). Sure, some of these signature first-trimester pregnancy symptoms can be a bit jarring—the exhaustion, nausea, vomiting, super-sore breasts, headaches, constipation, faintness, and mood swings—but try to go with the flow and look on the bright side.";
            case 7:
                return "Even though you're not starting to show yet, you may notice you've put on a few pounds and your pants are starting to feel tight. You aren't big enough for maternity clothes at this point, but you might want to start shopping for the future. Your skin may be going through some pregnancy changes already, too.";
            case 8:
                return "You might not have a hankering for pickles and ice cream, but pregnancy cravings could be wreaking havoc on your healthy eating plan. Yes, you are eating for two, but you really only need about an extra 300 calories per day (or 600 if you're expecting twins) to nourish your baby-to-be.";
            case 9:
                return "As your blood volume continues to increase, you might feel the effects through dizziness and frequent urination, and you might see the effects in bulging veins on your hands and feet or from a nosebleed. But this extra blood is there for good reason—it'll help protect your baby when you stand up or lie down, and it safeguards against the blood loss you'll experience during labor and delivery.";
            case 10:
                return "Loving your new, dewy glow? Thank the pregnancy hormones HCG and progesterone (they increase the number of oil glands in your face, making your complexion shinier and smoother) and your boosted blood volume, which can make your skin slightly flushed and plump.";
            case 11:
                return "Have you started to show yet? If this is your first pregnancy, you may just feel bloated, kind of like after a big meal. But some women have a little baby-belly pooch by the end of the first trimester. After all, your uterus is now the size of a grapefruit.";
            case 12:
                return "Until now, your uterus has fit snugly within your pelvis. But as it begins to move north this week, the pressure on your bladder should start to let up a bit. This also means your belly has the room to swell, giving you that slightly rounded, \"Yes, I'm pregnant!\" look.";
            case 13:
                return "From tripping to dropping dishes, you might be feeling clumsier these days. Relaxin, another hormone that messes with you during pregnancy, loosens up your ligaments and joints in preparation for birth.";
            case 14:
                return "You've reached the second trimester! You can rest easier knowing that your risk of miscarriage drops substantially at the end of this week—75 percent of miscarriages occur in the first trimester. If you're feeling on the up-and-up, thank your hormones. As levels of HCG drop, and estrogen and progesterone shift again, you've entered the \"feel-good\" trimester—typically marked by an energy burst and appetite increase.";
            case 15:
                return "If you take a peek in the mirror, you might notice yet another set of strange pregnancy symptoms: Skin darkening—most commonly around your nipples, areolas, navel, armpits and inner thighs—affects more than 90 percent of moms-to-be. And if you have dark hair and fair skin, you're also more prone to a condition called chloasma (aka \"the mask of pregnancy\")—a darkening around the eyes, nose, and cheeks.";
            case 16:
                return "Got butterflies in your belly? It could very well be your baby kicking! Most moms-to-be experience this major pregnancy milestone between their 16th and 20th weeks, but don't expect any big karate chops just yet. ";
            case 17:
                return "Your belly may have been rounding out for a few weeks now, but pretty soon it's going to start to really pop (so everyone will be able to tell you're actually pregnant—\u200Bas opposed to simply more snack-happy). And once the world at large can see that you're expecting, get ready for lots of knowing smiles!";
            case 18:
                return "You might be feeling your baby really kick now! These kicks will be bigger and more defined than the popcorn popping, butterfly feeling of quickening that you've previously felt.";
            case 19:
                return "Not as graceful as you used to be? Don't worry if you're a bit more wobbly these days; your growing belly has shifted your center of gravity, which can make you more prone to slips and spills. You'll adjust eventually, subconsciously tweaking your posture and gait to offset that expanding tummy.";
            case 20:
                return "This week is a cause for celebration—you've reached the halfway point of your pregnancy! If you turn to the side these days, others will really notice a change in your profile—you definitely look pregnant now.";
            case 21:
                return "When you look in the mirror these days, what do you see? Sexy curves or, well, Shamu? It's unlikely that you'll feel great about your body all the time—every woman has at least one of those \"Oh, my gosh, what's happening to me?\" moments during pregnancy. But let us state the obvious: You are not fat—you are pregnant! ";
            case 22:
                return "Feeling dizzy? This is another normal pregnancy side effect, and it's due to the fact that your blood pressure has dropped. Your blood can't move as fast as it used to, so you may feel lightheaded when you stand up or after standing for a long period of time. ";
            case 23:
                return "That baby of yours sure needs a lot of nutrients. As she uses more and more of the vitamins and minerals passing through your body, you might need an extra dose. That's why doctors sometimes prescribe iron supplements, in addition to your prenatal vitamin, during the second half of pregnancy.";
            case 24:
                return "The only thing shrinking right now may be your libido—so it's totally A-OK if your cravings for French fries trump your desire to do the deed. Many pregnant women report that their libidos are all over the map throughout these nine months.";
            case 25:
                return "Get ready to kiss your petite little belly goodbye soon (if you haven't already)—your uterus is growing big-time! It is now about the size of a soccer ball. As your uterus continues to expand upward—the top is nearly midway between your breasts and belly button now.";
            case 26:
                return "By now, you've probably gained around 15 pounds—and possibly even more, depending on your pre-pregnancy weight. It may sound like a lot and you may not be loving your pregnancy size, but remember it's a necessity.";
            case 27:
                return "Are you usually one of those shy, don't-rock-the-boat types? Many women find that being pregnant makes them more assertive than usual—and better equipped to set boundaries at home, at work, or anywhere.";
            case 28:
                return "You've made it to the third trimester and even though you're on the home stretch, you're probably about ready to be done with pregnancy! In your final trimester, you'll feel the physical toll of pregnancy. ";
            case 29:
                return "During pregnancy you'll experience a lot of surprising symptoms, and one that can happen during the third trimester is leaky breasts. The yellowish, thin fluid is colostrum, which is the precursor to breast milk. Usually you'll notice only a drop or two, if any at all, but if the flow becomes greater, you can put nursing pads inside your bra to keep things under control. ";
            case 30:
                return "As your skin expands to accommodate baby, stretch marks aren't always the only side effect. An estimated 20 percent of expectant moms also experience itchy skin. Your doctor may recommend antihistamines or ointments, but a calming lotion can also provide relief. As for those stretch marks? At least half of moms-to-be get them, usually in the sixth and seventh months of pregnancy.";
            case 31:
                return "You may be noticing more hip and lower-back pain. What gives? Pregnancy hormones are relaxing the ligaments and tendons throughout your pelvic area so that the bones can spread to make room for delivery. ";
            case 32:
                return "Because your uterus is now almost five inches above your belly, your baby is pressing more intensely on your internal organs. Expect symptoms such as urine leakage, heartburn, and breathlessness to intensify during the last few months.";
            case 33:
                return "Not all contractions mean that you're going to deliver soon, which at this point should have you breathing a sigh of relief! Just like your unborn baby's body takes time to develop and mature before he's ready for his birth, your body readies itself for labor by practicing. ";
            case 34:
                return "Feeling like you couldn't possibly get any bigger right now? Luckily, weight gain often plateaus or slows down by this time. ";
            case 35:
                return "Another labor signal to watch for is extra-thick vaginal discharge that's pink or even a bit blood-tinged. This is the start of your mucus plug dropping. The mucus plug is a ball of tissue that's been blocking your cervical opening during pregnancy to keep your uterus safe from germs.";
            case 36:
                return "Does your belly feel a bit lighter these days? It's called dropping, lightening, or engagement, and it's common around this time, as your baby settles lower into your pelvis to get ready for her big move outta there.";
            case 37:
                return "Sex might be the last thing on your mind now, but some experts believe it's beneficial and most agree it's harmless. At this point, your cervix is engorged with blood and feels sensitive, so you may see a little spotting after sex.";
            case 38:
                return "Your water could break any day now, and if you're thinking that means you're doomed for an embarrassing public scene, think again. Most women start to notice a wetness running down the leg, not a sudden gushing of water to the floor, so you should have enough time to get to a bathroom and call your doctor.";
            case 39:
                return "Less than 5 percent of women give birth on their actual due dates, which means your baby could come a few hours from now or not for another two weeks. But you're probably so attuned to the possibility of labor that every little twinge makes you think \"Is this it?\" Labor may begin in several ways: mild cramps (the most common scenario), your water breaking, or, if you have a scheduled induction or C-section, a trip to the hospital.";
            case 40:
                return "Your baby is due this week, and if you're lucky he will actually arrive this week! Soon you'll feel your first real contractions. They'll come fast and furious once you're in active labor—lasting up to a minute each, or even a bit longer—and yes, they huuuurt. You'll feel this intense pain radiating through your stomach, lower back and upper thighs.";
            default:
                return "Still pregnant? That's okay. Babies come to term anywhere between 38 and 42 weeks—your 40-week due date simply marks the midpoint of this period. If your delivery is scheduled, you'll check into the hospital and either be prepped for a C-section or, if you're going to deliver vaginally, given something to induce labor, like a prostaglandin gel to soften or ripen your cervix or an IV drip of pitocin (a synthetic version of the hormone oxytocin) to start up contractions.";
        }
    }

}
