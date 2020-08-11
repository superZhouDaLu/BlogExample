<template>
  <div>
    <merge-table v-if="tableData.length > 0" :table-data="tableData" :col-configs="colConfigs" :merge-columns="mergeColumns" />
  </div>
</template>

<script>
import MergeTable from './components/MergeTable'
export default {
  name: 'MergeTableDemo',
  components: { MergeTable },
  data() {
    return {
      tableData: [],
      colConfigs: [
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
      ],
      mergeColumns: [
        { index: 2, name: 'time' },
        { index: 3, name: 'grade' },
        { index: 4, name: 'name' },
        { index: 5, name: 'subjects' }
      ]
    }
  },
  mounted() {
    // 获取数据
    this.fetchData()
  },
  methods: {
    fetchData() {
      // 使用Axios调用数据 此处为模拟数据
      this.tableData = [
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
    }
  }
}
</script>
