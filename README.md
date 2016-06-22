
# PickView
A android pick view

##Demo

![](https://github.com/yangchangfu/PickView/images/preview_1.gif)

![](https://github.com/yangchangfu/PickView/images/preview_1.png)

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

### Other

* If you have questions, please contact me
```java
QQ:276054866
```

##Dependence
*   [WheelView](https://github.com/JakeWharton/NineOldAndroids)

##Thanks
*   [Numeric wheel view](Yuri Kanivets)
