
##PickView

Android数据选择器，欢迎使用。好用的话，亲，赏个星星吧❤️

##Android选择器的类型：
* 三列联动：PickView.Style.THREE
* 两列联动：PickView.Style.DOUBLE
* 一列：    PickView.Style.SINGLE

```java
public enum Style {
        SINGLE, DOUBLE, THREE
 }
```

##使用方法：
```java
pickView.setPickerView(datas, PickView.Style.THREE);//数据、选择器的类型
```

##属性与方法：
1、属性：isShow;//弹出状态属性
```java
System.out.println("pickView isshow = " + pickView.isShow);
```
2、show();
```java
pickView.show();
```

3、dismiss();
```java
pickView.show();
```

##Demo
![preview_1](https://github.com/yangchangfu/PickView/blob/master/images/demo.gif)
![preview_1](https://github.com/yangchangfu/PickView/blob/master/ww.gif)
<p>
    <img src="https://github.com/yangchangfu/PickView/blob/master/device-2016-08-30-114219.png" width="260" alt="Screenshot" />
    <img src="https://github.com/yangchangfu/PickView/blob/master/images/1.png" width="260" alt="Screenshot" />
    <img src="https://github.com/yangchangfu/PickView/blob/master/images/2.png" width="260" alt="Screenshot" />
    <img src="https://github.com/yangchangfu/PickView/blob/master/images/3.png" width="260" alt="Screenshot" />
</p>

##Gradle

```groovy
dependencies{
    compile 'com.yangchangfu:pickview-lib:1.0.2'
}

```

##版本更新与问题记录：

1、v1.0.1版本新增了PickView弹出视图的状态属性：isShow；

2、v1.0.2版本解决的问题：
   （1）多级同时滑动奔溃的问题；
   （2）Android部分手机存在虚拟键盘的遮挡问题；

# Usage

### Step 1

* init data

```java
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
public void selectCityClick(View view) {

    cityPickView = new PickView(this);
    cityPickView.setPickerView(cityItems, PickView.Style.THREE);
    cityPickView.setShowSelectedTextView(true);
    cityPickView.setOnSelectListener(this);
    cityPickView.show();
}

/**
 * 选择类型
 *
 * @param view
 */
public void selectCateClick(View view) {

    catePickView = new PickView(this);
    catePickView.setPickerView(cates, PickView.Style.DOUBLE);
    catePickView.setShowSelectedTextView(true);
    catePickView.setOnSelectListener(this);
    catePickView.show();
}

/**
 * 选择数据
 *
 * @param view
 */
public void selectDataClick(View view) {

    dataPickView = new PickView(this);
    dataPickView.setPickerView(datas, PickView.Style.SINGLE);
    dataPickView.setShowSelectedTextView(true);
    dataPickView.setOnSelectListener(this);
    dataPickView.show();
}
```

### Step 3

* listener item click event

```java
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
    } else if (view == catePickView) {
        button2.setText(selectedText);
    } else {
        button3.setText(selectedText);
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
