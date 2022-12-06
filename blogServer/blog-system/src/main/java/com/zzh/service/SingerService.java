package com.zzh.service;

import com.github.pagehelper.PageInfo;
import com.zzh.dto.SingerDTO;
import com.zzh.entity.Singer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.SingerVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  歌手 服务类
 * </p>
 *
 * @author zzh
 * @since 2022-04-02
 */
public interface SingerService extends IService<Singer> {

    /**
     * 查询分页歌手数据
     * @date 2022/4/3
     * @param conditionVO 查询条件
     * @return PageInfo分页歌手数据
     */
    PageInfo<Singer> listSingersPage(ConditionVO conditionVO);


    /**
     * 查询歌手简略信息
     * @return
     */
    List<SingerDTO> listSingers();

    /**
     * 更新歌手图片
     * @date 2022/4/3
     * @param file 图片文件
     * @param id 歌手id
     */
    void updateImg(MultipartFile file, Integer id);

    /**
     * 根据id删除歌手
     * @date 2022/4/3
     * @param id 歌手id
     */
    void deleteSinger(Integer id);

    /**
     * 修改或保存歌手
     * @param singerVO 歌手数据
     */
    void saveOrUpdateSinger(SingerVO singerVO);


}
