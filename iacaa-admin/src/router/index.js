import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import Main from '@/App'

/* Router Modules */

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/Questionnaire',
    component: () => import('@/views/questionnaire/CourseTaskQuestionnaire'),
    meta:{requireAuth:false},
    hidden: true
  },
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/',
    component: Layout,
    redirect: '/Main',
    children: [
      {
        path: 'Main',
        component: () => import('@/views/Main/Main'),
        name: 'Main',
        meta: { title: '??????', icon: 'chart', affix: true }
      }]
  },
  {
    path: '/Cultivation',
    component: Layout,
    redirect: '/Cultivation',
    meta: { title: '????????????', icon: 'chart', affix: true ,roles: ['adminExclusive', 'Iacaa20Server:Menue:yearData']},
    children: [
      {
        path: 'Cultivation',
        component: () => import('@/views/Cultivation/CultivationEdit'),
        name: 'CultivationEdit',
        meta: { title: '????????????????????????', icon: 'chart', affix: true, activeMenu: '/article/list' }
      },
      {
        path: 'GradRequirementEdit',
        component: () => import('@/views/Cultivation/GradRequirementEdit'),
        name: 'GradRequirementEdit',
        meta: { title: '??????????????????????????????', icon: 'chart', affix: true }
      },
      {
        path: 'Target',
        component: () => import('@/views/Cultivation/Target'),
        name: 'Target',
        meta: { title: '?????????????????????', icon: 'chart', affix: true }
      }
    ]
  },
  {
    path: '/ReqAnalysis',
    component: Layout,
    redirect: '/ReqAnalysis',
    meta: { title: '??????????????????', icon: 'chart', affix: true ,roles: ['adminExclusive', 'Iacaa20Server:Menue:yearData']},
    children: [
      {
        path: 'ReqAnalysis',
        component: () => import('@/views/analysis/ReqAnalysis'),
        name: 'ReqAnalysis',
        meta: { title: '????????????', icon: 'chart', affix: true }
      },
      {
        path: 'TargetsAnalysis',
        component: () => import('@/views/analysis/TargetsAnalysis'),
        name: 'TargetsAnalysis',
        meta: { title: '?????????????????????', icon: 'chart', affix: true }
      },
      {
        path: 'CourseAnalysis',
        component: () => import('@/views/analysis/CourseAnalysis'),
        name: 'CourseAnalysis',
        meta: { title: '????????????', icon: 'chart', affix: true }
      }
    ]
  },
  {
    path: '/AdminCourse',
    component: Layout,
    redirect: '/AdminCourse',
    meta: { title: '????????????', icon: 'edit', affix: true, roles: ['adminExclusive', 'Iacaa20Server:Menue:AdminCourse']},
    children: [
      {
        path: 'AdminCourse',
        component: () => import('@/views/adminCourse/Course'),
        name: 'AdminCourse',
        meta: { title: '????????????', icon: 'edit', affix: true }
      }
    ]
  },
  {
    path: '/Course',
    component: Layout,
    redirect: '/Course',
    meta: { title: '???????????????????????????', icon: 'edit', affix: true, roles: ['adminExclusive', 'Iacaa20Server:Menue:CourseObjective']},
    children: [
      {
        path: 'Assessment',
        component: () => import('@/views/course/Assessment'),
        name: 'Assessment',
        meta: { title: '??????????????????', icon: 'edit', affix: true }
      },
    ]
  },
  {
    path: '/Score',
    component: Layout,
    redirect: '/Score',
    meta: { title: '????????????', icon: 'list', affix: true, roles: ['adminExclusive', 'Iacaa20Server:Menue:Score']},
    children: [
      {
        path: 'CheckLinkScore',
        component: () => import('@/views/score/CheckLinkScore'),
        name: 'CheckLinkScore',
        meta: { title: '?????????????????????????????????', icon: 'list', affix: true }
      }
    ]
  },
  {
    path: '/structure',
    component: Layout,
    redirect: '/structure',
    alwaysShow: true, // will always show the root menu
    name: 'structure',
    meta: {
      title: '????????????',
      icon: 'component',
      roles: ['adminExclusive', 'AuthCenter:company:findTree', 'AuthCenter:position:findTree']
    },
    children: [
      {
        path: 'StudentInfo',
        component: () => import('@/views/Main/StudentInfo.vue'),
        name: 'StudentInfo',
        meta: { title: '??????????????????', icon: 'list', affix: true }
      }, {
        path: 'TeacherInfo',
        component: () => import('@/views/Main/TeacherInfo'),
        name: 'TeacherInfo',
        meta: { title: '??????????????????', icon: 'list', affix: true }
      },{
        path: 'company',
        component: () => import('@/views/user-manage/company/index'),
        name: 'company',
        meta: {
          title: '????????????',
          roles: ['adminExclusive', 'AuthCenter:company:findTree']
        }
      },
      {
        path: 'position',
        component: () => import('@/views/user-manage/position/index'),
        name: 'position',
        meta: {
          title: '????????????',
          roles: ['adminExclusive', 'AuthCenter:position:findTree']
        }
      }
    ]
  },
  {
    path: '/authCenter',
    component: Layout,
    redirect: '/auth/center',
    alwaysShow: true, // will always show the root menu
    name: 'authCenter',
    hidden: true,
    meta: {
      title: '????????????',
      icon: 'guide',
      roles: ['adminExclusive', 'AuthCenter:client:page', 'AuthCenter:resource:page']
    },
    children: [
      {
        path: 'clientList',
        component: () => import('@/views/auth-center/client/index'),
        name: 'PageClient',
        meta: {
          title: '??????????????????',
          roles: ['adminExclusive', 'AuthCenter:client:page']
        }
      },
      {
        path: 'resourceList',
        component: () => import('@/views/auth-center/resource/index'),
        name: 'PageResource',
        meta: {
          title: '????????????',
          roles: ['adminExclusive', 'AuthCenter:resource:page']
        }
      }
    ]
  },
  {
    path: '/userManage',
    component: Layout,
    redirect: '/user/manage',
    alwaysShow: true, // will always show the root menu
    name: 'userManage',
    meta: {
      title: '????????????',
      icon: 'user',
      roles: ['adminExclusive', 'UserServer:User:page', 'AuthCenter:role:page', 'AuthCenter:company:findTree', 'AuthCenter:position:findTree']
    },
    children: [
      {
        path: 'userList',
        component: () => import('@/views/user-manage/user/index'),
        name: 'PageUser',
        meta: {
          title: '????????????',
          roles: ['adminExclusive', 'UserServer:User:page']
        }
      },
      {
        path: 'role',
        component: () => import('@/views/user-manage/role/index'),
        name: 'RolePermission',
        meta: {
          title: '????????????',
          roles: ['adminExclusive', 'AuthCenter:role:page']
        }
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
