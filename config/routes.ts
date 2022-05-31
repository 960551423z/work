export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: '/user',
        routes: [
          {name: '登录', path: '/user/login', component: './user/Login'},
          {name: '注册', path: '/user/register', component: './user/Register'}
        ]
      },
      {component: './404'},
    ],
  },
  {
    path: '/welcome',
    name: '欢迎',
    icon: '../smile.svg',
    component: './Welcome'
  },
  {path: '/center', name: '个人中心', icon: '../center.svg', component: './Center'},
  {
    path: '/admin',
    name: '管理页',
    icon: '../crown.svg',
    access: 'canAdmin',
    component: './Admin',
    routes: [
      {path: '/admin/user-manger', name: '二级管理页', icon: 'smile', component: './Admin/UseManger'},
      {component: './404'},
    ],
  },
  // {name: '查询表格', icon: 'table', path: '/list', component: './TableList'},
  // {name: '用户中心', icon: 'user', path: '/Account', component: './Account'},
  {path: '/', redirect: '/welcome'},
  {component: './404'},
];
