package com.yangchangfu.pickview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yangchangfu.pickview.Model.CityModel;
import com.yangchangfu.pickview.Model.DistrictModel;
import com.yangchangfu.pickview.Model.ProvinceModel;
import com.yangchangfu.pickview_lib.Item;
import com.yangchangfu.pickview_lib.PickView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PickView.OnSelectListener {

    private List<Item> cityItems;
    private PickView cityPickView;

    private List<Item> cates;
    private PickView catePickView;

    private List<Item> datas;
    private PickView dataPickView;

    private Button button1, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        initCitys();
        initCates();
        initDatas();

        cityPickView = new PickView(this);
        cityPickView.setPickerView(cityItems, PickView.Style.THREE);
        cityPickView.setShowSelectedTextView(true);
        cityPickView.setOnSelectListener(this);

        catePickView = new PickView(this);
        catePickView.setPickerView(cates, PickView.Style.DOUBLE);
        catePickView.setShowSelectedTextView(true);
        catePickView.setOnSelectListener(this);

        dataPickView = new PickView(this);
        dataPickView.setPickerView(datas, PickView.Style.SINGLE);
        dataPickView.setShowSelectedTextView(true);
        dataPickView.setOnSelectListener(this);
    }

    /**
     * 选择城市
     *
     * @param view
     */
    public void selectCityClick(View view) {



        cityPickView.show();
    }

    /**
     * 选择类型
     *
     * @param view
     */
    public void selectCateClick(View view) {


        catePickView.show();
    }

    /**
     * 选择数据
     *
     * @param view
     */
    public void selectDataClick(View view) {


        dataPickView.show();
    }

    /**
     * 读取所有的状态 isShow
     *
     * @param view
     */
    public void readStateClick(View view) {

        System.out.println("cityPickView isshow = " + cityPickView.isShow);
        System.out.println("catePickView isshow = " + catePickView.isShow);
        System.out.println("dataPickView isshow = " + dataPickView.isShow);
    }


    @Override
    public void OnSelectItemClick(View view, int[] selectedIndexs, String selectedText) {

        System.out.println("-----------------OnSelectItemClick-----------------");
        System.out.println("view class = " + view.getClass());

        for (int i = 0; i < selectedIndexs.length; i++) {
            System.out.println("selectedIndexs[" + i + "] = " + selectedIndexs[i]);
        }
        System.out.println("selectedText = " + selectedText);

        //更新按钮
        if (view == cityPickView) {
            button1.setText(selectedText);
            System.out.println("cityPickView isshow = " + cityPickView.isShow);
        } else if (view == catePickView) {
            button2.setText(selectedText);
            System.out.println("catePickView isshow = " + catePickView.isShow);
        } else {
            button3.setText(selectedText);
            System.out.println("dataPickView isshow = " + dataPickView.isShow);
        }

    }

    /**
     * 初始化数据
     */
    public void initDatas() {
        List<Item> items = new ArrayList<>();

        String[] data = {"农家乐", "亲子园", "欢乐谷", "游泳馆", "其他"};

        for (int i = 0; i < data.length; i++) {
            Item item = new Item();
            item.name = data[i];

            items.add(item);
        }

        this.datas = items;
    }

    /**
     * 初始化分类的数据
     */
    public void initCates() {
        List<Item> items = new ArrayList<>();

        String[] data = {"电影", "音乐", "电台", "游戏"};
        String[] data1 = {"速度与激情7", "魔兽", "变形金刚4", "孤岛危机", "生化危机4"};
        String[] data2 = {"爱你一万年", "死了都要爱", "我相信", "默", "其他"};
        String[] data3 = {"时光电台", "其他"};
        String[] data4 = {"魔兽", "传奇", "孤岛危机", "穿越火箭", "其他"};

        for (int i = 0; i < data.length; i++) {
            Item item = new Item();
            item.name = data[i];

            List<Item> items1 = new ArrayList<>();

            if (i == 0){
                for (int j = 0; j < data1.length; j++) {
                    Item item1 = new Item();
                    item1.name = data1[j];
                    items1.add(item1);
                }
            }
            else if (i == 1){
                for (int j = 0; j < data2.length; j++) {
                    Item item1 = new Item();
                    item1.name = data2[j];
                    items1.add(item1);
                }
            }
            else if (i == 2){
                for (int j = 0; j < data3.length; j++) {
                    Item item1 = new Item();
                    item1.name = data3[j];
                    items1.add(item1);
                }
            }
            else if (i == 3){
                for (int j = 0; j < data4.length; j++) {
                    Item item1 = new Item();
                    item1.name = data4[j];
                    items1.add(item1);
                }
            }

            item.items = items1;

            items.add(item);
        }

        this.cates = items;
    }

    /**
     * 初始化城市的数据
     */
    public void initCitys() {

        //获取所有的省市数据
        List<ProvinceModel> provices = readAllCityData();
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < provices.size(); i++) {

            ProvinceModel province = provices.get(i);
            Item item = new Item();
            item.name = province.getName();

            //该省市下，所有的城市数据
            List<CityModel> citys = province.getCityList();
            List<Item> items1 = new ArrayList<>();

            for (int j = 0; j < citys.size(); j++) {

                CityModel city = citys.get(j);
                Item item1 = new Item();
                item1.name = city.getName();

                //该城市下，所有的区数据
                List<DistrictModel> districts = city.getDistrictList();
                List<Item> items2 = new ArrayList<>();

                for (int k = 0; k < districts.size(); k++) {

                    DistrictModel district = districts.get(k);

                    Item item2 = new Item();
                    item2.name = district.getName();
                    items2.add(item2);
                }

                item1.items = items2;
                items1.add(item1);
            }

            item.items = items1;
            items.add(item);
        }

        this.cityItems = items;
    }


    /**
     * 解析省市区的数据
     *
     * @return
     */
    public List<ProvinceModel> readAllCityData() {

        List<ProvinceModel> lists = new ArrayList<ProvinceModel>();

        String jString = readDataFromAssetsFile(this, "threeStageArea.json");//getAllMerchantTypeList.json
        System.out.println("-------------------------------------");
        System.out.println(jString);

        try {

            JSONObject jsonObject = new JSONObject(jString);
            JSONObject obj1 = jsonObject.getJSONObject("cities");

            for (int i = 0; i < 34; i++) {

                JSONObject obj2 = obj1.getJSONObject(String.valueOf(i));
                Iterator iterator = obj2.keys();

                while (iterator.hasNext()) {
                    String str1 = (String) iterator.next();
                    System.out.println("---------------------iterator----------------");
                    System.out.println(str1);

                    ProvinceModel provinceModel = new ProvinceModel();
                    provinceModel.setName(str1);

                    List<CityModel> cityList = new ArrayList<CityModel>();

                    JSONObject obj3 = obj2.getJSONObject(str1);
                    JSONArray array1 = obj3.names();

                    System.out.println("---------------------array1----------------");
                    System.out.println(array1);

                    for (int j = 0; j < array1.length(); j++) {
                        JSONObject obj4 = obj3.getJSONObject(String.valueOf(j));
                        JSONArray array2 = obj4.names();
                        for (int k = 0; k < array2.length(); k++) {
                            String city = (String) array2.get(k);

                            CityModel cityModel = new CityModel();
                            cityModel.setName(city);

                            JSONArray array3 = obj4.getJSONArray(city);

                            List<DistrictModel> districtList = new ArrayList<DistrictModel>();

                            for (int h = 0; h < array3.length(); h++) {
                                DistrictModel districtModel = new DistrictModel();
                                districtModel.setName(array3.getString(h));
                                districtModel.setZipcode("0000");
                                districtList.add(districtModel);
                            }

                            cityModel.setDistrictList(districtList);

                            cityList.add(cityModel);
                        }
                    }

                    provinceModel.setCityList(cityList);

                    lists.add(provinceModel);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("--------------------lists-----------------");
        System.out.println(lists.toString());

        return lists;
    }

    /**
     * 读取Assets文件资源
     *
     * @param context
     * @param fileName
     * @return
     */
    public String readDataFromAssetsFile(Context context, String fileName) {

        try {

            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";

            while ((line = bufReader.readLine()) != null)
                Result += line;

            return Result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
