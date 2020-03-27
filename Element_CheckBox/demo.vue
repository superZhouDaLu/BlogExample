<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button size="small" type="success" icon="el-icon-plus" @click="showCreate">
            新增
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog :title="textMap[dialogStatus]" width="850px" :visible.sync="dialogVisible">
      <el-form ref="tempRole" :model="tempRole" label-width="100px" size="mini">
        <el-form-item label="角色名称：" prop="role" required>
          <el-input v-model="tempRole.roleName" placeholder="填写角色名称" type="text" />
        </el-form-item>
        <el-form-item label="功能权限：">
          <el-table
              ref="menuList"
              :data="menuList"
              border
              fit
              size="mini"
              highlight-current-row
              row-key="menu.id"
              :default-expand-all="true"
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          >
            <el-table-column label="菜单名称" width="180">
              <template slot-scope="scope">
                <el-checkbox v-model="scope.row.checked" :indeterminate="scope.row.indeterminate" @change="handleCheckAllChange(scope.row, $event)">
                  {{ scope.row.menu.menuName }}
                </el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="功能选项">
              <template v-if="scope.row.children.length === 0" slot-scope="scope">
                <el-checkbox
                    v-for="options in scope.row.menuOptionsList"
                    :key="options.id"
                    v-model="options.checked"
                    :label="options.optionsName"
                    @change="handleCheckChange(scope.row)"
                />
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible = false">
          取 消
        </el-button>
        <el-button v-if="dialogStatus==='create'" size="mini" type="success">
          创 建
        </el-button>
        <el-button v-else size="mini" type="primary">
          修 改
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'RoleList',
    data() {
      return {
        list: [],
        dialogVisible: false,
        dialogStatus: '',
        textMap: {
          create: '新建角色',
          update: '编辑角色'
        },
        tempRole: {},
        menuList: []
      }
    },
    mounted() {
      this.getAllMenu()
    },
    methods: {
      getAllMenu() {
        // 这里可以修改为 Axios 查询真实数据
        this.menuList = [
          {
            menu: {
              id: 1,
              menuName: '系统管理',
              parentId: 0
            },
            menuOptionsList: [
              {
                id: 45,
                optionsName: '列表',
                checked: false
              }
            ],
            children: [
              {
                menu: {
                  id: 2,
                  menuName: '用户管理',
                  parentId: 1
                },
                menuOptionsList: [
                  {
                    id: 17,
                    optionsName: '列表',
                    checked: false
                  },
                  {
                    id: 18,
                    optionsName: '新增',
                    checked: false
                  },
                  {
                    id: 19,
                    optionsName: '删除',
                    checked: false
                  },
                  {
                    id: 20,
                    optionsName: '修改',
                    checked: false
                  }
                ],
                children: [],
                checked: false
              },
              {
                menu: {
                  id: 3,
                  menuName: '菜单管理',
                  parentId: 1
                },
                menuOptionsList: [
                  {
                    id: 21,
                    optionsName: '列表',
                    checked: false
                  },
                  {
                    id: 22,
                    optionsName: '新增',
                    checked: false
                  }
                ],
                children: [],
                checked: false
              },
              {
                menu:
                  {
                    id: 4,
                    menuName: '角色管理',
                    parentId: 1
                  },
                menuOptionsList: [
                  {
                    id: 27,
                    optionsName: '列表',
                    checked: false
                  },
                  {
                    id: 28,
                    optionsName: '新增',
                    checked: false
                  },
                  {
                    id: 29,
                    optionsName: '删除',
                    checked: false
                  },
                  {
                    id: 30,
                    optionsName: '修改',
                    checked: false
                  }
                ],
                children: [],
                checked: false
              }
            ],
            checked: false
          },
          {
            menu:
              {
                id: 14,
                menuName: '测试管理',
                parentId: 0
              },
            menuOptionsList: [
              {
                id: 46,
                optionsName: '列表',
                checked: false
              },
              {
                id: 47,
                optionsName: '新增',
                checked: false
              }
            ],
            children: [],
            checked: false
          },
          {
            menu:
              {
                id: 15,
                menuName: '多级管理',
                parentId: 0
              },
            menuOptionsList: [
              {
                id: 40,
                optionsName: '列表',
                checked: false
              }
            ],
            children: [
              {
                menu:
                  {
                    id: 16,
                    menuName: '二级管理',
                    parentId: 15
                  },
                menuOptionsList: [
                  {
                    id: 41,
                    optionsName: '列表',
                    checked: false
                  }
                ],
                children: [
                  {
                    menu:
                      {
                        id: 17,
                        menuName: '三级管理',
                        parentId: 16
                      },
                    menuOptionsList: [
                      {
                        id: 42,
                        optionsName: '列表',
                        checked: false
                      },
                      {
                        id: 43,
                        optionsName: '新增',
                        checked: false
                      },
                      {
                        id: 44,
                        optionsName: '修改',
                        checked: false
                      }
                    ],
                    children: [],
                    checked: false
                  },
                  {
                    menu:
                      {
                        id: 19,
                        menuName: '三级平级',
                        parentId: 16
                      },
                    menuOptionsList:
                      [
                        {
                          id: 50,
                          optionsName: '列表',
                          checked: false
                        },
                        {
                          id: 51,
                          optionsName: '删除',
                          checked: false
                        }
                      ],
                    children: [],
                    checked: false
                  }
                ],
                checked: false
              },
              {
                menu: {
                  id: 18,
                  menuName: '二级平级',
                  parentId: 15

                },
                menuOptionsList:
                  [
                    {
                      id: 48,
                      optionsName: '列表',
                      checked: false
                    },
                    {
                      id: 49,
                      optionsName: '新增',
                      checked: false
                    }
                  ],
                children: [],
                checked: false
              }
            ],
            checked: false
          }
        ]
      },
      showCreate() {
        this.dialogStatus = 'create'
        this.dialogVisible = true
      },
      handleCheckAllChange(val, checked) {
        // 有下级去处理下级
        if (val.children.length > 0) {
          this.findChildren(val.children, checked)
        } else {
          // 无下级处理本级
          val.menuOptionsList.forEach(options => { options.checked = checked })
        }
        // 处理上级
        if (val.menu.parentId !== 0) {
          this.findParent(this.menuList, val.menu.parentId)
        }
        val.indeterminate = false
      },
      handleCheckChange(val) {
        const length = val.menuOptionsList.length
        let checkedLength = 0
        val.menuOptionsList.forEach(options => {
          if (options.checked) {
            checkedLength++
          }
        })
        val.checked = checkedLength === length
        val.indeterminate = checkedLength > 0 && checkedLength < length
        // 处理上级
        if (val.menu.parentId !== 0) {
          this.findParent(this.menuList, val.menu.parentId)
        }
      },
      findChildren(list, checked) {
        list.forEach(children => {
          children.checked = checked
          children.indeterminate = false
          children.menuOptionsList.forEach(options => {
            options.checked = checked
            if (children.children.length > 0) {
              this.findChildren(children.children, checked)
            }
          })
        })
      },
      findParent(list, parentId) {
        list.forEach(result => {
          let parentCheckedLength = 0
          let parentIndeterminateLength = 0
          if (result.menu.id === parentId) {
            result.children.forEach(children => {
              if (children.indeterminate) {
                parentIndeterminateLength++
              } else if (children.checked) {
                parentCheckedLength++
              }
            })
            result.checked = parentCheckedLength === result.children.length
            result.indeterminate = (parentIndeterminateLength > 0 || parentCheckedLength > 0) && parentCheckedLength < result.children.length
            if (result.menu.parentId !== 0) {
              this.findParent(this.menuList, result.menu.parentId)
            }
          } else if (result.children.length > 0) {
            this.findParent(result.children, parentId)
          }
        })
      }
    }
  }
</script>
