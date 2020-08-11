### 前言

> 示例版本为 Element-UI 2.13.2 + Vue 2.6.11

由于项目业务需求，最近写了一个可以自动合并行、列的表格组件，**对数据格式有一定的要求性**。

![merge_table.png](http://ww1.sinaimg.cn/large/005yqb1Zly1ghmnxjbqeyj312r0eodft.jpg)

[Element-UI](
https://codepen.io/pen/?&editable=true=https%3A%2F%2Felement.eleme.cn%2F) 提供了表格内容可以合并的示例，但是复杂一些的功能还是需要自己完成，在写组件之前查找了相关资料，并没有发现相关的示例，故分享一下。

### 预设数据

组件在定义之前需要提前预设几个属性：

* tableData：表格中的数据
* colConfigs：表格列的内容信息
* mergeColumns：要合并的列信息

#### tableData
示例中的数据格式：

```
 [  
    { time: '2020-08-10', grade: '三年二班', name: '小明', subjects: '语文' },
    { time: '2020-08-10', grade: '三年二班', name: '小明', subjects: '数学' }, 
    { time: '2020-08-10', grade: '总成绩', name: '总成绩', subjects: '总成绩' },
    { time: '2020-08-10', grade: '三年一班', name: '小雷', subjects: '语文' },
    { time: '2020-08-10', grade: '三年一班', name: '小雷', subjects: '数学' },
    { time: '2020-08-10', grade: '总成绩', name: '总成绩', subjects: '总成绩' }, 
    { time: '2020-08-11', grade: '三年三班', name: '小花', subjects: '语文' }, 
    { time: '2020-08-11', grade: '三年三班', name: '小花', subjects: '数学' }, 
    { time: '2020-08-11', grade: '总成绩', name: '总成绩', subjects: '总成绩' }
 ]
```
#### colConfigs

表格列的内容信息：

```
[
  {
    label: '考试信息',
    align: 'center',
    type: 'label',
    children: [
      { prop: 'time', label: '考试时间', align: 'center' },
      { prop: 'grade', label: '所在班级', align: 'center' },
      { prop: 'name', label: '考生姓名', align: 'center' },
      { prop: 'subjects', label: '考试科目', align: 'center' }
    ]
  },
  { prop: 'results', label: '考试成绩', align: 'center', type: 'input' },
  { prop: 'remark', label: '备注', align: 'center', type: 'input' }
]
```

| 参数 | 说明 |
| --- | --- |
| label |  显示的标题（element-ui原始属性） |
| align | 对齐方式（element-ui原始属性） |
| width | 对应列的宽度（element-ui原始属性） |
| prop | 对应列内容的字段名（element-ui原始属性） |
| formatter | 用来格式化内容（element-ui原始属性） |
| type | 自定义属性，暂分为‘label’和其它，label为不可编辑的列，其它属性可按需自定义|
| children | 自定义属性，存在合并列的情况 |

#### mergeColumns


要合并的列信息数据格式：

```
[
  { index: 2, name: 'time' },
  { index: 3, name: 'grade' },
  { index: 4, name: 'name' },
  { index: 5, name: 'subjects' }
]
```

| 参数 | 说明 |
| --- | --- |
| index |  所在表格中的列下标 |
| name |  要合并的字段名称 |


### 定义组件

将上面预设好的数据以 `props` 的方式传递到定义好的组件内。`el-table`组件内的内容在不进行合并的时候坐标信息为 `[1,1]`，如果中间出现了 `0`，例如：`[0,1]`， 就会隐藏掉相关的单元格，反之如果为 `[1,2]` 就会把相邻的单元格合并。表格合并思路为：在组件加载时根据 `tableData`  和`mergeColumns` 生成新的坐标信息，并调用`Element`提供的 `span-method` 生成。

#### 生成坐标数组信息

![flow.jpg](http://ww1.sinaimg.cn/large/005yqb1Zly1ghmtx7cjvrj31870y4tba.jpg)

 ```
  // 遍历表格中需要合并的所有单元格
  for (let i = 0; i < this.tableData.length; i++) {
    for (let j = 0; j < this.mergeColumns.length; j++) {
      // 初始化行、列坐标信息
      let rowIndex = 1
      let columnIndex = 1
      // 比较横坐标左方的第一个元素
      if (j > 0 && this.tableData[i][this.mergeColumns[j]['name']] === this.tableData[i][this.mergeColumns[j - 1]['name']]) {
        columnIndex = 0
      }
      // 比较纵坐标上方的第一个元素
      if (i > 0 && this.tableData[i][this.mergeColumns[j]['name']] === this.tableData[i - 1][this.mergeColumns[j]['name']]) {
        rowIndex = 0
      }
      // 比较横坐标右方元素
      if (columnIndex > 0) {
        columnIndex = this.calculateColumnIndex(this.tableData[i], j, j + 1, 1, this.mergeColumns.length)
      }
      // 比较纵坐标下方元素
      if (rowIndex > 0) {
        rowIndex = this.calculateRowIndex(this.tableData, i, i + 1, 1, this.mergeColumns[j]['name'])
      }
      const key = this.mergeColumns[j]['index'] + '_' + i
      this.tableMergeData[key] = [rowIndex, columnIndex]
    }
  }
 ```

**遍历要合并的每一个单元格数据，以单个单元格为节点对周围的单元格的内容进行比对，然后用当前坐标为key存放新的坐标值，然后调用`Element`提供的 `span-method`方法生成合并后的数据结果。**

```
 mergeCols({ rowIndex, columnIndex }) {
    const key = columnIndex + '_' + rowIndex
    if (this.tableMergeData[key]) {
      return this.tableMergeData[key]
    }
  }
```
