import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/home/Home.vue";
import Article from "../views/article/Article.vue";
import Archive from "../views/archive/Archive.vue";
import Tag from "../views/tag/Tag.vue";
import Category from "../views/category/Category.vue";
import Link from "../views/link/Link.vue";
import About from "../views/about/About.vue";
import Message from "../views/message/Messsage.vue";
import ArticleList from "../components/ArticleList.vue";
import User from "../views/user/User.vue";
// import MyArticle from "../views/article/MyArticle.vue";
import MyComment from "../views/comment/myComment.vue";
import EditArticle from "../views/article/EditArticle.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: Home,
    meta: {
      title: "1874Blog"
    }
  },
  {
    path: "/articles/:articleId",
    component: Article
  },
  {
    path: "/archives",
    component: Archive,
    meta: {
      title: "归档"
    }
  },
  {
    path: "/tags",
    component: Tag,
    meta: {
      title: "标签"
    }
  },
  {
    path: "/categories",
    component: Category,
    meta: {
      title: "分类"
    }
  },
  {
    path: "/category/*",
    component: ArticleList
  },
  {
    path: "/links",
    component: Link,
    meta: {
      title: "友链列表"
    }
  },
  {
    path: "/about",
    component: About,
    meta: {
      title: "关于我"
    }
  },
  {
    path: "/message",
    component: Message,
    meta: {
      title: "留言板"
    }
  },
  {
    path: "/tag/*",
    component: ArticleList
  },
  {
    path: "/user",
    component: User,
    meta: {
      title: "个人中心"
    }
  },
  {
    path: "/comment",
    component: MyComment,
    meta: {
      title: "我的回复"
    }
  },
  {
    path: "/articles/edit/:articleId",
    component: EditArticle,
    meta: {
      title: "编辑文章"
    }
  }
];

const createRouter = () =>
  new VueRouter({
    mode: "history",
    base: "/",
    routes: routes
  });

const router = createRouter();
export default router;
