<template>
  <el-table
    ref="table"
    :data="tableData"
    border
    style="width: 80%;"
    :span-method="mergeCols"
    @row-click="onRowClick"
    @selection-change="handleSelectionChange"
  >
    <!-- 列表公共列 -->
    <el-table-column v-if="showSelection" type="selection" align="center" width="50" />
    <el-table-column type="index" align="center" width="50" label="序号" />
    <template v-for="colConfig in customColConfigs">
      <!--无需合并的列信息-->
      <el-table-column
        v-if="colConfig.type === 'label' && !colConfig.children"
        :key="colConfig.prop"
        :prop="colConfig.prop"
        :align="colConfig.align"
        :label="colConfig.label"
        :min-width="colConfig.width"
        :formatter="colConfig.formatter"
        :show-overflow-tooltip="colConfig.showOverflowTooltip"
      />
      <!--需合并的列信息-->
      <el-table-column
        v-else-if="colConfig.type === 'label' && colConfig.children"
        :key="colConfig.label"
        :align="colConfig.align"
        :label="colConfig.label"
        :min-width="colConfig.width"
      >
        <template v-for="children in colConfig.children">
          <el-table-column
            :key="children.prop"
            :prop="children.prop"
            :align="children.align"
            :label="children.label"
            :min-width="children.width"
            :show-overflow-tooltip="children.showOverflowTooltip"
          />
        </template>
      </el-table-column>
      <!--自定义的列信息-->
      <el-table-column
        v-else-if="colConfig.type !== 'label'"
        :key="colConfig.prop"
        :prop="colConfig.prop"
        :align="colConfig.align"
        :label="colConfig.label"
        :min-width="colConfig.width"
      >
        <template slot-scope="scope">
          <el-input v-if="colConfig.type === 'input'" v-model="scope.row[colConfig.prop]" :disabled="colConfig.disabled" />
        </template>
      </el-table-column>
    </template>
  </el-table>
</template>

<script>
export default {
  props: {
    tableData: {
      type: Array,
      required: true
    },
    colConfigs: {
      required: true,
      type: Array
    },
    showSelection: {
      type: Boolean,
      default: true
    },
    mergeColumns: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    return {
      multipleSelection: [],
      customColConfigs: {},
      tableMergeData: []
    }
  },
  mounted() {
    this.customColConfigs = this.colConfigs
    if (this.mergeColumns.length > 0) {
      this.$nextTick(() => {
        this.generateTableMergeData()
      })
    }
  },
  methods: {
    /**
     * 表格行点击事件
     * @param row 行对象
     */
    onRowClick(row) {
      this.$refs.table.toggleRowSelection(row)
    },
    /**
     * 表格多选框选中事件
     * @param val 选中的行数据信息
     */
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    /**
     * element表格合并方法
     * @param rowIndex 行坐标
     * @param columnIndex 列坐标
     * @returns {*} 单元格坐标信息
     */
    mergeCols({ rowIndex, columnIndex }) {
      const key = columnIndex + '_' + rowIndex
      if (this.tableMergeData[key]) {
        return this.tableMergeData[key]
      }
    },
    /**
     * 生成合并表格信息
     */
    generateTableMergeData() {
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
    },
    /**
     * 计算列坐标信息
     * @param data 单元格所在行数据
     * @param index 当前下标
     * @param nextIndex 下一个元素坐标
     * @param count 相同内容的数量
     * @param maxLength 当前行的列总数
     * @returns {*}
     */
    calculateColumnIndex(data, index, nextIndex, count, maxLength) {
      // 比较当前单元格中的数据与同一行之后的单元格是否相同
      if (nextIndex < maxLength && data[this.mergeColumns[index]['name']] === data[this.mergeColumns[nextIndex]['name']]) {
        return this.calculateColumnIndex(data, index, ++nextIndex, ++count, maxLength)
      }
      return count
    },
    /**
     * 计算行坐标信息
     * @param data 单元格所在行数据
     * @param index 当前下标
     * @param nextIndex 下一个元素坐标
     * @param count 相同内容的数量
     * @param name 数据的key
     * @returns {*}
     */
    calculateRowIndex(data, index, nextIndex, count, name) {
      // 比较当前单元格中的数据与同一列之后的单元格是否相同
      if (nextIndex < data.length && data[index][name] === data[nextIndex][name]) {
        return this.calculateRowIndex(data, index, ++nextIndex, ++count, name)
      }
      return count
    }
  }
}
</script>
