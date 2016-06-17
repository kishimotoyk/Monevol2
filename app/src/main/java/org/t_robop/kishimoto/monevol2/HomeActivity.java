package org.t_robop.kishimoto.monevol2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
//colortemplateを用いたとき用
import com.github.mikephil.charting.data.DataSet;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //ナビゲーションドロワーの関数呼び出し
        drawer();

        //円グラフの関数呼び出し
        piechart();
    }

    @Override
    //Androidの戻るボタンが押された時に、ドロワーが開いていた際閉じる動作をさせる設定
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //ナビゲーションドロワーの各ボタンが押された時の動作の設定
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //ドロワーのホームが押された時の動作
        if (id == R.id.home) {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        //ドロワーのグラフが押された時の動作
        else if (id == R.id.graph) {
            Intent intent = new Intent(HomeActivity.this, GraphActivity.class);
            startActivity(intent);
        }
        //ドロワーの課金履歴が押された時の動作
        else if (id == R.id.history) {
            Intent intent = new Intent(HomeActivity.this, RecordActivity.class);
            startActivity(intent);
        }
        //ドロワーの設定が押された時の動作
        else if (id == R.id.settings) {
            Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //ナビゲーションドロワーの設定
    public void drawer(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    public void piechart() {
        //円グラフの関数
        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        int[] color = new int[2];

        ArrayList<Entry> entries = new ArrayList<>();
        //グラフにデータを追加
        entries.add(new Entry(10, 0));
        entries.add(new Entry(8, 1));


        PieDataSet dataset = new PieDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        //データの名前の設定
        labels.add("課金額");
        labels.add("残り予算");

        color[0] = Color.LTGRAY;
        color[1] = Color.GREEN;

        dataset.setColors(color);
        //色の設定

        PieData data = new PieData(labels, dataset);

        //pieChart.setDescription("Description");
        pieChart.setData(data);


        //pieChart.animateY(2000);
        //アニメーションの時間設定小さくなればはやい 無効にすることも可能

        pieChart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image

        //真ん中に円状の穴を開けるか開けないか
        pieChart.setDrawHoleEnabled(true);

        //真ん中の円状の穴にテキスト表示
        pieChart.setDrawCenterText(true);

        //テキストの内容
        pieChart.setCenterText("残り予算\n~円");

        //円グラフを触って回転させるかさせないか
        pieChart.setRotationEnabled(false);

    }

    //課金額入力ボタンを押された時にダイアログメッセージを出して金額を入力させる
    public void onClick(View arg0) {

        //テキスト入力を受け付けるビューを作成します。
        final EditText editView = new EditText(HomeActivity.this);
        new AlertDialog.Builder(HomeActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("課金額入力")
                //setViewにてビューを設定します。
                .setView(editView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //入力した文字をトースト出力する
                        Toast.makeText(HomeActivity.this,
                                editView.getText().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }
}