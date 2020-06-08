package com.example.score.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.score.bean.ArticleInfo;
import java.util.ArrayList;
import java.util.List;

import static com.example.score.util.Global.AF_CNT;
import static com.example.score.util.Global.A_image;
import static com.example.score.util.Global.A_subhead;
import static com.example.score.util.Global.A_title;
import static com.example.score.util.Global.A_type;
import static com.example.score.util.Global.A_url;
import static com.example.score.util.Global.DB;

public class GetArticleService {

    public GetArticleService() {
        //第一次加载数据库赋值
        if(AF_CNT == 0) {
            initGetArticleService();
            AF_CNT++;
        }
    }

    /**
     * 数据库调取赋值
     */
    private void initGetArticleService(){
        DB.openDB();
        SQLiteDatabase thisDB = DB.getmDB();
        List<ArticleInfo> articleInfoList = new ArrayList<ArticleInfo>();
        String sql = "select * from articleInfo";
        Cursor cuser = thisDB.rawQuery(sql,null);
        try {
            while (cuser.moveToNext()){
                ArticleInfo item = new ArticleInfo();
                int type = cuser.getInt(cuser.getColumnIndex("type"));
                String imageId = cuser.getString(cuser.getColumnIndex("imageUrl"));
                String title = cuser.getString(cuser.getColumnIndex("title"));
                String  subhead = cuser.getString(cuser.getColumnIndex("subhead"));
                String url = cuser.getString(cuser.getColumnIndex("articleUrl"));
                A_type.add(type);
                A_title.add(title);
                A_subhead.add(subhead);
                A_image.add(imageId);
                A_url.add(url);
            }
            cuser.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**、
     * 初始化添加
     * @return
     */
    public List<ArticleInfo> setArticleList(){
        List<ArticleInfo> articleInfoList = new ArrayList<ArticleInfo>();
        //添加信息
        for (int i = 0; i < A_title.size() ; i++) {
            final ArticleInfo item = new ArticleInfo();
            //初始化图片id
            item.setImageId(A_image.get(i));
            //标题
            item.setTitle(A_title.get(i));
            //副标题
            item.setSubhead(A_subhead.get(i));
            //文章网址
            item.setUrl(A_url.get(i));
            //类型
            item.setType(A_type.get(i));
            articleInfoList.add(item);
        }
        return articleInfoList;
    }
    /**
     * 更新添加内容
    * */

    public List<ArticleInfo> addArticleList(List<ArticleInfo> infoList,int lastPos){

        return infoList;
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
