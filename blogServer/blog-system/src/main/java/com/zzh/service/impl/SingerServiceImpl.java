package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.aysnc.AsyncManager;
import com.zzh.common.exception.SaveOrUpdateException;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.common.utils.BeanCopyUtil;
import com.zzh.common.utils.StringUtils;
import com.zzh.dto.SingerDTO;
import com.zzh.entity.Singer;
import com.zzh.mapper.MusicMapper;
import com.zzh.mapper.SingerMapper;
import com.zzh.service.SingerService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.SingerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.zzh.common.constant.CommonConst.SINGER_COVER;
import static com.zzh.common.constant.CommonConst.SINGER_IMG_PATH;

/**
 * <p>
 * 歌手 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-04-02
 */
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    @Autowired
    SingerMapper singerMapper;
    @Autowired
    MusicMapper musicMapper;
    @Autowired
    AsyncManager asyncManager;

    @Override
    public PageInfo<Singer> listSingersPage(ConditionVO conditionVO) {
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        List<Singer> singers = singerMapper.selectList(
                new LambdaQueryWrapper<Singer>().like(
                        StringUtils.isNotBlank(conditionVO.getKeywords()), Singer::getSingerName, conditionVO.getKeywords()));
        return new PageInfo<>(singers);
    }

    @Override
    public List<SingerDTO> listSingers() {
        // 查找所有歌手
        List<Singer> singers = singerMapper.selectList(new LambdaQueryWrapper<Singer>().select(Singer::getId, Singer::getSingerName));
        // 复制属性成需要的对象并返回
        return BeanCopyUtil.copyList(singers,SingerDTO.class);
    }

    @Override
    public void updateImg(MultipartFile file, Integer id) {
        // 如果为空则返回
        if (file.isEmpty()) {
            throw new SaveOrUpdateException("服务器异常！请稍后再试！");
        }
        Singer singer = singerMapper.selectById(id);
        if (Objects.isNull(singer)) {
            throw new CommonWriteException("找不到相关歌手！");
        }
         /*
        删除原歌曲图片
         */
        // 获得图片实际地址
        String path = singer.getSingerPic();
        String realPath = System.getProperty("user.dir") + System.getProperty("file.separator") + path;
        File imgFile = new File(realPath);
        if (imgFile.exists() && !SINGER_COVER.equals(path)) {
            imgFile.delete();
        }
        // 文件名等于毫秒加文件名 避免重复
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        // 创建文件
        File file1 = new File(SINGER_IMG_PATH);
        // 如果文件路径不存在则新增
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(SINGER_IMG_PATH + System.getProperty("file.separator") + fileName);
        try {
            // 保存文件
            file.transferTo(dest);
            singerMapper.update(new Singer(), new LambdaUpdateWrapper<Singer>()
                    .set(Singer::getSingerPic, "/img/singerImg/" + fileName)
                    .eq(Singer::getId, id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSinger(Integer id) {
        // 首先删除对应歌曲
        List<Integer> musicIdList = musicMapper.listIdsBySinger(id);
        if (!musicIdList.isEmpty()) {
            // 不为空则删除
            asyncManager.deleteMusic(musicIdList);
        }
        Singer singer = singerMapper.selectById(id);
        // 删除歌手图片
        String picPath = singer.getSingerPic();
        // 获得图片真实路径
        String realPath = System.getProperty("user.dir") + System.getProperty("file.separator") + picPath;
        File file = new File(realPath);
        // 如果文件存在并且不是默认图则删除
        if (file.exists() && !SINGER_COVER.equals(picPath)) {
            file.delete();
        }
        // 删除数据库数据
        singerMapper.deleteById(id);
    }

    @Override
    public void saveOrUpdateSinger(SingerVO singerVO) {
        // 查询有无同名歌手
        Integer count = singerMapper.selectCount(new LambdaQueryWrapper<Singer>().eq(Singer::getSingerName, singerVO.getSingerName()));
        if (count > 0 && Objects.isNull(singerVO.getId())) {
            throw new SaveOrUpdateException("有相同歌手存在!");
        }
        // 保存数据库
        Singer singer = Singer.builder()
                .id(singerVO.getId())
                .singerSex(singerVO.getSingerSex())
                .singerBirth(singerVO.getSingerBirth())
                .singerIntroduction(singerVO.getSingerIntroduction())
                .singerLocation(singerVO.getSingerLocation())
                .singerName(singerVO.getSingerName())
                .build();
        if (Objects.isNull(singerVO.getId())) {
            singer.setSingerPic(SINGER_COVER);
        }
        this.saveOrUpdate(singer);
    }

}
