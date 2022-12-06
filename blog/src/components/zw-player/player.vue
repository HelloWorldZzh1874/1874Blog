<template>
  <div>
    <transition name="dis_list">
      <div class="list_box" v-if="listIsDis">
        <transition name="music_alert">
          <span class="music_alert" v-if="musicAlertState">{{
            musicAlertVal
          }}</span>
        </transition>
        <div class="list_close" @click="DisList">x</div>
        <div class="music_list">
          <div class="list_l">
            <ul class="music_type">
              <li
                :key="item.id"
                v-for="item in musicTypeList"
                @click="_getMusicType(item.id)"
                :class="{ type_active: item.id == thisMusicType }"
              >
                {{ item.name }}
              </li>
            </ul>
            <div class="list_title">
              <span style="font-size: 14px">歌曲列表</span>
              <img
                :src="musicStateButton"
                alt=""
                class="music_state"
                @click="MusicStateChange"
              />
              <div class="music_search_box">
                <input
                  type="text"
                  class="music_search"
                  v-model="musicSearchVal"
                  placeholder="搜索歌曲并添加到收藏"
                />
                <transition name="music_search">
                  <ul class="search_list" v-if="musicSearchVal !== ''">
                    <li
                      :key="item.id"
                      v-for="item in musicSearchList"
                      @click="ListAdd(item)"
                    >
                      <span class="music_search_name">{{ item.name }}</span>
                      <span class="music_search_ar">{{ item.singer }}</span>
                    </li>
                  </ul>
                </transition>
              </div>
            </div>
            <div class="music_ul_title">
              <span>歌曲</span><span>歌手</span><span>专辑</span>
            </div>
            <ul class="list">
              <li
                :key="item.id"
                v-for="(item, index) in thisMusicList"
                @mouseover="ButtonActive(index)"
                @dblclick="ListPlay((thisListPage - 1) * 10 + index)"
              >
                <div
                  class="this_music_shlter"
                  v-if="(thisListPage - 1) * 10 + index == thisMusicIndex"
                ></div>
                <span>{{ item.name }}</span
                ><span>{{ item.singer }}</span
                ><span>{{ item.al }}</span>
                <transition name="list_button">
                  <div
                    class="music_button"
                    v-if="listButtonActiveIndex == index"
                  >
                    <div
                      class="list_play"
                      title="播放这首歌"
                      :style="{ backgroundImage: 'url(' + listPlay + ')' }"
                      @click="ListPlay((thisListPage - 1) * 10 + index)"
                    ></div>
                    <div
                      class="list_play"
                      title="添加到我的收藏"
                      :style="{ backgroundImage: 'url(' + add + ')' }"
                      @click="ListAdd(item)"
                      v-if="thisMusicType != -1"
                    ></div>
                    <div
                      class="list_play"
                      title="取消收藏"
                      :style="{ backgroundImage: 'url(' + remove + ')' }"
                      @click="removeCollect(item)"
                      v-if="thisMusicType != 1"
                    ></div>
                  </div>
                </transition>
              </li>
            </ul>
            <div class="list_page">
              <div
                class="page_last"
                v-if="thisListPage != 1"
                @click="ListChange(true)"
              ></div>
              <div
                class="page_next"
                v-if="thisListPage != Math.ceil(musicList.length / 10)"
                @click="ListChange(false)"
              ></div>
            </div>
          </div>
        </div>
      </div>
    </transition>
    <div class="bbox" :class="{ bbox_active: disActive }">
      <div
        class="pan"
        :style="{ backgroundImage: 'url(' + pan + ')' }"
        :class="{ pan_active: disActive }"
        @click="DisActive"
      >
        <img :src="musicImg" alt="" class="pan_c" />
      </div>
      <div
        class="box"
        :style="{ backgroundImage: 'url(' + musicImg + ')' }"
        :class="{ box_active: disActive }"
        @dblclick="DisList"
      >
        <div
          class="music_shlter_2"
          :style="{ backgroundImage: 'url(' + musicImg + ')' }"
          :class="{ shlter_active: disActive }"
        ></div>
        <div
          class="music_shlter"
          :style="{ backgroundImage: 'url(' + musicImg + ')' }"
          :class="{ shlter_active: disActive }"
        ></div>
        <div class="music_shlter_3"></div>
        <div class="music_dis">
          <div class="dis_list" @click="DisList">···</div>
          <p class="music_title">{{ musicTitle }}</p>
          <p class="music_intro">歌手: {{ musicName }}</p>
          <ul class="music_words">
            <div class="music_words_box" :style="{ top: wordsTop + 'px' }">
              <li
                :key="item.id"
                v-for="(item, index) in musicWords"
                class="music_word"
                :class="{ word_highlight: wordIndex == index }"
              >
                {{ item }}
              </li>
            </div>
          </ul>
        </div>
        <div class="control_box">
          <div class="control_button">
            <img :src="playIcon" alt="" class="control_icon" />
          </div>
          <div class="progress">
            <div class="progress_c" :style="{ width: currentProgress }">
              <div class="progress_circle">
                <div class="progress_circle_c"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <video id="music" :src="musicUrl" name="media"></video>
    </div>
  </div>
