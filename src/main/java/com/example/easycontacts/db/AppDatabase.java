package com.example.easycontacts.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.easycontacts.model.User;
import com.example.easycontacts.model.Contact;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Contact.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ContactDao contactDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "easycontacts_db")
                        .fallbackToDestructiveMigration(true)
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                Executors.newSingleThreadExecutor().execute(() -> {
                                    getDatabase(context).contactDao().insertAll(
                                            new Contact("张三", "13800000001", "zhangsan@example.com", "1990-01-01", "北京市朝阳区", "老朋友"),
                                            new Contact("李四", "13800000002", "lisi@example.com", "1985-05-15", "上海市浦东新区", "同事"),
                                            new Contact("王五", "13800000003", "wangwu@example.com", "1992-09-10", "广州市天河区", "客户"),
                                            new Contact("赵六", "13800000004", "zhaoliu@example.com", "1988-12-20", "深圳市南山区", "供应商"),
                                            new Contact("钱七", "13800000005", "qianqi@example.com", "1995-03-30", "杭州市西湖区", "邻居"),
                                            new Contact("孙八", "13800000006", "sunba@example.com", "1993-07-07", "成都市武侯区", "老乡"),
                                            new Contact("周九", "13800000007", "zhoujiu@example.com", "1980-11-11", "南京市鼓楼区", "朋友"),
                                            new Contact("吴十", "13800000008", "wushi@example.com", "1987-08-08", "武汉市洪山区", "同学"),
                                            new Contact("郑十一", "13800000009", "zhengshiyi@example.com", "1991-04-04", "西安市雁塔区", "业务伙伴"),
                                            new Contact("冯十二", "13800000010", "fengshier@example.com", "1994-06-06", "苏州市姑苏区", "亲戚"),
                                            new Contact("陈十三", "13800000011", "chenshisan@example.com", "1989-02-02", "青岛市市南区", "学长"),
                                            new Contact("褚十四", "13800000012", "chushisi@example.com", "1986-10-10", "大连市中山区", "老同事"),
                                            new Contact("卫十五", "13800000013", "weishiwu@example.com", "1990-09-09", "厦门市思明区", "摄影师"),
                                            new Contact("蒋十六", "13800000014", "jiangshiliu@example.com", "1996-12-12", "哈尔滨市道里区", "跑步好友"),
                                            new Contact("沈十七", "13800000015", "shenshiqie@example.com", "1983-03-03", "合肥市蜀山区", "项目负责人"),
                                            new Contact("韩十八", "13800000016", "hanshibai@example.com", "1982-07-14", "石家庄市长安区", "前邻居"),
                                            new Contact("杨十九", "13800000017", "yangshijiu@example.com", "1997-01-01", "长沙市岳麓区", "合伙人"),
                                            new Contact("朱二十", "13800000018", "zhuershi@example.com", "1984-05-05", "郑州市金水区", "志愿者朋友"),
                                            new Contact("秦二一", "13800000019", "qiner1@example.com", "1998-08-18", "佛山市禅城区", "羽毛球搭子"),
                                            new Contact("尤二二", "13800000020", "youer2@example.com", "1993-11-23", "南宁市青秀区", "大学室友")
                                    );

                                });
                            }
                        })
                        .build();
            }
        }
        return INSTANCE;
    }
}

