package com.pingfangx.demo.androidx.activity.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pingfangx.demo.androidx.base.BaseListActivity
import com.pingfangx.demo.androidx.base.extension.INTENT_EXTRA_LAYOUT
import com.pingfangx.demo.androidx.base.extension.INTENT_EXTRA_TITLE
import com.pingfangx.demo.androidx.base.widget.recycler.BaseRecyclerViewAdapter
import com.pingfangx.demo.androidx.base.widget.recycler.BaseTextAdapter
import java.util.regex.Pattern

/**
 * 预览各种 view
 *
 * @author pingfangx
 * @date 2019/6/11
 */
class PreviewViewsActivity : BaseListActivity() {
    private val mData: MutableList<String> = mutableListOf()
    override fun createAdapter(): RecyclerView.Adapter<*> {
        initData()
        return BaseTextAdapter<String>(this, mData)
                .setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<String> {
                    override fun onItemClick(view: View, position: Int, t: String) {
                        startActivity(t)
                    }
                })
    }


    fun startActivity(name: String) {
        startActivity(Intent(this, this::class.java)
                .putExtra(INTENT_EXTRA_LAYOUT, name)
                .putExtra(INTENT_EXTRA_TITLE, name))
    }

    override fun setContentView() {
        val name: String? = intent.getStringExtra(INTENT_EXTRA_LAYOUT)
        if (name?.isNotEmpty() == true) {
            mIsDetail = true
            val clazz = classLoader.loadClass(name).asSubclass(View::class.java)
            val view: View = try {
                val constructor = clazz.getConstructor(Context::class.java)
                constructor.newInstance(this)
            } catch (e: Exception) {
                val textView = TextView(this)
                textView.text = e.toString()
                textView
            }
            view.setBackgroundColor(Color.GRAY)
            val frameLayout = FrameLayout(this)
            val size = 1000
            frameLayout.addView(view, FrameLayout.LayoutParams(size, size))
            setContentView(frameLayout)
        } else {
            super.setContentView()
        }
    }

    /**
     * 为什么不能通过反射获取所有的子类？一定是我打开的方式不对
     */
    private fun initData() {
        val tree: String = "View (android.view)\n" +
                "    ViewStub (android.view)\n" +
                "    GhostViewApi14 (androidx.transition)\n" +
                "    SurfaceView (android.view)\n" +
                "        GLSurfaceView (android.opengl)\n" +
                "        VideoView (android.widget)\n" +
                "    ProgressBar (android.widget)\n" +
                "        AbsSeekBar (android.widget)\n" +
                "            SeekBar (android.widget)\n" +
                "                AppCompatSeekBar (androidx.appcompat.widget)\n" +
                "            RatingBar (android.widget)\n" +
                "                AppCompatRatingBar (androidx.appcompat.widget)\n" +
                "        ContentLoadingProgressBar (androidx.core.widget)\n" +
                "    Space (android.widget)\n" +
                "    ImageView (android.widget)\n" +
                "        ImageButton (android.widget)\n" +
                "            ZoomButton (android.widget)\n" +
                "            AppCompatImageButton (androidx.appcompat.widget)\n" +
                "                CheckableImageButton (com.google.android.material.internal)\n" +
                "            VisibilityAwareImageButton (com.google.android.material.internal)\n" +
                "                FloatingActionButton (com.google.android.material.floatingactionbutton)\n" +
                "        CircleImageView (androidx.swiperefreshlayout.widget)\n" +
                "        AppCompatImageView (androidx.appcompat.widget)\n" +
                "            ActionMenuPresenter 中的 OverflowMenuButton  (androidx.appcompat.widget)\n" +
                "        QuickContactBadge (android.widget)\n" +
                "    ViewStubCompat (androidx.appcompat.widget)\n" +
                "    ConstraintHelper (androidx.constraintlayout.widget)\n" +
                "        Barrier (androidx.constraintlayout.widget)\n" +
                "        Group (androidx.constraintlayout.widget)\n" +
                "    MediaRouteButton (android.app)\n" +
                "    TextureView (android.view)\n" +
                "    ViewOverlayApi14 中的 OverlayViewGroup  中的 TouchInterceptor  (androidx.transition)\n" +
                "    ViewGroup (android.view)\n" +
                "        LinearLayout (android.widget)\n" +
                "            FitWindowsLinearLayout (androidx.appcompat.widget)\n" +
                "            SnackbarContentLayout (com.google.android.material.snackbar)\n" +
                "            ZoomControls (android.widget)\n" +
                "            SearchView (android.widget)\n" +
                "            TableLayout (android.widget)\n" +
                "            TextInputLayout (com.google.android.material.textfield)\n" +
                "            ListMenuItemView (androidx.appcompat.view.menu)\n" +
                "            ButtonBarLayout (androidx.appcompat.widget)\n" +
                "            RadioGroup (android.widget)\n" +
                "            AppBarLayout (com.google.android.material.appbar)\n" +
                "            TabLayout 中的 SlidingTabIndicator  (com.google.android.material.tabs)\n" +
                "            ScrollingTabContainerView 中的 TabView  (androidx.appcompat.widget)\n" +
                "            ActionMenuView (android.widget)\n" +
                "            TableRow (android.widget)\n" +
                "            TabWidget (android.widget)\n" +
                "            TabLayout 中的 TabView  (com.google.android.material.tabs)\n" +
                "            NumberPicker (android.widget)\n" +
                "            CircularRevealLinearLayout (com.google.android.material.circularreveal)\n" +
                "            ActivityChooserView 中的 InnerLayout  (androidx.appcompat.widget)\n" +
                "            NestedScrollingLinearLayout (com.pingfangx.demo.androidx.activity.androidx.core.view)\n" +
                "        DrawerLayout (androidx.drawerlayout.widget)\n" +
                "        RecyclerView (com.pingfangx.demo.androidx.activity.view.recyclerview.widget)\n" +
                "        FrameLayout (android.widget)\n" +
                "            CalendarView (android.widget)\n" +
                "            BottomNavigationView (com.google.android.material.bottomnavigation)\n" +
                "            FitWindowsFrameLayout (androidx.appcompat.widget)\n" +
                "            NestedScrollView (androidx.core.widget)\n" +
                "            ContentFrameLayout (androidx.appcompat.widget)\n" +
                "                AppCompatDelegateImpl 中的 ListMenuDecorView  (androidx.appcompat.app)\n" +
                "            BaseTransientBottomBar 中的 SnackbarBaseLayout  (com.google.android.material.snackbar)\n" +
                "                Snackbar 中的 SnackbarLayout  (com.google.android.material.snackbar)\n" +
                "            MediaController (android.widget)\n" +
                "            TabHost (android.widget)\n" +
                "                FragmentTabHost (androidx.fragment.app)\n" +
                "            GestureOverlayView (android.gesture)\n" +
                "            DatePicker (android.widget)\n" +
                "            CollapsingToolbarLayout (com.google.android.material.appbar)\n" +
                "            BottomNavigationItemView (com.google.android.material.bottomnavigation)\n" +
                "            HorizontalScrollView (android.widget)\n" +
                "                TabLayout (com.google.android.material.tabs)\n" +
                "                ScrollingTabContainerView (androidx.appcompat.widget)\n" +
                "            MenuItemWrapperICS 中的 CollapsibleActionViewWrapper  (androidx.appcompat.view.menu)\n" +
                "            ScrollView (android.widget)\n" +
                "            AppWidgetHostView (android.appwidget)\n" +
                "            ViewAnimator (android.widget)\n" +
                "                ViewSwitcher (android.widget)\n" +
                "                    TextSwitcher (android.widget)\n" +
                "                    ImageSwitcher (android.widget)\n" +
                "                ViewFlipper (android.widget)\n" +
                "            ActionBarContainer (androidx.appcompat.widget)\n" +
                "            TimePicker (android.widget)\n" +
                "            CircularRevealFrameLayout (com.google.android.material.circularreveal)\n" +
                "                TransformationChildLayout (com.google.android.material.transformation)\n" +
                "            ScrimInsetsFrameLayout (com.google.android.material.internal)\n" +
                "                NavigationView (com.google.android.material.navigation)\n" +
                "            CardView (androidx.cardview.widget)\n" +
                "                MaterialCardView (com.google.android.material.card)\n" +
                "                    CircularRevealCardView (com.google.android.material.circularreveal.cardview)\n" +
                "                        TransformationChildCard (com.google.android.material.transformation)\n" +
                "            ScrollerTestView (com.pingfangx.demo.androidx.activity.android.widget)\n" +
                "        ViewPager (androidx.viewpager.widget)\n" +
                "        ViewPager2 (androidx.viewpager2.widget)\n" +
                "        LinearLayoutCompat (androidx.appcompat.widget)\n" +
                "            ActionMenuView (androidx.appcompat.widget)\n" +
                "            AlertDialogLayout (androidx.appcompat.widget)\n" +
                "            ForegroundLinearLayout (com.google.android.material.internal)\n" +
                "                NavigationMenuItemView (com.google.android.material.internal)\n" +
                "            SearchView (androidx.appcompat.widget)\n" +
                "        PagerTitleStrip (androidx.viewpager.widget)\n" +
                "            PagerTabStrip (androidx.viewpager.widget)\n" +
                "        AbsoluteLayout (android.widget)\n" +
                "            WebView (android.webkit)\n" +
                "        Toolbar (android.widget)\n" +
                "        SlidingDrawer (android.widget)\n" +
                "        AdapterView (android.widget)\n" +
                "            AdapterViewAnimator (android.widget)\n" +
                "                StackView (android.widget)\n" +
                "                AdapterViewFlipper (android.widget)\n" +
                "            AbsSpinner (android.widget)\n" +
                "                Gallery (android.widget)\n" +
                "                Spinner (android.widget)\n" +
                "                    AppCompatSpinner (androidx.appcompat.widget)\n" +
                "            AbsListView (android.widget)\n" +
                "                GridView (android.widget)\n" +
                "                    MaterialCalendarGridView (com.google.android.material.picker)\n" +
                "                ListView (android.widget)\n" +
                "                    DropDownListView (androidx.appcompat.widget)\n" +
                "                        MenuPopupWindow 中的 MenuDropDownListView  (androidx.appcompat.widget)\n" +
                "                    AlertController 中的 RecycleListView  (androidx.appcompat.app)\n" +
                "                    ExpandedMenuView (androidx.appcompat.view.menu)\n" +
                "                    ExpandableListView (android.widget)\n" +
                "        RecyclerView (androidx.recyclerview.widget)\n" +
                "            ViewPager2 中的 RecyclerViewImpl  (androidx.viewpager2.widget)\n" +
                "            NavigationMenuView (com.google.android.material.internal)\n" +
                "        SlidingPaneLayout (androidx.slidingpanelayout.widget)\n" +
                "        GridLayout (android.widget)\n" +
                "            CircularRevealGridLayout (com.google.android.material.circularreveal)\n" +
                "        TvView (android.media.tv)\n" +
                "        AbsActionBarView (androidx.appcompat.widget)\n" +
                "            ActionBarContextView (androidx.appcompat.widget)\n" +
                "        CoordinatorLayout (androidx.coordinatorlayout.widget)\n" +
                "            CircularRevealCoordinatorLayout (com.google.android.material.circularreveal.coordinatorlayout)\n" +
                "        SwipeRefreshLayout (androidx.swiperefreshlayout.widget)\n" +
                "        ActionBarOverlayLayout (androidx.appcompat.widget)\n" +
                "        FragmentBreadCrumbs (android.app)\n" +
                "        BaselineLayout (com.google.android.material.internal)\n" +
                "        BottomNavigationMenuView (com.google.android.material.bottomnavigation)\n" +
                "        ActivityChooserView (androidx.appcompat.widget)\n" +
                "        ViewOverlayApi14 中的 OverlayViewGroup  (androidx.transition)\n" +
                "        ConstraintLayout (androidx.constraintlayout.widget)\n" +
                "        RelativeLayout (android.widget)\n" +
                "            TwoLineListItem (android.widget)\n" +
                "            DialerFilter (android.widget)\n" +
                "            MaterialButtonToggleGroup (com.google.android.material.button)\n" +
                "            CircularRevealRelativeLayout (com.google.android.material.circularreveal)\n" +
                "        Toolbar (androidx.appcompat.widget)\n" +
                "            BottomAppBar (com.google.android.material.bottomappbar)\n" +
                "            MaterialToolbar (com.google.android.material.appbar)\n" +
                "        Constraints (androidx.constraintlayout.widget)\n" +
                "        FlowLayout (com.google.android.material.internal)\n" +
                "            ChipGroup (com.google.android.material.chip)\n" +
                "    KeyboardView (android.inputmethodservice)\n" +
                "    TextView (android.widget)\n" +
                "        AppCompatTextView (androidx.appcompat.widget)\n" +
                "            ActionMenuItemView (androidx.appcompat.view.menu)\n" +
                "            DialogTitle (androidx.appcompat.widget)\n" +
                "        DigitalClock (android.widget)\n" +
                "        CheckedTextView (android.widget)\n" +
                "            AppCompatCheckedTextView (androidx.appcompat.widget)\n" +
                "        TextClock (android.widget)\n" +
                "        Button (android.widget)\n" +
                "            CompoundButton (android.widget)\n" +
                "                Switch (android.widget)\n" +
                "                CheckBox (android.widget)\n" +
                "                    AppCompatCheckBox (androidx.appcompat.widget)\n" +
                "                        Chip (com.google.android.material.chip)\n" +
                "                        MaterialCheckBox (com.google.android.material.checkbox)\n" +
                "                SwitchCompat (androidx.appcompat.widget)\n" +
                "                    SwitchMaterial (com.google.android.material.switchmaterial)\n" +
                "                RadioButton (android.widget)\n" +
                "                    AppCompatRadioButton (androidx.appcompat.widget)\n" +
                "                        MaterialRadioButton (com.google.android.material.radiobutton)\n" +
                "                ToggleButton (android.widget)\n" +
                "                    AppCompatToggleButton (androidx.appcompat.widget)\n" +
                "            AppCompatButton (androidx.appcompat.widget)\n" +
                "                MaterialButton (com.google.android.material.button)\n" +
                "                    ExtendedFloatingActionButton (com.google.android.material.floatingactionbutton)\n" +
                "            BaseDoubleClickableButton (com.pingfangx.demo.androidx.activity.view.textview)\n" +
                "                DoubleClickableButtonByPostRunnable (com.pingfangx.demo.androidx.activity.view.textview)\n" +
                "                DoubleClickableButtonBySendMessage (com.pingfangx.demo.androidx.activity.view.textview)\n" +
                "        Chronometer (android.widget)\n" +
                "        EditText (android.widget)\n" +
                "            ExtractEditText (android.inputmethodservice)\n" +
                "            AppCompatEditText (androidx.appcompat.widget)\n" +
                "                TextInputEditText (com.google.android.material.textfield)\n" +
                "            AutoCompleteTextView (android.widget)\n" +
                "                AppCompatAutoCompleteTextView (androidx.appcompat.widget)\n" +
                "                    SearchView 中的 SearchAutoComplete  (androidx.appcompat.widget)\n" +
                "                MultiAutoCompleteTextView (android.widget)\n" +
                "                    AppCompatMultiAutoCompleteTextView (androidx.appcompat.widget)\n" +
                "    Placeholder (androidx.constraintlayout.widget)\n" +
                "    TabItem (com.google.android.material.tabs)\n" +
                "    AnalogClock (android.widget)\n" +
                "    Space (androidx.legacy.widget)\n" +
                "    Guideline (androidx.constraintlayout.widget)\n"
        val lines = tree.split("\n")
        val pattern = Pattern.compile("\\s*(.*?)\\s*\\((.*?)\\)")
        for (line in lines) {
            if (line.contains("中")) {
                continue
            }
            val matcher = pattern.matcher(line)
            if (matcher.find()) {
                val className = matcher.group(2) + "." + matcher.group(1)
                if (!mData.contains(className)) {
                    mData.add(className)
                }
            }
        }
    }
}