</template>
<script>
import {
  getWords,
  addCollect,
  removeCollect,
  getCollect,
  getMusicUrl,
  getHotMusic,
  getSearchSuggest
} from "./api/music";
import pan from "./img/pan.png";
import play from "./img/play.png";
import pause from "./img/pause.png";
import add from "./img/add.png";
import remove from "./img/remove.png";
import shlter from "./img/list_pan.png";
import listPlay from "./img/list_play_hover.png";
import state0 from "./img/state_0.png";
import state1 from "./img/state_1.png";
import talkicon1 from "./img/talkicon1.png";
import talkicon2 from "./img/talkicon2.png";
import $ from "jquery";
import { mixin } from "../../utils/index";
export default {
  mixins: [mixin],
  name: "Player",
  data() {
    return {
      o: 0,
      top: 0,
      pan,
      play,
      pause,
      add,
      remove,
      shlter,
      listPlay,
      state0,
      state1,
      talkicon1,
      talkicon2,
      playState: false,
      playIcon: play,
      musicImg: "",
      musicUrl: "",
      musicWords: [],
      musicTitle: "",
      musicName: "",
      wordsTime: [],
      wordsTop: 0,
      wordIndex: 0,
      currentProgress: "0%",
      musicList: [],
      thisMusicIndex: 1,
      disActive: false,
      listIsDis: false,
      listButtonActiveIndex: -1,
      thisListPage: 1,
      // 歌曲类型
      musicTypeList: [
        { name: "歌曲列表", id: 1 },
        { name: "我的收藏", id: -1 }
      ],
      // 当前歌曲类型,默认我的收藏
      thisMusicType: -1,
      // 歌单中无法播放的歌曲id
      notPlay: [],
      //0列表循环  1单曲循环
      musicState: 0,
      musicStateButton: state1,
      musicSearchVal: "",
      musicSearchList: [],
      musicAlertVal: "",
      musicAlertState: false,
      musicAlertTimer: ""
    };
  },
  created() {
    // 获取初始音乐数据--1代表热歌榜
    this._getMusicType(1);
    // 禁删~感谢配合
    this.DisAuthorInfo();
  },
  mounted() {
    // 初始播放器(已有数据后)
    this.Player();
  },
  computed: {
    // 分页
    thisMusicList() {
      return this.musicList.slice((this.thisListPage - 1) * 10, 10);
    }
  },
  watch: {
    // 搜索栏值的监控
    musicSearchVal() {
      if (this.musicSearchVal === "") {
        this.musicSearchList = [];
      } else {
        getSearchSuggest(this.musicSearchVal).then(res => {
          if (res.data == undefined) {
            this.musicSearchList = [];
          } else {
            this.musicSearchList = res.data;
          }
        });
      }
    }
  },
  methods: {
    //禁删~感谢配合
    DisAuthorInfo() {
      console.log(
        "%c音乐播放器作者----仲威，博客地址：https://blogme.top",
        "background-color:rgb(30,30,30);border-radius:4px;font-size:12px;padding:4px;color:rgb(220,208,129);"
      );
    },
    // 歌曲模块的消息弹窗
    MusicAlert(val) {
      this.musicAlertState = true;
      this.musicAlertVal = val;
      clearTimeout(this.musicAlertTimer);
      this.musicAlertTimer = setTimeout(() => {
        this.musicAlertState = false;
        this.musicAlertVal = "";
      }, 2000);
    },
    // 添加到我的收藏列表
    ListAdd(obj) {
      // 验证登录状态
      if (!this.$store.state.id) {
        this.MusicAlert("请登录后再操作!");
        return false;
      }
      let param = new URLSearchParams();
      param.append("id", obj.id);
      addCollect(param).then(res => {
        if (res.code === "200") {
          this.musicSearchVal = "";
          // 提示已经添加进去
          this.MusicAlert("添加成功");
        } else {
          this.MusicAlert("添加失败，请稍后再试!");
        }
      });
    },
    // 移除我的收藏
    removeCollect(item) {
      let param = {};
      let data = { id: item.id };
      param = { params: data };
      let that = this;
      removeCollect(param).then(res => {
        if (res.code === "200") {
          getCollect().then(res => {
            that.musicList = res.data.splice(0, 200);
            if (this.musicList.length !== 0) {
              // 歌曲切换到第一首
              that.thisMusicIndex = 0;
              that.thisListPage = 1;
              // 获取歌曲信息
              that._getInfo();
              that.top = 0;
              that.o = 0;
              that.wordIndex = 0;
              that.wordsTop = 0;
              // 歌曲进度条
              this.currentProgress = "0%";
              if (!this.playState) {
                $(".control_icon").click();
              }
            } else {
              //自定义库没有歌曲 提示需要搜索才可以添加
              this.MusicAlert("没有歌曲,需要自己添加");
            }
          });
        } else {
          this.MusicAlert(res.msg);
        }
      });
    },
    // 播放模式切换
    MusicStateChange() {
      if (this.musicState == 0) {
        this.musicState = 1;
        this.musicStateButton = this.state0;
        this.MusicAlert("已切换为单曲循环模式");
      } else {
        this.musicState = 0;
        this.musicStateButton = this.state1;
        this.MusicAlert("已切换为列表循环模式");
      }
    },
    DisList() {
      this.listIsDis = !this.listIsDis;
    },
    // 分页
    ListChange(isLast) {
      if (isLast) {
        this.thisListPage--;
      } else {
        this.thisListPage++;
      }
    },
    // 列表播放/下一首播放
    ListPlay(id) {
      // 保证id(下一首歌曲)不等于当前播放的歌曲id
      if (this.thisMusicIndex != id) {
        // 重新赋值当前歌曲index，如果长度越界则跳转第一首歌
        this.thisMusicIndex = id > this.musicList.length - 1 ? 0 : id;
        // 重新获取url
        this._getInfo();
        this.top = 0;
        this.o = 0;
        this.wordIndex = 0;
        this.wordsTop = 0;
        this.currentProgress = "0%";
        setTimeout(() => {
          if (!this.playState) {
            $(".control_icon").click();
          }else {
            let player = $("#music")[0];
            player.play();
          }
        }, 500);
      }
    },
    ButtonActive(id) {
      this.listButtonActiveIndex = id;
    },
    DisActive() {
      this.disActive = !this.disActive;
    },
    // 获取相关歌单歌曲
    _getMusicType(id) {
      // 获取歌单数据，初始化时获取热歌榜
      if (this.thisMusicType != id) {
        this.notPlay = [];
        // 获取我的收藏音乐数据
        if (id == -1) {
          // 判断登录
          if (!this.$store.state.id) {
            this.MusicAlert("请登录!");
            return false;
          }
          getCollect().then(res => {
            this.musicList = res.data.splice(0, 200);
            // 当前歌单类型切换到用户收藏
            this.thisMusicType = id;
            if (this.musicList.length !== 0) {
              // 歌曲切换到第一首
              this.thisMusicIndex = 0;
              this.thisListPage = 1;
              // 获取歌曲信息
              this._getInfo();
              this.top = 0;
              this.o = 0;
              this.wordIndex = 0;
              this.wordsTop = 0;
              // 歌曲进度条
              this.currentProgress = "0%";
              // if (!this.playState) {
              //   $(".control_icon").click();
              // }
              setTimeout(() => {
                if (this.playState) {
                  let player = $("#music")[0];
                  player.play();
                }
              }, 500);
            } else {
              //自定义库没有歌曲 提示需要搜索才可以添加
              this.MusicAlert("没有歌曲,需要自己添加");
            }
          });
        } else {
          // 获取热歌榜数据
          getHotMusic().then(res => {
            // 截取前200数据
            this.musicList = res.data.splice(0, 200);
            // 当前歌单类型切换到普通歌单
            this.thisMusicType = id;
            // 播放指针指到第一首歌
            this.thisMusicIndex = 0;
            // 第一页
            this.thisListPage = 1;
            // 获取歌曲详细信息
            this._getInfo();
            this.top = 0;
            this.o = 0;
            this.wordIndex = 0;
            this.wordsTop = 0;
            // 歌曲进度条
            this.currentProgress = "0%";
            // 播放歌曲
            // if (!this.playState) {
            //   $(".control_icon").click();
            // }
            setTimeout(() => {
              if (this.playState) {
                let player = $("#music")[0];
                player.play();
              }
            }, 500);
          });
        }
      }
    },
    // 获取歌曲信息，包括url，封面，标题等
    _getInfo() {
      // 通过歌曲id获取当前播放指针(this.thisMusicIndex)歌曲的Url
      // 初始化时该指针为0表示第一首歌
      getMusicUrl(this.musicList[this.thisMusicIndex].id).then(res => {
        // 如果没有获取到信息
        if (res.data == null || res.data == "" || res.data == undefined) {
          // 如果歌单中无法播放的歌曲数量不等于歌单中歌曲的数量
          if (this.notPlay.length != this.musicList.length) {
            // 跳转查询下一首歌
            let nextIndex = this.thisMusicIndex + 1;
            // 如果不可播放列表中还没有这首歌，则将这首歌存入notPlay中
            if (this.notPlay.indexOf(this.thisMusicIndex) == -1) {
              this.notPlay.push(this.thisMusicIndex);
            }
            // 提示这首歌不能放
            this.MusicAlert(
              `${this.musicList[this.thisMusicIndex].name}因为一些原因不能播放`
            );
            // 寻找下一首歌  直到找到
            this.ListPlay(nextIndex);
          } else {
            // 遍历完没有找到
            this.MusicAlert("此列表所有歌都不能播放");
          }
        } else {
          // 如果获取到了url，准备播放
          this.musicUrl = this.getUrl(res.data);
          // 获取歌曲封面、标题等信息,展示到当前正在播放上
          this.musicImg = this.getUrl(
            this.musicList[this.thisMusicIndex].picUrl
          );
          this.musicTitle = this.musicList[this.thisMusicIndex].name;
          this.musicName = this.musicList[this.thisMusicIndex].singer;
          // 获取歌曲歌词
          getWords(this.musicList[this.thisMusicIndex].id).then(res => {
            if (res.code === "200") {
              let info = this.Cut(res.data);
              this.musicWords = info.wordArr;
              this.wordsTime = info.timeArr;
            }
          });
        }
      });
    },
    // 歌词截图工具
    Ltrim(s) {
      return s.replace(/(^\s*)/g, "");
    },
    Rtrim(s) {
      return s.replace(/(\s*$)/g, "");
    },
    //歌词截取函数
    Cut(str) {
      let timeArr = [];
      let wordArr = [];
      timeArr = str.split("[");
      timeArr.splice(0, 1);
      for (let i = 0; i < timeArr.length; i++) {
        let cut = timeArr[i].split("]");
        let time = cut[0].split(".")[0].split(":");
        timeArr[i] = Number.parseInt(time[0]) * 60 + Number.parseInt(time[1]);
        timeArr[i] = isNaN(timeArr[i]) ? 0 : timeArr[i]; //处理NaN
        wordArr[i] = this.Rtrim(this.Ltrim(cut[1]));
      }
      return { timeArr: timeArr, wordArr: wordArr };
    },
    Player() {
      let self = this;
      let player = $("#music")[0];
      let playerTimer = setInterval(timer, 1000);
      //定时器函数
      $("body").on("click", () => {
        player.pause();
        $("body").unbind("click");
      });
      // 歌词滚动
      function timer() {
        self.currentProgress = `${(player.currentTime / player.duration) *
          100}%`;
        //接着这里写歌词滚动
        if (player.currentTime >= self.wordsTime[self.o + 1]) {
          self.top += Number.parseInt(
            $(".music_word")
              .eq(self.o)
              .height() +
              Number.parseInt(
                $(".music_word")
                  .eq(self.o)
                  .css("marginTop")
              )
          );
          if (self.top >= $(".music_words").height() / 2 - 11) {
            //开始滚动的高度
            self.wordsTop += -Number.parseInt(
              $(".music_word")
                .eq(self.o)
                .height() +
                Number.parseInt(
                  $(".music_word")
                    .eq(self.o)
                    .css("marginTop")
                )
            );
          }
          self.wordIndex = self.o + 1;
          self.o++;
        }
        if (player.currentTime >= player.duration) {
          //切歌
          if (self.musicList.length != 1) {
            //只有一首歌  重复播放
            if (self.musicState == 0) {
              self.thisMusicIndex =
                self.thisMusicIndex >= self.musicList.length - 1
                  ? 0
                  : self.thisMusicIndex + 1;
              self._getInfo();
            }
          }
          setTimeout(() => {
            player.play();
            self.top = 0;
            self.o = 0;
            self.wordIndex = 0;
            self.wordsTop = 0;
            self.currentProgress = "0%";
          }, 500);
        }
      }
      //进度条控制
      $(".progress").on("mousedown", ev => {
        let e = ev || event;
        let pro =
          (e.clientX - $(".progress").offset().left) / $(".progress").width();
        clearInterval(playerTimer);
        this.currentProgress = `${pro * 100}%`;
        $(document).on("mousemove", ev => {
          let e = ev || event;
          pro =
            (e.clientX - $(".progress").offset().left) / $(".progress").width();
          this.currentProgress = `${pro * 100}%`;
        });
        $(document).on("mouseup", () => {
          player.currentTime = player.duration * pro;
          let c_arr = [...this.wordsTime];
          c_arr.push(player.currentTime);
          c_arr.sort((l, r) => {
            return l - r;
          });
          let now_o = c_arr.indexOf(player.currentTime) - 1;
          let diff_h = 0;
          if (this.o < now_o) {
            for (let i = this.o; i < now_o; i++) {
              diff_h += -Number.parseInt(
                $(".music_word")
                  .eq(i)
                  .height() +
                  Number.parseInt(
                    $(".music_word")
                      .eq(i)
                      .css("marginTop")
                  )
              );
            }
          } else {
            for (let i = now_o; i < this.o; i++) {
              diff_h += Number.parseInt(
                $(".music_word")
                  .eq(i)
                  .height() +
                  Number.parseInt(
                    $(".music_word")
                      .eq(i)
                      .css("marginTop")
                  )
              );
            }
          }
          this.wordsTop += diff_h;
          self.wordIndex = this.o = now_o;
          clearInterval(playerTimer);
          playerTimer = setInterval(timer, 1000);
          this.playState = true;
          this.playIcon = this.pause;
          if (player.currentTime >= player.duration) {
            this.top = 0;
            this.o = 0;
            this.wordIndex = 0;
            this.wordsTop = 0;
            this.currentProgress = "0%";
          }
          player.play();
          $(document).unbind("mousemove");
          $(document).unbind("mouseup");
        });
      });
      //播放暂停按钮控制
      $(".control_icon").on("click", () => {
        if (this.playState) {
          player.pause();
          this.playState = false;
          this.playIcon = this.play;
          clearInterval(playerTimer);
        } else {
          player.play();
          this.playState = true;
          this.playIcon = this.pause;
          clearInterval(playerTimer);
          playerTimer = setInterval(timer, 1000);
        }
      });
    },
    Contorl() {
      let player = $("#music")[0];
      player.currentTime = 100;
    }
  }
};
</script>
<style scoped>
@import url("./player.css");
@import url("./playermobile.css");
</style>
