package com.example.user.surpriseu;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //登入
    private TextView textViewUserID;
    private TextView textViewUserName;
    private TextView textViewResult;

    private String userID;
    private String userName;
    private String result;

    //廣告
    private ViewPager viewPagerAds;
    private LinearLayout linearLayoutAds;
    private List<View> viewListAds;
    private ImageView[] imageViewsAds;
    private ImageView imageViewAds;
    private AdPageAdapter adpageAdapterAds;
    private AtomicInteger atomicIntegerAds = new AtomicInteger(0);
    private boolean isContinueAds = true;


    //切換頁面
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    //螢幕
    private static int intScreenHeight;
    private static int intScreenWidth;


/*
    private TabLayout tabLayoutTab;
    private ViewPager viewPagerTab;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //登入
        //textViewUserID = (TextView) findViewById(R.id.textView2);
        //textViewUserName = (TextView) findViewById(R.id.textView4);
        //textViewResult = (TextView) findViewById(R.id.textView5);

        userID = getIntent().getStringExtra("userID");         //取得傳遞過來的資料
        userName = getIntent().getStringExtra("userName");   //取得傳遞過來的資料
        result = getIntent().getStringExtra("result");           //取得傳遞過來的資料
        System.out.println("userID : " + userID);
        //login();    //Login

        //廣告
        linearLayoutAds = (LinearLayout) findViewById((R.id.viewPagerAdsContent));

        initViewPagerAds(); //Start ads


        //切換頁面
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
/*
        tabLayoutTab = (android.support.design.widget.TabLayout) findViewById(R.id.tabs);
        initTab();
*/

        //螢幕
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        intScreenWidth = dm.widthPixels;   //螢幕的寬
        intScreenHeight = dm.heightPixels;  //螢幕的高


        //原本的
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //片段管理者
       // android.app.FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Login
    private void login(){
        textViewResult.setText(result);                 //顯示result

        if(result.equals("登入成功")) {
            textViewUserID.setText(userID);          //顯示userID
            textViewUserName.setText(userName);    //顯示userName
        }
    }


    /********************************* 廣告(開始) *********************************/

    //初始化廣告
    private void initViewPagerAds(){
        //創建ViewPager
        viewPagerAds = new ViewPager(this);

        //獲取屏幕像素相關訊息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //根據屏幕訊息設置ViewPager廣告容器的高度
        viewPagerAds.setLayoutParams(new ViewGroup.LayoutParams(dm.widthPixels, dm.heightPixels * 2 / 5));

        //將ViewPager容器設置到布局文件父容器中
        linearLayoutAds.addView(viewPagerAds);

        //初始化並放置所有圖片
        initPageAdapter();

        //初始化點點
        initCirclePoint();

        viewPagerAds.setAdapter(adpageAdapterAds);
        viewPagerAds.setOnPageChangeListener(new AdPageChangeListener());

        new Thread(new Runnable(){
            @Override
            public void run() {
                while (true) {
                    if (isContinueAds){
                        viewHandler.sendEmptyMessage(atomicIntegerAds.get());
                        atomicOption();
                    }
                }
            }
        }).start();
    }

    private void atomicOption() {
        atomicIntegerAds.incrementAndGet();
        if (atomicIntegerAds.get() > imageViewsAds.length - 1) {
            atomicIntegerAds.getAndAdd(-5);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
    }

    //每隔固定時間切換廣告欄圖片
    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            viewPagerAds.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }

    };

    //  初始化並放置所有圖片
    private void initPageAdapter() {
        viewListAds = new ArrayList<View>();

        ImageView img1 = new ImageView(this);
        img1.setBackgroundResource(R.drawable.view_add_1);
        viewListAds.add(img1);

        ImageView img2 = new ImageView(this);
        img2.setBackgroundResource(R.drawable.view_add_2);
        viewListAds.add(img2);

        ImageView img3 = new ImageView(this);
        img3.setBackgroundResource(R.drawable.view_add_3);
        viewListAds.add(img3);

        ImageView img4 = new ImageView(this);
        img4.setBackgroundResource(R.drawable.view_add_4);
        viewListAds.add(img4);

        ImageView img5 = new ImageView(this);
        img5.setBackgroundResource(R.drawable.view_add_5);
        viewListAds.add(img5);

        adpageAdapterAds = new AdPageAdapter(viewListAds);
    }

    // 初始化並放置點點
    private void initCirclePoint(){
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroupAds);
        imageViewsAds = new ImageView[viewListAds.size()];
        //广告栏的小圆点图标
        for (int i = 0; i < viewListAds.size(); i++) {
            //创建一个ImageView, 并设置宽高. 将该对象放入到数组中
            imageViewAds = new ImageView(this);
            imageViewAds.setLayoutParams(new ViewGroup.LayoutParams(30,30));
            imageViewsAds[i] = imageViewAds;

            //初始值, 默认第0个选中
            if (i == 0) {
                imageViewsAds[i]
                        .setBackgroundResource(R.drawable.point_focused);
            } else {
                imageViewsAds[i]
                        .setBackgroundResource(R.drawable.point_unfocused);
            }
            //将小圆点放入到布局中
            group.addView(imageViewsAds[i]);
        }
    }

    // ViewPager 页面改变监听器
    private final class AdPageChangeListener implements ViewPager.OnPageChangeListener {

        // 页面滚动状态发生改变的时候触发
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        // 页面滚动的时候触发
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        // 页面选中的时候触发
        @Override
        public void onPageSelected(int arg0) {
            //獲取當前的頁面是哪個頁面
            atomicIntegerAds.getAndSet(arg0);
            //重新设置原点布局集合
            for (int i = 0; i < imageViewsAds.length; i++) {
                imageViewsAds[arg0]
                        .setBackgroundResource(R.drawable.point_focused);
                if (arg0 != i) {
                    imageViewsAds[i]
                            .setBackgroundResource(R.drawable.point_unfocused);
                }
            }
        }
    }

    // AdPageAdapter Class
    private final class AdPageAdapter extends PagerAdapter {
        private List<View> views = null;

        // 初始化数据源, 即View数组
        public AdPageAdapter(List<View> views) {
            this.views = views;
        }

        // 从ViewPager中删除集合中对应索引的View对象
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        // 获取ViewPager的个数
        @Override
        public int getCount() {
            return views.size();
        }


        // 从View集合中获取对应索引的元素, 并添加到ViewPager中
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position), 0);
            return views.get(position);
        }

        // 是否将显示的ViewPager页面与instantiateItem返回的对象进行关联
        // 这个方法是必须实现的
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /********************************* 廣告(結束) *********************************/
    /********************************* 切換頁面(開始) *********************************


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        private GridView gridView;
        List<Map<String, Object>> items;
        Map<String, Object> item;
        SimpleAdapter adapter;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_pager_tab, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            TextView title = (TextView) rootView.findViewById(R.id.item_title);
            title.setText(String.valueOf(getArguments().getInt(ARG_SECTION_NUMBER)));


            //設定圖片


            gridView = (GridView) rootView.findViewById(R.id.gridview);
            gridView.setNumColumns(2);  //設定為兩欄

            //設定grid view item
            items = new ArrayList<>();
            adapter = new SimpleAdapter(rootView.getContext(),items,R.layout.grid_view
                    ,new String[]{"text","img"},new int[]{R.id.textViewGrid1,R.id.imageView2});

            for(int i=0;i<getArguments().getInt(ARG_SECTION_NUMBER);i++) {
                item = new HashMap<>();
                item.put("text", "Num: " + i);
                item.put("img", i);
                items.add(item);
            }
            madapter();
/*
            GridView gridview=(GridView) rootView.findViewById(R.id.gridview);//找到homepgae..xml中定义gridview 的id
            gridview.setAdapter(new ImageAdapter(gridview.getContext()));//调用ImageAdapter.java
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){//监听事件
                public void onItemClick(AdapterView<?> parent, View view, int p, long id)
                {
                    //Toast.makeText(this, ""+p,Toast.LENGTH_SHORT).show();//显示信息;
                    System.out.println("getArguments().getInt(ARG_SECTION_NUMBER) : " + getArguments().getInt(ARG_SECTION_NUMBER));
                    Log.d("test","action");               }
            });

*/

            return rootView;
        }


        //設定grid view item
        public void madapter(){
            adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data, String textRepresentation) {
                    if(view.getId() == R.id.imageView2){
                        int value = Integer.parseInt(data.toString());
                        ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);

                        if(value == 1)
                            ((ImageView) view).setImageResource(R.drawable.sample_1);
                        else if(value == 2)
                            ((ImageView) view).setImageResource(R.drawable.sample_2);
                        else if(value == 3)
                            ((ImageView) view).setImageResource(R.drawable.sample_3);
                        else if(value == 4)
                            ((ImageView) view).setImageResource(R.drawable.sample_4);
                        else if(value == 5)
                            ((ImageView) view).setImageResource(R.drawable.sample_5);

                        return true;
                    }
                    return false;
                }
            });
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(view.getContext(), "你選擇了" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "分類";
                case 1:
                    return "熱門";
                case 2:
                    return "最新";
                case 3:
                    return "收藏";
                case 4:
                    return "我的交換";
            }
            return null;
        }
    }
    /********************************* 切換頁面(結束) *********************************















    /********************************* 切換頁面(開始) *********************************

    // 初始化切換頁面
    private void initTab(){
        for(int i=0;i<5;i++)
            tabLayoutTab.addTab(tabLayoutTab.newTab().setText("Tab "+(i+1)));

        // tab選擇變為黑色，沒有選擇則為灰色
        tabLayoutTab.setTabTextColors(Color.GRAY, Color.BLACK);

        viewPagerTab = (ViewPager) findViewById(R.id.viewpager);
        viewPagerTab.setAdapter(new SamplePagerAdapter());
        viewPagerTab.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutTab));

        // 設置一開始顯示的tab頁面
        viewPagerTab.setCurrentItem(0);

        // 讓頁籤長度大於螢幕長度時，頁籤可以進行滑滾的動作。
        // 但是有一點要注意，就是當頁籤長度小餘螢幕長度時，頁籤無法配合螢幕長度，進行頁籤與螢幕等寬的長度設定
        tabLayoutTab.setTabMode(TabLayout.MODE_SCROLLABLE);

        // 當使用者按下tab，顯示正確的頁面
        tabLayoutTab.setupWithViewPager(viewPagerTab);
    }

    // SamplePagerAdapter Class
    class SamplePagerAdapter extends PagerAdapter {


        @Override
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        // 该方案的缺点：那就是会刷新所有的 Item，这将导致系统资源的浪费，所以这种方式不适合数据量较大的场景。
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            // Setting maximum of Tabs
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Setting title name of the Tab
            return "Item " + (position + 1);
        }

        @Override
        //創建指定位置的頁面試圖
        public Object instantiateItem(ViewGroup container, int position) {
            // Setting your content of page on the ViewPager
            System.out.println("1. position : "+(position));
            View view = getLayoutInflater().inflate(R.layout.activity_pager_tab,
                    container, false);
            container.addView(view);

            TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(String.valueOf(position + 1));

            System.out.println("2. position : "+(position));


            GridView gridview=(GridView)findViewById(R.id.gridview);//找到main.xml中定义gridview 的id
            gridview.setAdapter(new ImageAdapter(gridview.getContext()));//调用ImageAdapter.java
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){//监听事件
                public void onItemClick(AdapterView<?> parent, View view, int p, long id)
                {
                    Toast.makeText(Homepage.this, ""+p,Toast.LENGTH_SHORT).show();//显示信息;
                    Log.d("test","action");               }
            });

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    ********************************* 切換頁面(結束) *********************************/

}
