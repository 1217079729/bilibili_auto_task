package top.misec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import top.misec.config.Config;
import top.misec.login.ServerVerify;
import top.misec.login.Verify;
import top.misec.task.DailyTask;
import top.misec.utils.VersionInfo;


/**
 * @author Junzhou Liu
 * @create 2020/10/11 2:29
 */
public class BiliMain {

    static Logger logger = (Logger) LogManager.getLogger(BiliMain.class.getName());

    public static void main(String[] args) {

        if (args.length < 3) {
            logger.info("任务启动失败");
            logger.warn("Cookies参数缺失，请检查是否在Github Secrets中配置Cookies参数");
        }
        //读取环境变量
        Verify.verifyInit(args[0], args[1], args[2]);

        if (args.length > 3) {
            ServerVerify.verifyInit(args[3]);
        }

        VersionInfo.printVersionInfo();
        //每日任务65经验
        Config.getInstance().configInit();
        if (Config.getInstance().getSkipDailyTask() == 0) {
            DailyTask dailyTask = new DailyTask();
            dailyTask.doDailyTask();
        } else {
            logger.info("自定义配置中开启了跳过本日任务，本日任务跳过，如果需要取消跳过，请将skipDailyTask值改为0");
        }
    }

}
