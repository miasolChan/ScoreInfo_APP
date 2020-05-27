package com.example.score.service;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import com.example.score.bean.ArticleInfo;
import com.example.score.util.ImageLoaderUtils;
import java.util.ArrayList;
import java.util.List;

public class GetArticleService {
    private String[] image = {"image_0.jpg","image_1.jpg","image_2.jpg"};
    private String[] title = {"疫情下的作曲劳模Alexandr Desplat","专访《囧妈》作曲鹏飞","专访John Powell"};
    private String[] subhead = {"谈新作《法兰西特派》与《木偶奇遇记》","音乐打磨800遍，只为今年华语乐坛电影最动人的一场戏","谈《野性的呼唤》"};
    private String[] url = {"http://www.baidu.com","2","3"};
    private int[] type = {1,1,2};

    /**、
     * 初始化添加
     * @return
     */
    public List<ArticleInfo> setArticleList(){
        List<ArticleInfo> articleInfoList = new ArrayList<ArticleInfo>();
        //添加信息
        for (int i = 0; i < title.length ; i++) {
            final ArticleInfo item = new ArticleInfo();
            //初始化图片id
            item.setImageId(image[i]);
            //标题
            item.setTitle(title[i]);
            //副标题
            item.setSubhead(subhead[i]);
            //文章网址
            item.setUrl(url[i]);
            //类型
            item.setType(type[i]);
            articleInfoList.add(item);
        }
        return articleInfoList;
    }

    /**、
     * 选择类型
     * @return
     */

    public List<ArticleInfo> selectItemType(List<ArticleInfo> articleInfoList,int type){
        List<ArticleInfo> selectedList = new ArrayList<ArticleInfo>();
        for (int i = 0; i < articleInfoList.size() ; i++) {
            ArticleInfo item = articleInfoList.get(i);
            if(type == 0){
                return articleInfoList;
            } else if(item.getType()==type){
                selectedList.add(item);
            }
        }
        return selectedList;
    }



}
