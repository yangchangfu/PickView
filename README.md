
# PickView
A android pick view

##Demo
![](https://github.com/H07000223/FlycoTabLayout/blob/master/preview_1.gif)

##Gradle

```groovy
dependencies{
    compile 'com.yangchangfu:pickview-lib:1.0.0'
}

```

# Usage

### Step 1

* add PullDownMenuView in layout xml

```xml
<com.yangchangfu.pull_downmenu_lib.PullDownMenuView
        android:id="@+id/pulldownmenu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:menuTotalColumn="3"/>
```

### Step 2

* create a list to add items.

```java
List<PullDownMenuItemData> list0 = new ArrayList<>();
for (int i=0; i<15; i++){
    PullDownMenuItemData data = new PullDownMenuItemData();
    data.id = "" + i;
    data.name = "主分类" + i;
    data.num = String.valueOf(10 * i);

    List<PullDownMenuItemData> itemList = new ArrayList<>();
    for (int j=0; j<6; j++){
        PullDownMenuItemData sub = new PullDownMenuItemData();
        sub.id = "" + i;
        sub.name = "子分类:" + i + "-" + j;
        sub.num = String.valueOf(5 * i);
        itemList.add(sub);
    }

    if (i % 2 == 0) {
        data.itemList = itemList;
    } 

    list0.add(data);
}
        
PullDownMenuView pullDownMenuView = (PullDownMenuView) findViewById(R.id.pulldownmenu);
pullDownMenuView.setMenuColumn(0, list0, 0);
pullDownMenuView.setMenuColumn(1, list0, 0, 0);
pullDownMenuView.setMenuColumn(2, list0, 0);
```

### Step 3

* listener item click event

```java
pullDownMenuView.setOnItemSelectListener(new PullDownMenuView.OnItemSelectListener() {
      @Override
      public void OnItemSelect(int column, int position1, PullDownMenuItemData data1, int position2, PullDownMenuItemData data2) {
          System.out.println("----------------------------");
          System.out.println("column = " + column);
          System.out.println("position1 = " + position1);
          System.out.println("data1 = " + data1.toString());
          System.out.println("position2 = " + position2);
          System.out.println("data2 = " + data2.toString());
      }
  });
```

### Other

* open menu method for PullDownMenuView

```java
//update menu
pullDownMenuView.updateMenuColumn(2, list0, 5);
```
* If you have questions, please contact me
```java
QQ:276054866
```

##Dependence
*   [WheelView](https://github.com/JakeWharton/NineOldAndroids)

##Thanks
*   [WheelView](https://github.com/jpardogo/PagerSlidingTabStrip)
