package com.paula.mypregnancy.controller;

/*
 * BabyFragment
 *
 * An implementation of a pregnancy tracker app.
 * Development of mobile applications
 * Umeå Universitet, summer course 2019
 *
 * Paula D'Cruz
 *
 * This is one of the controller classes. It controls the view where the user can see information about the foetus.
 *
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.paula.mypregnancy.R;
import com.paula.mypregnancy.model.DueDate;


public class BabyFragment extends Fragment {

    private Toolbar mToolbar;
    private Button mTrackPregnancyButton;
    private TextView mWeekTextView, mBabyInformation;
    private DueDate mDueDate;
    private static final String DUE_DATE_PARCEL = "com.paula.mypregnancy.model.DueDate";
    private final static String TAG = "BabyFragment";

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
    }

    /**
     * onCreateView
     *
     * this method sets up the entire baby view. it constructs all UI artifacts such as ImageView, a TextView with
     * information about what developments the foetus is going through in the current week of pregnancy,
     * as well as a Button to take the user back to the Track pregnancy view.
     *
     * @param inflater the layoutInflator object that is used to inflate view to the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_baby, container, false);
        if (getArguments() != null) {
            mDueDate = getArguments().getParcelable("dueDate");
        }
        mToolbar = view.findViewById(R.id.action_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);

        mWeekTextView = view.findViewById(R.id.weekNo);
        mBabyInformation = view.findViewById(R.id.babyInformation);
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
     * updateView
     *
     * this method is called to determine which information should be shown in the view, based on the pregnancy week.
     *
     */

    private void updateView() {
        int week = mDueDate.getPregnancyWeek();
        if (week == -1) {
            mWeekTextView.setText("Overdue");
        } else {
            mWeekTextView.setText(Integer.toString(mDueDate.getPregnancyWeek()));
        }
        mBabyInformation.setText(getInformation());
    }

    /**
     * getInformation
     *
     * a long switch case that will return a hardcoded String with relevant information the the user, based on how many
     * weeks pregnant they are.
     *
     * @return a String with information
     */

    private String getInformation(){
        switch(mDueDate.getPregnancyWeek()){
            case 0:
                return "Not actually pregnant yet";
            case 1:
                return "Not actually pregnant yet";
            case 2:
                return "At the start of this week, you ovulate. Your egg is fertilized 12 to 24 hours later if a sperm penetrates it – and this simple biological occurrence begins a series of increasingly complicated processes that leads to a new human life, if all goes well. Over the next several days, the fertilized egg will start dividing into multiple cells as it travels down the fallopian tube, enters your uterus, and starts to burrow into the uterine lining.";
            case 3:
                return "Now nestled in the nutrient-rich lining of your uterus is a microscopic ball of hundreds of rapidly multiplying cells that will develop into your baby. This ball, called a blastocyst, has begun to produce the pregnancy hormone hCG, which tells your ovaries to stop releasing eggs.";
            case 4:
                return "Your ball of cells is now officially an embryo. You're now about 4 weeks from the beginning of your last period. It's around this time – when your next period would normally be due – that you might be able to get a positive result on a home pregnancy test.";
            case 5:
                return "Your baby resembles a tadpole more than a human, but is growing fast. The circulatory system is beginning to form, and the tiny \"heart\" will start to beat this week.";
            case 6:
                return "Your baby's nose, mouth and ears are starting to take shape, and the intestines and brain are beginning to develop.";
            case 7:
                return "Your baby has doubled in size since last week, but still has a tail, which will soon disappear. Little hands and feet that look more like paddles are emerging from the developing arms and legs.";
            case 8:
                return "Your baby has started moving around, though you won't feel movement yet. Nerve cells are branching out, forming primitive neural pathways. Breathing tubes now extend from his throat to his developing lungs.";
            case 9:
                return "Your baby's basic physiology is in place (she even has tiny earlobes), but there's much more to come. Her embryonic tail has disappeared. She weighs just a fraction of an ounce but is about to start gaining weight fast.";
            case 10:
                return "Your embryo has completed the most critical portion of development. His skin is still translucent, but his tiny limbs can bend and fine details like nails are starting to form.";
            case 11:
                return "Your baby is almost fully formed. She's kicking, stretching, and even hiccupping as her diaphragm develops, although you can't feel any activity yet.";
            case 12:
                return "This week your baby's reflexes kick in: His fingers will soon begin to open and close, toes will curl, and his mouth will make sucking movements. He'll feel it if you gently poke your tummy – though you won't feel his movements yet.";
            case 13:
                return "This is the last week of your first trimester. Your baby's tiny fingers now have fingerprints, and her veins and organs are clearly visible through her skin. If you're having a girl, her ovaries contain more than 2 million eggs.";
            case 14:
                return "Your baby's brain impulses have begun to fire and he's using his facial muscles. His kidneys are working now, too. If you have an ultrasound, you may even see him sucking his thumb.";
            case 15:
                return "Your baby's eyelids are still fused shut, but she can sense light. If you shine a flashlight on your tummy, she'll move away from the beam. Ultrasounds done this week may reveal your baby's sex.";
            case 16:
                return "The patterning on your baby's scalp has begun, though the hair isn't visible yet. His legs are more developed – find out when you're likely to feel your baby kick! His head is more upright, and his ears are close to their final position.";
            case 17:
                return "Your baby can move her joints, and her skeleton – formerly soft cartilage – is now hardening to bone. The umbilical cord is growing stronger and thicker.";
            case 18:
                return "Your baby is flexing his arms and legs, and you may be able to feel those movements. Internally, a protective coating of myelin is forming around his nerves.";
            case 19:
                return "Your baby's senses – smell, vision, touch, taste and hearing – are developing and she may be able to hear your voice. Talk, sing or read out loud to her, if you feel like it.";
            case 20:
                return "Your baby can swallow now and his digestive system is producing meconium, the dark, sticky goo that he'll pass in his first poop – either in his diaper or in the womb during delivery.";
            case 21:
                return "Your baby's movements have gone from flutters to full-on kicks and jabs against the walls of your womb. You may start to notice patterns as you become more familiar with her activity.";
            case 22:
                return "Your baby now looks almost like a miniature newborn. Features such as lips and eyebrows are more distinct, but the pigment that will color his eyes isn't present yet.";
            case 23:
                return "Your baby's ears are getting better at picking up sounds. After birth, she may recognize some noises outside the womb that she's hearing inside now.";
            case 24:
                return "Your baby cuts a pretty long and lean figure, but chubbier times are coming. His skin is still thin and translucent, but that will begin to change soon too.";
            case 25:
                return "Your baby's wrinkled skin is starting to fill out with baby fat, making her look more like a newborn. Her hair is beginning to come in, and it has color and texture.";
            case 26:
                return "Your baby is now inhaling and exhaling amniotic fluid, which helps develop his lungs. These breathing movements are good practice for that first breath of air at birth.";
            case 27:
                return "This is the last week of your second trimester. Your baby now sleeps and wakes on a regular schedule, and her brain is very active. Her lungs aren't fully formed, but they could function outside the womb with medical help.";
            case 28:
                return "Your baby's eyesight is developing, which may enable her to sense light filtering in from the outside. She can blink, and her eyelashes have grown in.";
            case 29:
                return "Your baby's muscles and lungs are busy getting ready to function in the outside world, and his head is growing to make room for his developing brain.";
            case 30:
                return "Your baby is surrounded by a pint and a half of amniotic fluid, although there will be less of it as she grows and claims more space inside your uterus.";
            case 31:
                return "Your baby can now turn his head from side to side. A protective layer of fat is accumulating under his skin, filling out his arms and legs.";
            case 32:
                return "You're probably gaining about a pound a week. Half of that goes straight to your baby, who will gain one-third to half her birth weight in the next seven weeks in preparation for life outside the womb.";
            case 33:
                return "The bones in your baby's skull aren't fused yet. That allows them to shift as his head squeezes through the birth canal. They won't fully fuse until adulthood.";
            case 34:
                return "Your baby's central nervous system is maturing, as are her lungs. Babies born between 34 and 37 weeks who have no other health problems usually do well in the long run.";
            case 35:
                return "It's getting snug inside your womb! Your baby's kidneys are fully developed, and his liver can process some waste products.";
            case 36:
                return "Your baby is gaining about an ounce a day. She's also losing most of the fine down that covered her body, along with the vernix casosa, a waxy substance that was protecting her skin until now.";
            case 37:
                return "Your due date is very close, but though your baby looks like a newborn, he isn't quite ready for the outside world. Over the next two weeks his lungs and brain will fully mature.";
            case 38:
                return "Are you curious about your baby's eye color? Her irises are not fully pigmented, so if she's born with blue eyes, they could change to a darker color up until she's about a year old.";
            case 39:
                return "Your baby's physical development is complete, but he's still busy putting on fat he'll need to help regulate his body temperature in the outside world.";
            case 40:
                return "If you're past your due date you may not be as late as you think, especially if you calculated it solely based on the day of your last period. Sometimes women ovulate later than expected.";
            default:
                return "Your baby is now considered late-term. Going more than two weeks past your due date can put you and your baby at risk for complications, so your provider will probably talk to you about inducing labor.";
        }
    }
}
