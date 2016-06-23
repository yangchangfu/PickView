
##PickView

Android数据选择器，欢迎使用。好用的话，亲，赏个星星吧❤️

＃Android选择器的类型：
* 三列联动：PickView.Style.THREE
* 两列联动：PickView.Style.DOUBLE
* 一列：    PickView.Style.SINGLE
* 
* 使用方法：pickView.setPickerView(datas, PickView.Style.THREE);

##Demo

![preview_1](https://github.com/yangchangfu/PickView/blob/master/images/preview_1.gif)

<p>
    <img src="https://github.com/yangchangfu/PickView/blob/master/images/preview_1.png" width="320" alt="Screenshot" />
    <img src="https://github.com/yangchangfu/PickView/blob/master/images/preview_2.jpg" width="320" alt="Screenshot" />
</p>

##Gradle

```groovy
dependencies{
    compile 'com.yangchangfu:pickview-lib:1.0.0'
}

```

# Usage

### Step 1

* init data

```java
/**
* 初始化城市的数据
*/
public void initCityData() {

//获取所有的省市数据
List<ProvinceModel> provices = readAllCityData();
List<Item> items = new ArrayList<>();

for (int i=0; i<provices.size(); i++){

    ProvinceModel province = provices.get(i);
    Item item = new Item();
    item.name = province.getName();

    //该省市下，所有的城市数据
    List<CityModel> citys = province.getCityList();
    List<Item> items1 = new ArrayList<>();

    for (int j=0; j<citys.size(); j++){

        CityModel city = citys.get(j);
        Item item1 = new Item();
        item1.name = city.getName();

        //该城市下，所有的区数据
        List<DistrictModel> districts = city.getDistrictList();
        List<Item> items2 = new ArrayList<>();

        for (int k=0; k<districts.size(); k++){

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

```

### Step 2

* create a pickview, and show it.

```java
/**
 * 选择城市
 *
 * @param view
 */
public void selectCityClick(View view){

    cityPickView = new PickView(this);
    cityPickView.setPickerView(cityItems, PickView.Style.THREE);
    cityPickView.setShowSelectedTextView(true);
    cityPickView.setOnSelectListener(this);
    cityPickView.show();
}
```

### Step 3

* listener item click event

```java
public void OnSelectItemClick(View view, int[] selectedIndexs, String selectedText) {

    System.out.println("-----------------OnSelectItemClick-----------------");
    System.out.println("view class = " + view.getClass());

    for (int i=0; i<selectedIndexs.length; i++){
        System.out.println("selectedIndexs[" + i +"] = " + selectedIndexs[i]);
    }

    System.out.println("selectedText = " + selectedText);
}
```

### In all

* all codes

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.yangchangfu.pickview.MainActivity">

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="163dp"
        android:onClick="selectCityClick"
        android:text="选择城市" />

</RelativeLayout>
```

```java
public class MainActivity extends AppCompatActivity implements PickView.OnSelectListener {

    private List<Item> cityItems;
    private PickView cityPickView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        initCityData();
    }

    /**
     * 选择城市
     *
     * @param view
     */
    public void selectCityClick(View view){

        cityPickView = new PickView(this);
        cityPickView.setPickerView(cityItems, PickView.Style.THREE);
        cityPickView.setShowSelectedTextView(true);
        cityPickView.setOnSelectListener(this);
        cityPickView.show();
    }

    @Override
    public void OnSelectItemClick(View view, int[] selectedIndexs, String selectedText) {

        System.out.println("-----------------OnSelectItemClick-----------------");
        System.out.println("view class = " + view.getClass());

        for (int i=0; i<selectedIndexs.length; i++){
            System.out.println("selectedIndexs[" + i +"] = " + selectedIndexs[i]);
        }

        System.out.println("selectedText = " + selectedText);

        //更新按钮
        button.setText(selectedText);
    }

    /**
     * 初始化城市的数据
     */
    public void initCityData() {

        //获取所有的省市数据
        List<ProvinceModel> provices = readAllCityData();
        List<Item> items = new ArrayList<>();

        for (int i=0; i<provices.size(); i++){

            ProvinceModel province = provices.get(i);
            Item item = new Item();
            item.name = province.getName();

            //该省市下，所有的城市数据
            List<CityModel> citys = province.getCityList();
            List<Item> items1 = new ArrayList<>();

            for (int j=0; j<citys.size(); j++){

                CityModel city = citys.get(j);
                Item item1 = new Item();
                item1.name = city.getName();

                //该城市下，所有的区数据
                List<DistrictModel> districts = city.getDistrictList();
                List<Item> items2 = new ArrayList<>();

                for (int k=0; k<districts.size(); k++){

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
```

### Other

* If you have questions, please contact me
```java
QQ : 276054866
```

##Dependence
*   Yuri Kanivets [WheelView]()

##Thanks
*   Yuri Kanivets [Numeric wheel view](Yuri Kanivets)
